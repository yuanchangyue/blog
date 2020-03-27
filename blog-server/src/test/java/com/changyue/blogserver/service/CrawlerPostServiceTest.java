package com.changyue.blogserver.service;

import com.changyue.blogserver.model.entity.CrawlerPost;
import com.changyue.blogserver.serivce.CrawlerPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的业务接口层测试类
 * @date : 2020-03-26 15:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerPostServiceTest {

    @Autowired
    private CrawlerPostService crawlerPostService;

    @Test
    public void testSavePost() {

        CrawlerPost crawlerPost = new CrawlerPost();
        crawlerPost.setCreateTime(new Date());
        crawlerPost.setChannelId(2);
        crawlerPost.setContent("test");
        crawlerPost.setLabelIds("1,2");
        crawlerPost.setOriginalTime(new Date());
        crawlerPost.setStatus((byte) 1);
        crawlerPost.setName("123123");
        crawlerPost.setTitle("title");
        crawlerPost.setPublishTime(new Date());

        crawlerPostService.savePost(crawlerPost);
    }

}
