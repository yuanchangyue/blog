package com.changyue.blogserver.model.enums;

/**
 * @author : 袁阊越
 * @description : 文章状态
 * @date : 2020-02-03 13:21
 */
public enum PostStatus {

    /**
     * 发布状态
     */
    PUBLISHED(0),

    /**
     * 草稿状态
     */
    DRAFT(1),

    /**
     * 回收站状态
     */
    RECYCLE(2),

    /**
     * 加密状态
     */
    Encrypt(3);

    private int statusCode;

    PostStatus(int statusCode) {
        this.statusCode = statusCode;
    }


    public int getStatusCode() {
        return statusCode;
    }


}
