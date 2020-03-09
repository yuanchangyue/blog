package com.changyue.blogserver.utils;

import com.changyue.blogserver.model.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author : 袁阊越
 * @description : 权限工具
 * @date : 2020-03-09 17:10
 */
public class ShiroUtils {

    public static User getUser() {
        Subject subject = SecurityUtils.getSubject();
        return (User) subject.getPrincipal();
    }
}
