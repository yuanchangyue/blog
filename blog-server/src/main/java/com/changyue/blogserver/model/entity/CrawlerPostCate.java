package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的分类
 * @date : 2020/3/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CrawlerPostCate extends BaseEntity {

    private Integer id;

    /**
     * 爬虫文章的分类icon
     */
    private String icon;

    /**
     * 爬虫文章的分类简述
     */
    private String brief;

    /**
     * 爬虫文章的分类site id集合
     */
    private String siteIds;

    /**
     * 爬虫文章的分类名称
     */
    private String name;

}
