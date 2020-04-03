package com.changyue.blogserver.model.enums;

/**
 * @author : 袁阊越
 * @description : 评论状态
 * @date : 2020-04-03 15:35
 */

public enum CommentState {
    /**
     * 已审核
     */
    CHECKED(1),
    /**
     * 未审核
     */
    NOT_CHECKED(0);

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    CommentState(Integer code) {
        this.code = code;
    }
}
