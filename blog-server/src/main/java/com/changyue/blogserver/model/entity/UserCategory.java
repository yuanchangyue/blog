package com.changyue.blogserver.model.entity;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 用户标签实体
 * @date : 2020/3/2
 */
@Data
public class UserCategory {

    /**
     * 用户类别ID
     */
    private Integer userCategoryId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 类别ID
     */
    private Integer categoryId;

    public UserCategory(Integer userId, Integer categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }
}
