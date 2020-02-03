package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data


public class Category extends BaseEntity {


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


}
