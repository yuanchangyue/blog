package com.changyue.blogserver.model.vo;

import com.changyue.blogserver.model.entity.CrawlerPostCate;
import lombok.Data;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 站点视图对象
 * @date : 2020-03-30 21:33
 */
@Data
public class SiteVO {

    private String id;

    /**
     * 爬虫文章的站点的图片
     */
    private String pic;

    /**
     * 爬虫文章的站点的简介
     */
    private String brief;

    /**
     * 爬虫文章的站点rss
     */
    private String rssUrl;

    /**
     * 爬虫文章的站点名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 爬虫文章的站点文章数量
     */
    private Integer articleNum;

    /**
     * 文章分类
     */
    private CrawlerPostCate crawlerPostCate;
}
