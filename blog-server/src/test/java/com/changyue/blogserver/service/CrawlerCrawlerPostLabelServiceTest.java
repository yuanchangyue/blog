package com.changyue.blogserver.service;

import com.changyue.blogserver.serivce.CrawlerPostLabelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description : 爬虫文章标签业务测试类
 * @date : 2020-03-25 15:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerCrawlerPostLabelServiceTest {

    @Autowired
    private CrawlerPostLabelService crawlerPostLabelService;

    @Test
    public void testGetPostLabelIdByLabelName() {
        String postLabelIdByLabelName = crawlerPostLabelService.getPostLabelIdByLabelName("java,docker,python");
        System.out.println(postLabelIdByLabelName);
    }

    @Test
    public void testGetPostChannelByLabelIds() {
        Integer channelByLabelIds = crawlerPostLabelService.getPostChannelByLabelIds("1,2");
        System.out.println("channelByLabelIds = " + channelByLabelIds);
    }

}
