package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : 袁阊越
 * @description : 文章类别
 * @date : 2020/2/3/003
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

    public PostCategory(Integer categoryId, Integer postId) {
        this.categoryId = categoryId;
        this.postId = postId;
    }
}
