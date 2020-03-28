package com.changyue.blogserver.service;

import com.changyue.blogserver.model.entity.CrawlerPostCate;
import com.changyue.blogserver.serivce.CrawlerPostCateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-28 20:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerCsdnPostCateServiceTest {
    @Autowired
    private CrawlerPostCateService crawlerPostCateService;

    @Test
    public void saveCate() {

        CrawlerPostCate crawlerPostCate = new CrawlerPostCate();
        crawlerPostCate.setId(123);
        crawlerPostCate.setSiteIds("123");
        crawlerPostCate.setIcon("34");
        crawlerPostCate.setName("234");

        crawlerPostCateService.create(crawlerPostCate);

    }
}
