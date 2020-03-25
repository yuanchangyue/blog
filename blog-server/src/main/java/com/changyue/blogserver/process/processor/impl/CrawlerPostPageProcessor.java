package com.changyue.blogserver.process.processor.impl;

import com.changyue.blogserver.crawler.helper.CrawlerHelper;
import com.changyue.blogserver.crawler.model.CrawlerConfigProperty;
import com.changyue.blogserver.crawler.parse.ParseRule;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.processor.AbstractCrawlerPageProcessor;
import com.changyue.blogserver.utils.crawler.ParseRuleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 文章详情页的爬虫处理
 * @date : 2020-03-24 16:51
 */
@Slf4j
@Component
public class CrawlerPostPageProcessor extends AbstractCrawlerPageProcessor {

    @Autowired
    private CrawlerConfigProperty crawlerConfigProperty;

    @Autowired
    private CrawlerHelper crawlerHelper;


    @Override
    public void handle(Page page) {
        //处理类型
        String handleType = crawlerHelper.getHandleType(page.getRequest());

        //开始时间
        long currentTimeMillis = System.currentTimeMillis();

        //请求的url
        String requestUrl = page.getUrl().get();

        log.info("正在解析文章页,当前的url:[{}],处理的类型为：[{}]", requestUrl, handleType);

        //获取文章页的解析规则
        List<ParseRule> targetParseRuleList = crawlerConfigProperty.getTargetParseRuleList();

        //获取有效的规则
        targetParseRuleList = ParseRuleUtils.parseHtmlByRuleList(page.getHtml(), targetParseRuleList);

        if (null != targetParseRuleList && !targetParseRuleList.isEmpty()) {
            for (ParseRule parseRule : targetParseRuleList) {
                log.info("字段:[{}],内容为:[{}]", parseRule.getField(), parseRule.getMergeContent());
                //解析到字段的数据 添加到page中
                page.putField(parseRule.getField(), parseRule.getMergeContent());
            }
        }

        log.info("正在解析文章页,当前的url:[{}],处理的类型为：[{}],用时:[{}]", requestUrl, handleType, System.currentTimeMillis() - currentTimeMillis);
    }

    /**
     * 是否需要处理类型
     *
     * @param handleType 处理类型
     */
    @Override
    public boolean isNeedHandlerType(String handleType) {
        return CrawlerStatus.HandelType.FORWARD.name().equals(handleType);
    }

    /**
     * 是否需要文档类型
     *
     * @param documentType 文档类型
     */
    @Override
    public boolean isNeedDocumentType(String documentType) {
        return CrawlerStatus.DocumentType.PAGE.name().equals(documentType);
    }

    @Override
    public int getPriority() {
        return 120;
    }
}
