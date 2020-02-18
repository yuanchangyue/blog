package com.changyue.blogserver.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 类别
 * @date : 2020/2/15/015
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
