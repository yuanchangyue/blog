package com.changyue.blogserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @program: blog-server
 * @description:
 * @author: 袁阊越
 * @create: 2020-01-20 22:00
 */
public class CreateException extends BlogException {

    public CreateException(String message) {
        super(message);
    }

    public CreateException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
