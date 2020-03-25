package com.changyue.blogserver.process;

import com.changyue.blogserver.crawler.model.ProcessFlowData;
import com.changyue.blogserver.model.enums.CrawlerStatus;

/**
 * @author : 袁阊越
 * @description : 业务流
 * @date : 2020-03-23 22:19
 */
public interface ProcessFlow {

    /**
     * 获取优先级
     *
     * @return 优先级
     */
    int getPriority();

    /**
     * 处理业务
     */
    void handle(ProcessFlowData processFlowData);

    /**
     * 获取抓取类型
     */
    CrawlerStatus.ComponentType getComponentType();

}
