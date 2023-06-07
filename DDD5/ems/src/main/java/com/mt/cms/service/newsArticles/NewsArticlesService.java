package com.mt.cms.service.newsArticles;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.cms.entity.newsArticles.NewsArticles;

import java.text.ParseException;
import java.util.List;

public interface NewsArticlesService {
    List<NewsArticles> findAllNewsArticlessMy();

    /**
     * 根据分页参数查询新闻文章集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findNewsArticless(PageDTO pageDTO);

    /**
     * 查询全部新闻文章集合
     *
     */
    public List<NewsArticles> findAllNewsArticless();

    /**
     * 根据ID查询指定的新闻文章
     *
     * @param newsArticlesId Id
     */
    public NewsArticles findNewsArticles(Long newsArticlesId);


    /**
     * 新增新闻文章
     *
     * @param newsArticles 实体对象
     */
    public NewsArticles saveNewsArticles(NewsArticles newsArticles) throws ParseException;

    /**
     * 更新新闻文章
     *
     * @param newsArticles 实体对象
     */
    public NewsArticles updateNewsArticles(NewsArticles newsArticles);

    /**
     * 根据ID删除新闻文章
     *
     * @param newsArticlesId ID
     */
    public void deleteNewsArticles(Long newsArticlesId);

    NewsArticles findNewsArticlesWithForeignName(Long newsArticlesId);
}
