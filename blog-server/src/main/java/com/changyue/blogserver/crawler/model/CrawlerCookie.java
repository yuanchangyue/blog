package com.changyue.blogserver.crawler.model;

import lombok.Data;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 爬虫的Cookie
 * @date : 2020/3/24
 */
@Data
public class CrawlerCookie {

    /**
     * cookie名称
     */
    private String name;
    /**
     * cookie 值
     */
    private String value;
    /**
     * 域名
     */
    private String domain;
    /**
     * 路径
     */
    private String path;

    /**
     * 过期时间
     */
    private Date expire;

    /**
     * 是否是必须的
     */
    private boolean isRequired;

    /**
     * 校验是否过期
     */
    public boolean isExpire() {
        boolean flag = false;
        if (null != expire) {
            flag = expire.getTime() <= (new Date()).getTime();
        }
        return flag;
    }

    public CrawlerCookie() {
    }

    public CrawlerCookie(String name, boolean isRequired) {
        this.name = name;
        this.isRequired = isRequired;
    }

}
