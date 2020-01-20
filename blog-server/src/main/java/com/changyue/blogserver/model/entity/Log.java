package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "logs")
public class Log extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日志类别
     */
    @Column(name = "type", columnDefinition = "int not null")
    private String type;

    /**
     * 日志内容
     */
    @Column(name = "content", columnDefinition = "varchar(1023) not null")
    private String content;

    /**
     * 操作者的ip地址
     */
    @Column(name = "ip_address", columnDefinition = "varchar(127) default ''")
    private String ipAddress;


    @Override
    public void prePersist() {
        super.prePersist();
        id = null;
    }
}
