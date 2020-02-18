package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : 袁阊越
 * @description : 用户权限关联
 * @date : 2020/2/18/018
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UsersRole extends BaseEntity {

    /**
     * ID
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 权限id
     */
    private Integer roleId;

    public UsersRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

}
