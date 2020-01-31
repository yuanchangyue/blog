package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;


/**
 * Post base entity.
 *
 * @author johnniang
 * @author ryanwang
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity {


    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 文章地址
     */

    private String url;

    /**
     * 原生内容
     */

    private String originalContent;

    /**
     * 格式内容
     */

    private String formatContent;

    /**
     * 文章简介
     */

    private String summary;

    /**
     * 收略图
     */

    private String thumbnail;

    /**
     * 文章访问量
     */

    private Long visits;

    /**
     * 是否允许评价
     */

    private Boolean disallowComment;

    /**
     * 文章密码
     */

    private String password;


    /**
     * 优先权
     */

    private Integer topPriority;


    /**
     * 点赞
     */

    private Long likes;

    /**
     * 编辑时间
     */
    private Date editTime;

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
