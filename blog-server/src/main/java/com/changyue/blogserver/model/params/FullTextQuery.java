package com.changyue.blogserver.model.params;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 首页搜索属性
 * @date : 2020-03-22 20:59
 */
@Data
public class FullTextQuery {

    private String originalContent;

    private String title;

}
