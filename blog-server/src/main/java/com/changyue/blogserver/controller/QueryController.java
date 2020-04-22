package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.elsatic.Article;
import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import com.changyue.blogserver.model.params.SiteQuery;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.model.vo.QueryVO;
import com.changyue.blogserver.model.vo.SiteVO;
import com.changyue.blogserver.serivce.CrawlerPostSiteService;
import com.changyue.blogserver.serivce.CrawlerSmartisonService;
import com.changyue.blogserver.serivce.ElasticsearchService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 博客搜索控制层
 * @date : 2020-04-19 18:10
 */
@RestController
@RequestMapping("/api/query")
public class QueryController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private CrawlerSmartisonService crawlerSmartisonService;

    @Autowired
    private CrawlerPostSiteService crawlerPostSiteService;

    @GetMapping("/full/{query}")
    public Result fullTextQuery(@PathVariable("query") String query) {

        //全文搜索
        List<Article> articles = elasticsearchService.searchArticle(query);

        //爬虫文章
        List<CrawlerSmartisonPost> crawlerSmartisonPosts = crawlerSmartisonService.getSimplyPost(query);

        //爬虫站点
        SiteQuery siteQuery = new SiteQuery();
        siteQuery.setKeyWords(query);
        PageInfo<SiteVO> siteVOPageInfo = crawlerPostSiteService.listByAll(1, 8, siteQuery);
        List<SiteVO> siteVOPageInfoList = siteVOPageInfo.getList();

        //构造结果
        QueryVO queryVO = new QueryVO();
        queryVO.setArticles(articles);
        queryVO.setCrawlerSmartisonPosts(crawlerSmartisonPosts);
        queryVO.setSiteVOList(siteVOPageInfoList);

        return Result.create(queryVO);
    }

}
