package com.changyue.blogserver.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 前端菜单栏式图对象
 * @date : 2020-03-29 14:41
 */
@Data
public class MenuVo {

    private Integer id;

    private String name;

    private String url;

    private String icon;

    List<MenuVo> children;

}
