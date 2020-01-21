package com.changyue.blogserver.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: blog-server
 * @description: 标签
 * @author: 袁阊越
 * @create: 2020-01-20 21:22
 */
@Data
public class TagDTO {

    private Integer id;

    private String name;

    private String slugName;

    private Date createTime;

}
