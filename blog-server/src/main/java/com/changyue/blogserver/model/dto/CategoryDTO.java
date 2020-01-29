package com.changyue.blogserver.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: blog-server
 * @description: 类别
 * @author: 袁阊越
 * @create: 2020-01-22 17:26
 */
@Data
public class CategoryDTO {

    private Integer id;

    private String name;

    private String slugName;

    private String description;

    private Integer parentId;

    private Date createTime;

}
