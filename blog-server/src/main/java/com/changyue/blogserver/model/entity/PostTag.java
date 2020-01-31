package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: blog-server
 * @description: 文章标签
 * @author: ChangYue
 * @create: 2020-01-21 08:49
 */
@EqualsAndHashCode(callSuper = true)
@Data

public class PostTag extends BaseEntity {


    private Integer id;


    private Integer postId;


    private Integer tagId;

    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
    }

}
