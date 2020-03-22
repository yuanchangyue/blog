package com.changyue.blogserver.model.params;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 文章搜索
 * @date : 2020/2/3/003
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
