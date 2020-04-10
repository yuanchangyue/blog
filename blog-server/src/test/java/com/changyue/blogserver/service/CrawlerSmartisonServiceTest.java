package com.changyue.blogserver.service;

import com.changyue.blogserver.config.CrawlerConfig;
import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import com.changyue.blogserver.serivce.CrawlerSmartisonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-28 21:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerSmartisonServiceTest {
    @Autowired
    private CrawlerSmartisonService crawlerSmartisonService;

    @Autowired
    private CrawlerConfig crawlerConfig;

    @Test
    public void testSaveSite() {
        List<String> smartisonSiteUrl = crawlerConfig.getSmartisonSiteUrl();
        smartisonSiteUrl.forEach(s -> crawlerSmartisonService.saveSite(s));
    }

    @Test
    public void testSaveArticle() {
        List<String> smartisonArticleUrl = crawlerConfig.getSmartisonArticleUrl();
        smartisonArticleUrl.forEach(s -> crawlerSmartisonService.saveArticle(s));
    }

    @Test
    public void test() {

        List<CrawlerSmartisonPost> crawlerSmartisonPosts = crawlerSmartisonService.randomList();
        System.out.println(crawlerSmartisonPosts);

    }


}
