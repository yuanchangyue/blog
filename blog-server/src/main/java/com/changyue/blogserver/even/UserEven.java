package com.changyue.blogserver.even;

import org.springframework.context.ApplicationEvent;

import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

/**
 * @program: blog-server
 * @description: 用户事件
 * @author: ChangYue
 * @create: 2020-01-20 17:40
 */
public class UserEven extends ApplicationEvent {

    private final Integer userId;

    public UserEven(Object source, @NotNull Integer userId) {
        super(source);
        Assert.notNull(userId, "用户的Id,不能为空");
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}
