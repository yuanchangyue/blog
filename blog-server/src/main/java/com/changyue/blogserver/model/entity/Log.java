package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data

public class Log extends BaseEntity {


    private Long id;

    /**
     * 日志类别
     */

    private String type;

    /**
     * 日志内容
     */

    private String content;

    /**
     * 操作者的ip地址
     */

    private String ipAddress;


    @Override
    public void prePersist() {
        super.prePersist();
        id = null;
    }
}
