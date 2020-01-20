package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Entity
@Table(name = "photos")
@EqualsAndHashCode(callSuper = true)
public class Photo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 照片名称
     */
    @Column(name = "name", columnDefinition = "varchar(255) not null")
    private String name;

    /**
     * 照片描述
     */
    @Column(name = "description", columnDefinition = "varchar(255) default ''")
    private String description;

    /**
     * 照相时间
     */
    @Column(name = "take_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime takeTime;

    /**
     * 照片地点
     */
    @Column(name = "location", columnDefinition = "varchar(255) default ''")
    private String location;

    /**
     * 收略图
     */
    @Column(name = "thumbnail", columnDefinition = "varchar(1023) default ''")
    private String thumbnail;

    /**
     * 照片地址
     */
    @Column(name = "url", columnDefinition = "varchar(1023) not null")
    private String url;

    /**
     * 照片组名
     */
    @Column(name = "team", columnDefinition = "varchar(255) default ''")
    private String team;

    @Override
    public void prePersist() {
        super.prePersist();
        id = null;

        if (takeTime == null) {
            takeTime = this.getCreateTime();
        }

        if (description == null) {
            description = "";
        }

        if (location == null) {
            location = "";
        }

        if (thumbnail == null) {
            thumbnail = "";
        }

        if (team == null) {
            team = "";
        }
    }
}
