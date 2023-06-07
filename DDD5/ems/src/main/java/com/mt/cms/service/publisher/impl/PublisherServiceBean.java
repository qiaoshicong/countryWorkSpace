package com.mt.cms.service.publisher.impl;

import com.mt.cms.dao.publisher.PublisherDao;
import com.mt.cms.entity.publisher.Publisher;
import com.mt.cms.service.publisher.PublisherService;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.BaseService;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PublisherServiceBean extends BaseService implements PublisherService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private PublisherDao publisherDao;


	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询发布者信息集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findPublishers(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());

		List<Publisher> publisherDTOS = this.publisherDao.findPublishers(pageDTO);
		Long totalCount = this.publisherDao.findPublisherTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(publisherDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部发布者信息集合
	 *
	 */
	@Override
	public List<Publisher> findAllPublishers(){
		return this.publisherDao.findAllPublishers();
	}


	/**
	 * 根据ID查询指定的发布者信息
	 *
	 * @param publisherId Id
	 */
	@Override
	public Publisher findPublisher(Long publisherId){

		return this.publisherDao.findPublisher(publisherId);
	}

	/**
	 * 新增发布者信息
	 *
	 * @param publisher 实体对象
	 */
	@Override
	public Publisher savePublisher(Publisher publisher){

		Long rows = this.publisherDao.savePublisher(publisher);
		if(rows != 1)
		{
			String error = "新增保存发布者信息出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return publisher;
	}

	/**
	 * 更新发布者信息
	 *
	 * @param publisher 实体对象
	 */
	@Override
	public Publisher updatePublisher(Publisher publisher){

		Long rows = this.publisherDao.updatePublisher(publisher);
		if(rows != 1)
		{
			String error = "修改保存发布者信息出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return publisher;
	}

	/**
	 * 根据ID删除发布者信息
	 *
	 * @param publisherId ID
	 */
	@Override
	public void deletePublisher(Long publisherId){


		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(Publisher.class, publisherId);
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

		Long rows = this.publisherDao.deletePublisher(publisherId);
		if(rows != 1){
			String error = "删除发布者信息出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}


	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
