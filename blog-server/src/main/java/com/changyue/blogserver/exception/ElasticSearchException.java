package com.changyue.blogserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : 袁阊越
 * @description : es操作异常类
 * @date : 2020/3/22
*/
public class ElasticSearchException extends BlogException {

    public ElasticSearchException(String message) {
        super(message);
    }

    public ElasticSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
