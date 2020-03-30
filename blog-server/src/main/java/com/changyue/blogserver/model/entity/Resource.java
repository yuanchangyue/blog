package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020/3/29
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseEntity {

    private Integer id;

    private Integer parentId;

    private String name;

    private String url;

    private String permission;

    private String component;

    private String icon;

}
