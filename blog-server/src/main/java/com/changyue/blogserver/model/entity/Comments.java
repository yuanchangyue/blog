package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Comments extends BaseEntity {

    private Long id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 是否admin
     */
    private Byte isAdmin;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 文章id
     */
    private Integer postId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer topPriority;

}
