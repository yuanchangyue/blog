package com.changyue.blogserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : 袁阊越
 * @description : Bean 操作异常
 * @date : 2020/2/1/001
 */
public class BeanException extends BlogException {

    public BeanException(String message) {
        super(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
