

package com.mt.cms.controller.newsCategory;

import com.mt.cms.entity.newsCategory.NewsCategory;
import com.mt.cms.service.newsCategory.NewsCategoryService;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
							

@Api(tags = "新闻类别")
@RestController
@RequestMapping("/api/cms/newsCategory/NewsCategory")
@CrossOrigin(allowCredentials = "true")
public class NewsCategoryController {
private static Logger logger = LoggerFactory.getLogger(NewsCategoryController.class);

	@Autowired private NewsCategoryService newsCategoryService;
	
	/**
	* 根据分页参数查询新闻类别集合
	*
	* @param pageDTO 分页条件
	*/
	@PostMapping("/findNewsCategorys")
	public PageResultDTO findNewsCategorys(@RequestBody PageDTO pageDTO){
		return this.newsCategoryService.findNewsCategorys(pageDTO);
	}

	/**
	* 根据ID查询指定的新闻类别
	* @param newsCategoryId Id
	*/
	@PostMapping("/findNewsCategory")
	public NewsCategory findNewsCategory(@RequestParam Long newsCategoryId){
		return this.newsCategoryService.findNewsCategory(newsCategoryId);
	}

//
//	/**
//	* 创建新的新闻类别
//	*/
//
//	@PostMapping("/createNewsCategory")
//	public NewsCategoryEditDto createNewsCategory(){
//				NewsCategoryEditDto newsCategoryEditDto = new NewsCategoryEditDto();
//		newsCategoryEditDto.setNewsCategory(new NewsCategory());
//
//		this.prepareNewsCategoryEditDto(newsCategoryEditDto);
//		return newsCategoryEditDto;
//	}
////
//	private void prepareNewsCategoryEditDto(NewsCategoryEditDto newsCategoryEditDto){
//																							}

	/**
	* 新增保存新闻类别
	* @param newsCategory 实体对象
	*/
	@PostMapping("/saveNewsCategory")
	public NewsCategory saveNewsCategory(@RequestBody NewsCategory newsCategory){
		return this.newsCategoryService.saveNewsCategory(newsCategory);
	}

	/**
	* 修改保存新闻类别
	*
	* @param newsCategory 实体对象
	*/
	@PostMapping("/updateNewsCategory")
	public NewsCategory updateNewsCategory(@RequestBody NewsCategory newsCategory){
		return this.newsCategoryService.updateNewsCategory(newsCategory);
	}

	/**
	* 根据ID删除新闻类别
	*
	* @param newsCategoryId ID
	*/
	@PostMapping("/deleteNewsCategory")
	public void deleteNewsCategory(@RequestParam Long newsCategoryId){
		this.newsCategoryService.deleteNewsCategory(newsCategoryId);
	}



}

