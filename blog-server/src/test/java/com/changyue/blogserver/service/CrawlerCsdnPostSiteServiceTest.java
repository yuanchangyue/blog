package com.changyue.blogserver.service;

import com.changyue.blogserver.serivce.CrawlerPostSiteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-28 21:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerCsdnPostSiteServiceTest {

    @Autowired
    private CrawlerPostSiteService crawlerPostSiteService;

    @Test
    public void test() {
        Objects.requireNonNull(crawlerPostSiteService.listAll()).forEach(System.out::println);
    }

}
