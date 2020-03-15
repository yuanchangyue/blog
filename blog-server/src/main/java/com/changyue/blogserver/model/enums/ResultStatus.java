package com.changyue.blogserver.model.enums;

/**
 * @author : 袁阊越
 * @description : 返回结果
 * @date : 2020-03-14 20:50
 */
public enum ResultStatus {

    USER_LOGIN_SUCCESS(1000, "登录成功"),
    USER_LOGIN_ERROR(2000, "登录失败,用户名或者密码不正确！"),
    SUCCESS(200, "请求成功"),
    PARAMETER_ERROR(1001, "请求参数有误!"),
    UNKNOWN_ERROR(9999, "未知的错误!"),
    OPERATION_ERROR(1002, "操作失败"),
    OPERATION_SUCCESS(2002, "操作成功");

    private int code;
    private String msg;

    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
