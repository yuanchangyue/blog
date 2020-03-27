package com.changyue.blogserver.model.parse;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020/3/26
 */
@Data
public class HtmlLabel {

    /**
     * 解析的数据类型
     */
    private String type;

    /**
     * 标签内容
     */
    private String value;

    /**
     * 设置样式
     */
    private String style;

}
