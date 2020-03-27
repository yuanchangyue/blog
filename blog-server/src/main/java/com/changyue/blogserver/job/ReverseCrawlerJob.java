package com.changyue.blogserver.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author : 袁阊越
 * @description : 正向爬虫
 * @date : 2020-03-27 10:45
 */
@Component
@DisallowConcurrentExecution
public class ReverseCrawlerJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("ReverseCrawlerJob");
    }

}
