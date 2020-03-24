package com.changyue.blogserver.process.processor.impl;

import com.changyue.blogserver.crawler.model.CrawlerConfigProperty;
import com.changyue.blogserver.model.enums.CrawlerEnum;
import com.changyue.blogserver.process.processor.AbstractCrawlerPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 初始化页面爬虫处理
 * @date : 2020-03-24 15:24
 */
@Component
public class CrawlerInitPageProcessor extends AbstractCrawlerPageProcessor {

    @Autowired
    private CrawlerConfigProperty crawlerConfigProperty;

    @Override
    public void handle(Page page) {

        //初始的爬虫Xpath
        String initCrawlerXpath = crawlerConfigProperty.getInitCrawlerXpath();
        //获取下一页的url
        List<String> helpUrl = page.getHtml().xpath(initCrawlerXpath).links().all();

        addSpiderRequest(helpUrl, page.getRequest(), CrawlerEnum.DocumentType.HELP);
    }

    /**
     * 是否需要处理类型
     *
     * @param handleType 处理类型
     */
    @Override
    public boolean isNeedHandlerType(String handleType) {
        return CrawlerEnum.HandelType.FORWARD.name().equals(handleType);
    }

    /**
     * 是否需要文档类型
     *
     * @param documentType 文档类型
     */
    @Override
    public boolean isNeedDocumentType(String documentType) {
        return CrawlerEnum.DocumentType.INIT.name().equals(documentType);
    }

    @Override
    public int getPriority() {
        return 100;
    }
}
