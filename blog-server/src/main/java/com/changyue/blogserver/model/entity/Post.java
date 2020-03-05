package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;


/**
 * @author : 袁阊越
 * @description : 文章实体
 * @date : 2020/2/1/001
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户ID
     */
    private Integer userId;
}
