package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : 袁阊越
 * @description : 文章标签
 * @date : 2020/2/3/003
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostTag extends BaseEntity {


    private Integer id;

    /**
     * 文章id
     */
    private Integer postId;

    /**
     * 标签id
     */
    private Integer tagId;

    public PostTag(Integer postId, Integer tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }
}
