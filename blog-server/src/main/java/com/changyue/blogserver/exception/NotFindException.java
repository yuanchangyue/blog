package com.changyue.blogserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @program: blog-server
 * @description:
 * @author: 袁阊越
 * @create: 2020-01-20 23:25
 */
public class NotFindException extends BlogException {

    public NotFindException(String message) {
        super(message);
    }

    public NotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
