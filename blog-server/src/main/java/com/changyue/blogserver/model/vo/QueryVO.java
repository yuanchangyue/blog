package com.changyue.blogserver.model.vo;

import com.changyue.blogserver.model.elsatic.Article;
import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import lombok.Data;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 整体搜索视图对象
 * @date : 2020-04-19 18:37
 */
@Data
public class QueryVO {

    /**
     * 文章列表
     */
    List<Article> articles;

    /**
     * 爬虫文章列表
     */
    List<CrawlerSmartisonPost> crawlerSmartisonPosts;

    /**
     * 站点列表
     */
    List<SiteVO> siteVOList;

}
