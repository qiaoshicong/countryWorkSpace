
import axios from 'axios';

/**
 * 新闻文章
 */
export class NewsArticlesService {
  //----2
  static findAll () {
    return axios({
      url: '/cms/newsArticles/NewsArticles/findAll',
      method: 'POST',
    });
  }


  // 分页查询列表---1
    static findNewsArticless (queryParams) {
        return axios({
            url: '/cms/newsArticles/NewsArticles/findNewsArticless',
            method: 'POST',
            data: queryParams
        });
    }

    //根据ID查询指定的新闻文章
    static findNewsArticles (newsArticlesId) {
        const query = {
            newsArticlesId
        }
        return axios({
            url: '/cms/newsArticles/NewsArticles/findNewsArticles',
            method: 'POST',
            params: query,
        });
    }

    //根据ID查询指定的新闻文章(包含外键名称)
    static findNewsArticlesForView (newsArticlesId) {
        const query = {
            newsArticlesId
        }
        return axios({
            url: '/cms/newsArticles/NewsArticles/findNewsArticlesForView',
            method: 'POST',
            params: query,
        });
    }


    //新增保存新闻文章
    static saveNewsArticles (newsArticles) {
        const query = {}
        return axios({
            url: '/cms/newsArticles/NewsArticles/saveNewsArticles',
            method: 'POST',
            params: query,
            data: newsArticles
        });
    }

    //修改保存新闻文章
    static updateNewsArticles (newsArticles) {
        const query = {}
        return axios({
            url: '/cms/newsArticles/NewsArticles/updateNewsArticles',
            method: 'POST',
            params: query,
            data: newsArticles
        });
    }

    //根据ID查询指定的新闻文章(只提取ID 和 Name)     -----3
    static deleteNewsArticles (newsArticlesId) {
        const query = {
            newsArticlesId
        }
        return axios({
            url: '/cms/newsArticles/NewsArticles/deleteNewsArticles',
            method: 'POST',
            params: query,
        });
    }
}
