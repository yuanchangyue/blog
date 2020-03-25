package com.changyue.blogserver.service;

import com.changyue.blogserver.model.entity.CrawlerPostAdditional;
import com.changyue.blogserver.serivce.CrawlerPostAdditionalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description : 爬虫代理ip池业务逻辑接口测试
 * @date : 2020-03-25 16:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerPostAdditionalServiceTest {

    @Autowired
    private CrawlerPostAdditionalService crawlerPostAdditionalService;

    @Test
    public void testSaveAdditional() {
        CrawlerPostAdditional crawlerPostAdditional = new CrawlerPostAdditional();
        crawlerPostAdditional.setUrl("www.baidu.com");
        crawlerPostAdditional.setCollection(12);
        crawlerPostAdditional.setLikes(12);
        crawlerPostAdditional.setForward(1);
        crawlerPostAdditionalService.saveAdditional(crawlerPostAdditional);
    }

    @Test
    public void testCheckExist() {
        System.out.println("crawlerPostAdditionalService.checkExist(\"www.baidu.com\") = " + crawlerPostAdditionalService.checkExist("www.baidu.com"));
    }


    @Test
    public void test() {
        CrawlerPostAdditional crawlerPostAdditional = new CrawlerPostAdditional();
        crawlerPostAdditional.setUrl("www.baidu.com");
        crawlerPostAdditionalService.getList(crawlerPostAdditional).forEach(System.out::println);
    }

}
