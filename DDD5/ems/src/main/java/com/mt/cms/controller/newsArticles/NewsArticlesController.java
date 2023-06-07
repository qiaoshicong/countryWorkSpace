

package com.mt.cms.controller.newsArticles;

import com.mt.cms.entity.newsArticles.NewsArticles;
import com.mt.cms.service.newsArticles.NewsArticlesService;
import com.mt.cms.service.publisher.PublisherService;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@Api(tags = "新闻文章")
@RestController
@RequestMapping("/api/cms/newsArticles/NewsArticles")
@CrossOrigin(allowCredentials = "true")
public class NewsArticlesController {
    private static Logger logger = LoggerFactory.getLogger(NewsArticlesController.class);


    @Autowired
    private NewsArticlesService newsArticlesService;
    @Autowired
    private PublisherService publisherService;

    /**
     * 根据分页参数查询新闻文章集合
     * @param pageDTO 分页条件
     */
    @PostMapping("/findNewsArticless")
    public PageResultDTO findNewsArticless(@RequestBody PageDTO pageDTO) {
        return this.newsArticlesService.findNewsArticless(pageDTO);
    }

    /**
     * 根据ID查询指定的新闻文章
     * @param newsArticlesId Id
     */
    @PostMapping("/findNewsArticles")
    public NewsArticles findNewsArticles(@RequestParam Long newsArticlesId) {
        return this.newsArticlesService.findNewsArticles(newsArticlesId);
    }

    /**
     * 查询全部新闻文章
     * @return
     */
    @PostMapping("/findAll")
    public List<NewsArticles> findAllNewsArticlessMy(){
        return this.newsArticlesService.findAllNewsArticlessMy();
    }
    /**
     * 新增保存新闻文章
     * @param newsArticles 实体对象
     */

    /**
     * 根据ID查询指定的新闻文章(包含外键名称)
     *
     * @param newsArticlesId Id
     */
    @PostMapping("/findNewsArticlesForView")
    public NewsArticles findNewsArticlesForView(@RequestParam Long newsArticlesId) {
        return this.newsArticlesService.findNewsArticlesWithForeignName(newsArticlesId);
    }
    @PostMapping("/saveNewsArticles")
    public NewsArticles saveNewsArticles(@RequestBody NewsArticles newsArticles) throws ParseException {
        return this.newsArticlesService.saveNewsArticles(newsArticles);
    }
    /**
     * 修改保存新闻文章
     * @param newsArticles 实体对象
     */

    @PostMapping("/updateNewsArticles")
    public NewsArticles updateNewsArticles(@RequestBody NewsArticles newsArticles) {
        return this.newsArticlesService.updateNewsArticles(newsArticles);
    }

    /**
     * 根据ID删除新闻文章
     * @param newsArticlesId ID
     */
    @PostMapping("/deleteNewsArticles")
    public void deleteNewsArticles(@RequestParam Long newsArticlesId) {
        this.newsArticlesService.deleteNewsArticles(newsArticlesId);
    }


}

