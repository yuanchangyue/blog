package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {

    private Integer id;

    /**
     * 标签
     */
    private String name;

    /**
     * 别名
     */
    private String slugName;

    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
    }
}
