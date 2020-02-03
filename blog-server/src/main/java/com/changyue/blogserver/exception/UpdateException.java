package com.changyue.blogserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @program: blog-server
 * @description:
 * @author: 袁阊越
 * @create: 2020-01-20 22:00
 */
public class UpdateException extends BlogException {

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
