package com.changyue.blogserver.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 爬虫的文章标签
 * @date : 2020-03-25 12:05
 */
@Data
public class CrawlerPostLabel {

    /**
     * id
     */
    private Integer id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 类型
     */
    private Boolean type;

    /**
     * 创建时间
     */
    private Date createTime;

}
