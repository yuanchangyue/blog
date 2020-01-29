package com.changyue.blogserver.model.params;

import lombok.Data;

/**
 * @program: blog-server
 * @description: 文章搜索
 * @author: ChangYue
 * @create: 2020-01-21 10:07
 */
@Data
public class PostQuery {
    /**
     * 关键词
     */
    private String keyWords;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 类别
     */
    private Integer categoryId;

}
