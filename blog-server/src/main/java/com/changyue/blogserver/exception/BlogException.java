package com.changyue.blogserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

/**
 * @program: blog-server
 * @description: 博客异常
 * @author: 袁阊越
 * @create: 2020-01-20 22:05
 */
public abstract class BlogException extends RuntimeException {

    /**
     * 错误的数据
     */
    private Object errData;

    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getStatus();

    /**
     * 设置错误的数据
     *
     * @param errData 错误数据
     * @return 当前的异常
     */
    public BlogException setErrData(Object errData) {
        this.errData = errData;
        return this;
    }
}
