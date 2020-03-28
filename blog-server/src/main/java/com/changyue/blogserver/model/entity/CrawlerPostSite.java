package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的站点
 * @date : 2020/3/28
 */
@Data
public class CrawlerPostSite extends BaseEntity {

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
     * 爬虫文章的站点文章数量
     */
    private Integer articleNum;

    /**
     * 所属的分类
     */
    private Integer cateId;

}
