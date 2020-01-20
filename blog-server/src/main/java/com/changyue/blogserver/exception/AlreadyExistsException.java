package com.changyue.blogserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @program: blog-server
 * @description:
 * @author: 袁阊越
 * @create: 2020-01-20 22:00
 */
public class AlreadyExistsException extends BlogException {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
