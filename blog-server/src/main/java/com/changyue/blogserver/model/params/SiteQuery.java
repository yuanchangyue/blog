package com.changyue.blogserver.model.params;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 站点查找
 * @date : 2020-03-30 21:49
 */
@Data
public class SiteQuery {
    /**
     * 关键词
     */
    private String keyWords;

    /**
     * 分类id
     */
    private Integer cateId;
}
