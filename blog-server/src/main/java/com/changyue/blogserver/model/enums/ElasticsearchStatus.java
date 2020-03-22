package com.changyue.blogserver.model.enums;

/**
 * @author : 袁阊越
 * @description : es存入类型
 * @date : 2020-03-22 19:36
 */
public enum ElasticsearchStatus {

    CREATION(0),
    CRAWLER(1);

    private int code;

    ElasticsearchStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
