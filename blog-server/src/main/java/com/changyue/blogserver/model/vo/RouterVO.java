package com.changyue.blogserver.model.vo;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 前端路由视图对象
 * @date : 2020-03-29 15:00
 */
@Data
public class RouterVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String url;

    /**
     * 组件
     */
    private String component;

}
