package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Post base entity.
 *
 * @author johnniang
 * @author ryanwang
 */
@Data
@Entity()
@Table(name = "posts")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "int default 0")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文章标题
     */
    @Column(name = "title", columnDefinition = "varchar(100) not null")
    private String title;

    /**
     * 文化状态
     */
    @Column(name = "status", columnDefinition = "int default 1")
    private Integer status;

    /**
     * 文章地址
     */
    @Column(name = "url", columnDefinition = "varchar(255) not null", unique = true)
    private String url;

    /**
     * 原生内容
     */
    @Column(name = "original_content", columnDefinition = "longtext not null")
    private String originalContent;

    /**
     * 格式内容
     */
    @Column(name = "format_content", columnDefinition = "longtext not null")
    private String formatContent;

    /**
     * 文章简介
     */
    @Column(name = "summary", columnDefinition = "varchar(500) default ''")
    private String summary;

    /**
     * 收略图
     */
    @Column(name = "thumbnail", columnDefinition = "varchar(1023) default ''")
    private String thumbnail;

    /**
     * 文章访问量
     */
    @Column(name = "visits", columnDefinition = "bigint default 0")
    private Long visits;

    /**
     * 是否允许评价
     */
    @Column(name = "disallow_comment", columnDefinition = "int default 0")
    private Boolean disallowComment;

    /**
     * 文章密码
     */
    @Column(name = "password", columnDefinition = "varchar(255) default ''")
    private String password;


    /**
     * 优先权
     */
    @Column(name = "top_priority", columnDefinition = "int default 0")
    private Integer topPriority;


    /**
     * 点赞
     */
    @Column(name = "likes", columnDefinition = "bigint default 0")
    private Long likes;

    /**
     * 编辑时间
     */
    @Column(name = "edit_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editTime;

    @Override
    public void prePersist() {
        super.prePersist();

        id = null;

        if (editTime == null) {
            editTime = getCreateTime();
        }

        if (summary == null) {
            summary = "";
        }

        if (thumbnail == null) {
            thumbnail = "";
        }

        if (disallowComment == null) {
            disallowComment = false;
        }

        if (password == null) {
            password = "";
        }

        if (topPriority == null) {
            topPriority = 0;
        }

        if (visits == null || visits < 0) {
            visits = 0L;
        }

        if (likes == null || likes < 0) {
            likes = 0L;
        }

        if (originalContent == null) {
            originalContent = "";
        }

        if (formatContent == null) {
            formatContent = "";
        }
    }

}
