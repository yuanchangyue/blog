package com.changyue.blogserver.crawler.model;


import com.changyue.blogserver.crawler.parse.ParseRule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫配置相关属性
 * @date : 2020/3/24
 */
@Setter
@Getter
public class CrawlerConfigProperty {
    /**
     * 初始化请求
     */
    private List<String> initCrawlerUrlList;
    /**
     * 初始化抓取的xpath表达式
     */
    private String initCrawlerXpath;

    /**
     * 帮助页面抓取规则
     */
    private String helpCrawlerXpath;

    /**
     * 页面分页抓取的范围
     */
    private Integer crawlerHelpNextPagingSize;

    /**
     * 目标页抓取规则
     */
    private List<ParseRule> targetParseRuleList;

}
