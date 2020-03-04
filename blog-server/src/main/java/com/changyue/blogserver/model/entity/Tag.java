package com.changyue.blogserver.model.entity;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 标签
 * @date : 2020/2/18/018
 */
@Data
public class Tag {

    private Integer id;

    /**
     * 标签
     */
    private String name;

    /**
     * 别名
     */
    private String slugName;

    /**
     * 用户id
     */
    private Integer userId;
}

