package com.changyue.blogserver.service;

import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.params.UserParam;
import com.changyue.blogserver.serivce.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


/**
 * @program: blog-server
 * @description: 用户业务测试
 * @author: 袁阊越
 * @create: 2020-01-20 19:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateBy() {
        UserParam userParam = new UserParam();
        userParam.setUsername("tom");
        userParam.setAvatar("avatar");
        userParam.setEmail("@qq.com");
        userParam.setDescription("description");
        userParam.setNickname("tom");
        userParam.setPassword("123123");
        userService.createBy(userParam);
    }

    @Test
    public void test() {
       User changyue = userService.getByUsername("changyue");
        System.out.println(changyue);
    }

}
