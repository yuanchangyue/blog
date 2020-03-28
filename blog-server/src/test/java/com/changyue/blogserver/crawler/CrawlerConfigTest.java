package com.changyue.blogserver.crawler;

import com.changyue.blogserver.config.CrawlerConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-28 13:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerConfigTest {
    @Autowired
    private CrawlerConfig crawlerConfig;

    @Test
    public void test() {
        crawlerConfig.getSmartisonUrlList().forEach(System.out::println);
    }
}
