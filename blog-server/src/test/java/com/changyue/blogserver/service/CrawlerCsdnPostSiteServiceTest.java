package com.changyue.blogserver.service;

import com.changyue.blogserver.serivce.CrawlerPostSiteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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
    public void testListAll() {
        Objects.requireNonNull(crawlerPostSiteService.listAll()).forEach(System.out::println);
    }

    @Test
    public void test() {
        List<String> strings = crawlerPostSiteService.listIds();
        System.out.println(strings);
    }
}
