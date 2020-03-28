package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的附加信息，方便自后进行反向爬虫
 * @date : 2020/3/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CrawlerPostAdditional extends BaseEntity {
    private Integer id;
    /**
     * 文章id
     */
    private Integer newsId;
    /**
     * 地址
     */
    private String url;
    /**
     * 阅读量
     */
    private Integer readCount;
    /**
     * 点赞量
     */
    private Integer likes;
    /**
     * 评论量
     */
    private Integer comment;
    /**
     * 爬取方向
     */
    private Integer forward;
    /**
     * 不喜欢
     */
    private Integer unlikes;
    /**
     * 收藏量
     */
    private Integer collection;

    private Date count;

    /**
     * 下次更新时间
     */
    private Date nextUpdateTime;

    /**
     * 更新次数
     */
    private Integer updateNum;
}
