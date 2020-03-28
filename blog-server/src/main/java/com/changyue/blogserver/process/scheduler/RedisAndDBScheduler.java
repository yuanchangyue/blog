package com.changyue.blogserver.process.scheduler;

import com.changyue.blogserver.crawler.helper.CrawlerHelper;
import com.changyue.blogserver.crawler.model.ProcessFlowData;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.ProcessFlow;
import com.changyue.blogserver.serivce.CrawlerPostAdditionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * @author : 袁阊越
 * @description : 去重
 * @date : 2020-03-25 19:52
 */
@Slf4j
public class RedisAndDBScheduler extends RedisScheduler implements ProcessFlow {

    @Autowired
    private CrawlerPostAdditionalService crawlerPostAdditionalService;

    @Autowired
    private CrawlerHelper crawlerHelper;


    public RedisAndDBScheduler(String host) {
        super(host);
    }

    public RedisAndDBScheduler(JedisPool pool) {
        super(pool);
    }

    /**
     * 进行排重
     */
    @Override
    public boolean isDuplicate(Request request, Task task) {

        String handleType = crawlerHelper.getHandleType(request);
        boolean isDuplicate = false;

        if (CrawlerStatus.HandelType.FORWARD.name().equals(handleType)) {
            log.info("url正在排重,url:[{}],handleType:[{}]", request.getUrl(), handleType);
            //第一步：使用redis排重
            isDuplicate = super.isDuplicate(request, task);
            //第二步：数据库排重
            if (!isDuplicate) {
                isDuplicate = crawlerPostAdditionalService.checkExist(request.getUrl());
            }
            log.info("排重完成,url:[{}],handleType:[{}],isDuplicate:[{}]", request.getUrl(), handleType, isDuplicate);
        }

        return isDuplicate;
    }

    @Override
    public int getPriority() {
        return 130;
    }

    @Override
    public void handle(ProcessFlowData processFlowData) {
    }

    @Override
    public CrawlerStatus.ComponentType getComponentType() {
        return CrawlerStatus.ComponentType.SCHEDULER;
    }
}
