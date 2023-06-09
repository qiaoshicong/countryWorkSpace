package com.mt.cms.service.newsArticles.impl;

import com.mt.cms.dao.newsArticles.NewsArticlesDao;
import com.mt.cms.dao.newsCategory.NewsCategoryDao;
import com.mt.cms.dao.publisher.PublisherDao;
import com.mt.cms.entity.newsArticles.NewsArticles;
import com.mt.cms.service.newsArticles.NewsArticlesService;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.security.LoginUser;
import com.mt.common.core.web.BaseService;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.entity.Attachment;
import com.mt.common.system.mapper.AttachmentDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class NewsArticlesServiceBean extends BaseService implements NewsArticlesService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private NewsArticlesDao newsArticlesDao;
	@Autowired
	private NewsCategoryDao newsCategoryDao;

	@Autowired
	private AttachmentDao attachmentDao;

	@Autowired
	private PublisherDao publisherDao;

	@Resource
	private RedisTemplate<String, List<NewsArticles>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 查询全部新闻文章
	 * @return
	 */
	@Override
	public List<NewsArticles> findAllNewsArticlessMy(){
		List<NewsArticles> newsArticlesDTOS = this.newsArticlesDao.findNewsArticlessMy();
	return newsArticlesDTOS;
	}
	/**
	 * 根据分页参数查询新闻文章集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findNewsArticless(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());

		List<NewsArticles> newsArticlesDTOS = this.newsArticlesDao.findNewsArticless(pageDTO);
		Long totalCount = this.newsArticlesDao.findNewsArticlesTotalCount(pageDTO);

		//在查询新闻类别表中根据id查询名称，存入新闻文章表中
		newsArticlesDTOS.stream().forEach(newsArticles -> {
			if(Objects.nonNull(newsArticles.getCategoryId())) {
				String categoryName = newsCategoryDao.findNewsCategorysWithNameById(Long.valueOf(newsArticles.getCategoryId())).getName();
				newsArticles.setCategoryName(categoryName);
			}
			if(Objects.isNull(newsArticles.getThumbnail())){
				Attachment attachment = attachmentDao.findAllAttachmentsWithAssociate(newsArticles.getEid(),"newsArticles");
				String imgUrl;
				if(attachment != null ){
					imgUrl = attachment.getAttachmentAddr();
					System.out.println("imgUrl------------" + imgUrl);
					newsArticles.setThumbnail(imgUrl);
				}
			}
		});

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(newsArticlesDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部新闻文章集合
	 *
	 */
	@Override
	public List<NewsArticles> findAllNewsArticless(){
		return this.newsArticlesDao.findAllNewsArticless();
	}

	/**
	 * 根据ID查询指定的新闻文章
	 *
	 * @param newsArticlesId Id
	 */
	@Override
	public NewsArticles findNewsArticles(Long newsArticlesId){

		return this.newsArticlesDao.findNewsArticles(newsArticlesId);
	}


	/**
	 * 新增新闻文章
	 *
	 * @param newsArticles 实体对象
	 */
	@Override
	public NewsArticles saveNewsArticles(NewsArticles newsArticles) throws ParseException {
		//获取 当前用户姓名存入数据库
		LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		User user =  JSON.parseObject(JSON.parse(principal).toString(), User.class);
		String nickname = user.getNickname();
		Long eid = publisherDao.findPublisherByName(nickname).getEid();
		newsArticles.setPublisherNameId(eid);

		//获取当前时间并存入数据库（新闻发布时间）
		java.util.Date day=new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(day);
		Date date = sdf.parse(format);
		newsArticles.setCreateDatetime(date);
		Long rows = this.newsArticlesDao.saveNewsArticles(newsArticles);
		if(rows != 1)
		{
			String error = "新增保存新闻文章出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return newsArticles;
	}

	/**
	 * 更新新闻文章
	 *
	 * @param newsArticles 实体对象
	 */
	@Override
	public NewsArticles updateNewsArticles(NewsArticles newsArticles){

		Long rows = this.newsArticlesDao.updateNewsArticles(newsArticles);
		if(rows != 1)
		{
			String error = "修改保存新闻文章出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return newsArticles;
	}

	/**
	 * 根据ID删除新闻文章
	 *
	 * @param newsArticlesId ID
	 */
	@Override
	public void deleteNewsArticles(Long newsArticlesId){

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(NewsArticles.class, newsArticlesId);
		if(entityUsageMap != null && entityUsageMap.size() >0){
			StringBuilder errors = new StringBuilder();
			errors.append("计划删除的数据正在被以下数引用\n");
			for(EntityUsage entityUsage : entityUsageMap.values()){
				errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
				for(Map.Entry<Long,String> entry : entityUsage.getUsageIdNames().entrySet() ){
					errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
				}
			}
			errors.append("，不能删除，请检查处理后再删除");
			throw  new BusinessException(errors.toString());
		}

		Long rows = this.newsArticlesDao.deleteNewsArticles(newsArticlesId);
		if(rows != 1){
			String error = "删除新闻文章出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	/**
	 * 根据ID查询指定的新闻文章(包含外键)
	 *
	 * @param newsArticlesId Id
	 */
	@Override
	public NewsArticles findNewsArticlesWithForeignName(Long newsArticlesId){

		return this.newsArticlesDao.findNewsArticlesWithForeignName(newsArticlesId);
	}


	private void validateSaveNewsArticles(NewsArticles newsArticles) {
	//不为空判断
	if (newsArticles.getEid() != null || newsArticles.getCreatorId() != null || newsArticles.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateNewsArticles()写法
	}

	private void validateUpdateNewsArticles(NewsArticles newsArticles) {
	//不为空判断
	if (newsArticles.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.newsArticlesDao.findNewsArticlesTotalCount(PageDTO.create(NewsArticles.FIELD_ID, newsArticles.getEid())) == 0) {
	throw new BusinessException("修改的新闻文章 " + newsArticles.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateNewsArticles()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
		return false;
	}


}
