package com.mt.cms.dao.newsArticles;

import com.mt.cms.entity.newsArticles.NewsArticles;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "newsArticlesDao")
public interface NewsArticlesDao {

    /**
    * 根据分页参数查询新闻文章集合
    *
    * @param pageDTO 分页条件
    */
    public List<NewsArticles> findNewsArticless(PageDTO pageDTO);

    /**
    * 查询全部新闻文章集合
    *
    */
    public List<NewsArticles> findAllNewsArticless();



    /**
    * 根据分页参数查询新闻文章集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findNewsArticlesTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的新闻文章
    *
    * @param newsArticlesId Id
    */
    public NewsArticles findNewsArticles(@Param("newsArticlesId") Long newsArticlesId);

//    /**
//    * 根据ID查询指定的新闻文章(包含外键)
//    *
//    * @param newsArticlesId Id
//    */
//    public NewsArticles findNewsArticlesWithForeignName(@Param("newsArticlesId") Long newsArticlesId);

    /**
    * 新增新闻文章
    *
    * @param newsArticles 实体对象
    */
    public Long saveNewsArticles(NewsArticles newsArticles);

    /**
    * 更新新闻文章
    *
    * @param newsArticles 实体对象
    */
    public Long updateNewsArticles(NewsArticles newsArticles);

    /**
    * 根据ID删除新闻文章
    *
    * @param newsArticlesId ID
    */
    public Long deleteNewsArticles(@Param("newsArticlesId") Long newsArticlesId);

    List<NewsArticles> findNewsArticlessMy();

    NewsArticles findNewsArticlesWithForeignName(Long newsArticlesId);
}
