package com.changyue.blogserver.service;

import com.changyue.blogserver.model.entity.CrawlerIpPool;
import com.changyue.blogserver.serivce.CrawlerIpPoolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 爬虫代理ip池业务逻辑接口测试
 * @date : 2020-03-25 16:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerCrawlerIpPoolServiceTest {

    @Autowired
    private CrawlerIpPoolService crawlerIpPoolService;

    @Test
    public void testSaveCrawlerIpPool() {
        CrawlerIpPool crawlerIpPool = new CrawlerIpPool();
        crawlerIpPool.setIp("2222.3333.444.5555");
        crawlerIpPool.setPort(1111);
        crawlerIpPool.setIsEnable(true);
        crawlerIpPool.setCreateTime(new Date());
        crawlerIpPoolService.saveCrawlerIpPool(crawlerIpPool);
    }

    @Test
    public void testCheckExist() {
        System.out.println(crawlerIpPoolService.checkExist("2222.3333.444.5555", 1111));
    }

}
