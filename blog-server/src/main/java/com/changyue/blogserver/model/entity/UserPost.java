package com.changyue.blogserver.model.entity;

import lombok.Data;

@Data
public class UserPost {


    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户创作文章id
     */
    private Integer postId;

    /**
     * 爬虫文章id
     */
    private Integer crawlerPostId;

}
