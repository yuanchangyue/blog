package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSite extends BaseEntity {

    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 站点id
     */
    private Integer siteId;

}
