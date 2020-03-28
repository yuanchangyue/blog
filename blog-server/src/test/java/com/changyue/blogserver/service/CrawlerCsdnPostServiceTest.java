package com.changyue.blogserver.service;

import com.changyue.blogserver.model.entity.CrawlerCsdnPost;
import com.changyue.blogserver.serivce.CrawlerCsdnPostService;
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
public class CrawlerCsdnPostServiceTest {

    @Autowired
    private CrawlerCsdnPostService crawlerCsdnPostService;

    @Test
    public void testSavePost() {

        CrawlerCsdnPost crawlerCsdnPost = new CrawlerCsdnPost();
        crawlerCsdnPost.setCreateTime(new Date());
        crawlerCsdnPost.setChannelId(2);
        crawlerCsdnPost.setContent("test");
        crawlerCsdnPost.setLabelIds("1,2");
        crawlerCsdnPost.setOriginalTime(new Date());
        crawlerCsdnPost.setStatus((byte) 1);
        crawlerCsdnPost.setName("123123");
        crawlerCsdnPost.setTitle("title");
        crawlerCsdnPost.setPublishTime(new Date());

        crawlerCsdnPostService.savePost(crawlerCsdnPost);
    }

}
