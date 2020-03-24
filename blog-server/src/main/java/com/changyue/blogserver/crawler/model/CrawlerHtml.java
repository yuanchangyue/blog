package com.changyue.blogserver.crawler.model;


import lombok.Data;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫的HTML
 * @date : 2020/3/24
 */
@Data
public class CrawlerHtml {

    /**
     * 地址
     */
    private String url;

    /**
     * html
     */
    private String html;

    /**
     * 代理
     */
    private CrawlerProxy proxy;

    private List<CrawlerCookie> crawlerCookieList = null;

    public CrawlerHtml() {
    }

    public CrawlerHtml(String url) {
        this.url = url;
    }

}
