package com.changyue.blogserver.model.entity;

import lombok.Data;

@Data
public class Category {

    private Integer id;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 类别别名
     */
    private String slugName;

    /**
     * 描述
     */
    private String description;

    /**
     * 父类别
     */
    private Integer parentId;

    /**
     * 用户ID
     */
    private Integer userId;

}
