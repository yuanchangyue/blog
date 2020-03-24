package com.changyue.blogserver.model.elsatic;


import lombok.Data;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : es 文章类
 * @date : 2020/2/18/018
 */
@Data
public class Article {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String originalContent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 标签（区分来源）
     */
    private Integer tag;

}
