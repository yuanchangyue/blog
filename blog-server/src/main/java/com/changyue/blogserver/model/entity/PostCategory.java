package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: blog-server
 * @description: 文章类别
 * @author: 袁阊越
 * @create: 2020-01-22 21:09
 */
@Data

@EqualsAndHashCode(callSuper = true)
public class PostCategory extends BaseEntity {

    private Integer id;

    /**
     * 类别id
     */
    private Integer categoryId;

    /**
     * 文章id
     */
    private Integer postId;



}
