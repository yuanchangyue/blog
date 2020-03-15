package com.changyue.blogserver.handler;

import com.changyue.blogserver.model.enums.ResultStatus;
import lombok.Data;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-15 09:13
 */
@Data
public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultStatus resultStatus) {
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMsg();
    }

    public static Result create(Object data) {
        return create(ResultStatus.SUCCESS, data);
    }

    public static Result create(ResultStatus resultStatus, Object data) {
        Result result = new Result(resultStatus);
        result.setData(data);
        return result;
    }

}
