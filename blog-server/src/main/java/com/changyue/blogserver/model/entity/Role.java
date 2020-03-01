package com.changyue.blogserver.model.entity;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 权限
 * @date : 2020-02-18 10:32
 */
@Data
public class Role {

    /**
     * 权限id
     */
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

}
