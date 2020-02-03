package com.changyue.blogserver.model.base;

import lombok.Data;

import java.util.Date;

/**
 * @author : ChangYue
 * @date : 2020-01-20 14:52
 * @description : 通用实体
 */
@Data
public class BaseEntity {

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标识
     */
    private Boolean deleted = false;

    /**
     * 与业务无关值默认附上初值
     */
    public BaseEntity() {
        Date now = new Date();
        if (createTime == null) {
            createTime = now;
        }
        if (updateTime == null) {
            updateTime = now;
        }
    }
}
