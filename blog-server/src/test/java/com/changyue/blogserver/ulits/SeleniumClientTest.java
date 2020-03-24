package com.changyue.blogserver.ulits;


import com.changyue.blogserver.crawler.model.CrawlerHtml;
import com.changyue.blogserver.utils.crawler.SeleniumClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SeleniumClientTest {

    @Autowired
    private SeleniumClient seleniumClient;

    @Test
    public void test(){
        CrawlerHtml crawlerHtml =  seleniumClient.getCrawlerHtml("http://www.baidu.com",null,null);
        System.out.println(crawlerHtml.getHtml());
    }

}
