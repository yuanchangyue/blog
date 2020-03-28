package com.changyue.blogserver.process.processor;

import com.changyue.blogserver.crawler.helper.CrawlerHelper;
import com.changyue.blogserver.process.ProcessFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫的流程管理器
 * @date : 2020-03-24 17:16
 */
@Component
public class CrawlerPostManager {

    @Autowired
    private CrawlerHelper crawlerHelper;

    @Resource
    private List<AbstractCrawlerPageProcessor> abstractCrawlerPageProcessors;

    /**
     * 初始化顺序  小->大
     */
    @PostConstruct
    public void initProcessingFlow() {
        if (null != abstractCrawlerPageProcessors && !abstractCrawlerPageProcessors.isEmpty()) {
            abstractCrawlerPageProcessors.sort((Comparator<ProcessFlow>) (o1, o2) -> {
                if (o1.getPriority() > o2.getPriority()) {
                    return 1;
                } else if (o1.getPriority() > o2.getPriority()) {
                    return -1;
                }
                return 0;
            });
        }
    }

    /**
     * 判断是否当前页面
     *
     * @param page 页面
     */
    public void handle(Page page) {

        String handleType = crawlerHelper.getHandleType(page.getRequest());
        String documentType = crawlerHelper.getDocumentType(page.getRequest());

        for (AbstractCrawlerPageProcessor processor : abstractCrawlerPageProcessors) {
            boolean needDocumentType = processor.isNeedDocumentType(documentType);
            boolean needHandlerType = processor.isNeedHandlerType(handleType);
            if (needDocumentType && needHandlerType) {
                processor.handle(page);
            }
        }
    }

}
