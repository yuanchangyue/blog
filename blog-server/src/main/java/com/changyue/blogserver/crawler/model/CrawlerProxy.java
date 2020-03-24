package com.changyue.blogserver.crawler.model;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 代理IP实体类
 * @date : 2020/3/24
 */
@Data
public class CrawlerProxy {

    private String host;

    private Integer port;

    public CrawlerProxy(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 获取代理信息
     */
    public String getProxyInfo() {
        return this.host + ":" + port;
    }

}
