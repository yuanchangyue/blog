package com.changyue.blogserver.service;

import com.changyue.blogserver.serivce.QuartzService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description : 定时任务业务接口测试
 * @date : 2020-03-27 16:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartzServiceTest {

    @Autowired
    private QuartzService quartzService;

    @Test
    public void test() {

        //quartzService.addJob(HelloJob.class, "job", "test", "0/30 * * * * ?", null);
        //quartzService.runAJobNow("job","test");
        //quartzService.addJob(ForwardCrawlerJob.class, "ForwardCrawlerJob", "crawler", "0/5 * * * * ?", null);
        //quartzService.addJob(ReverseCrawlerJob.class, "ReverseCrawlerJob", "crawler", "0/8 * * * * ?", null);

        quartzService.pauseJob("ReverseCrawlerJob", "crawler");

    }

}
