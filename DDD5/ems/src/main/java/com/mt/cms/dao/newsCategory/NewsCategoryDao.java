package com.mt.cms.dao.newsCategory;

import com.mt.cms.entity.newsCategory.NewsCategory;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "newsCategoryDao")
public interface NewsCategoryDao {

    /**
    * 根据分页参数查询新闻类别集合
    *
    * @param pageDTO 分页条件
    */
    public List<NewsCategory> findNewsCategorys(PageDTO pageDTO);

    /**
    * 查询全部新闻类别集合
    *
    */
    public List<NewsCategory> findAllNewsCategorys();





    /**
    * 根据分页参数查询新闻类别集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findNewsCategoryTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的新闻类别
    *
    * @param newsCategoryId Id
    */
    public NewsCategory findNewsCategory(@Param("newsCategoryId") Long newsCategoryId);


    /**
    * 新增新闻类别
    *
    * @param newsCategory 实体对象
    */
    public Long saveNewsCategory(NewsCategory newsCategory);

    /**
    * 更新新闻类别
    *
    * @param newsCategory 实体对象
    */
    public Long updateNewsCategory(NewsCategory newsCategory);

    /**
    * 根据ID删除新闻类别
    *
    * @param newsCategoryId ID
    */
    public Long deleteNewsCategory(@Param("newsCategoryId") Long newsCategoryId);
}
