package com.changyue.blogserver.even;

import org.springframework.context.ApplicationEvent;

/**
 * @program: blog-server
 * @description:
 * @author: ChangYue
 * @create: 2020-01-20 17:40
 */
public class UserEven extends ApplicationEvent {
    public UserEven(Object source) {
        super(source);
    }
}
