package com.changyue.blogserver.model.base;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: blog-server
 * @description:
 * @author: ChangYue
 * @create: 2020-01-20 14:52
 */
@Data
public class BaseEntity {
    /**
     * 创建时间
     */
    @Column(name = "create_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 删除标识
     */
    @Column(name = "deleted", columnDefinition = "TINYINT default 0")
    private Boolean deleted = false;

    /**
     * 与业务无关值默认附上初值
     */
    @PrePersist
    protected void prePersist() {
        deleted = false;
        Date now = new Date();
        if (createTime == null) {
            createTime = now;
        }
        if (updateTime == null) {
            updateTime = now;
        }
    }

    @PreUpdate
    protected void preUpdate() {
        updateTime = new Date();
    }

    @PreRemove
    protected void preRemove() {
        updateTime = new Date();
    }


}
