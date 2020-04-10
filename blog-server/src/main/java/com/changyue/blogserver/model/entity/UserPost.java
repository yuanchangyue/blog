package com.changyue.blogserver.model.entity;

import lombok.Data;

@Data
public class UserPost {

    private Integer id;

    private Integer userId;

    private Integer postId;

    private Integer crawlerPostId;

}
