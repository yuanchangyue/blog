package com.changyue.blogserver.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * 文章评论
 */
@Data
public class CrawlerPostComment {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 文章ID
     */
    private Integer newsId;

    /**
     * 评论人
     */
    private String username;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论日期
     */
    private Date commentDate;

    /**
     * 创建日期
     */
    private Date createTime;

}
