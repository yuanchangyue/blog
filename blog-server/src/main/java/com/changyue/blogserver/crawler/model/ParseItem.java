package com.changyue.blogserver.crawler.model;


import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 解析封装对象
 * @date : 2020/3/23
 */
@Data
public abstract class ParseItem {
    /**
     * 处理类型 有正向 反向两种
     * FORWARD, 正向 REVERSE 反向
     */
    private String handelType = null;
    /**
     * 文档抓取类型
     */
    private String documentType = null;
    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 获取初始的URL
     */
    public abstract String getInitialUrl();

    /**
     * 获取需要处理的内容
     */
    public abstract String getParserContent();

}
