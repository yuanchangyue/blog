package com.changyue.blogserver.controller;

import com.changyue.blogserver.serivce.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: blog-server
 * @description: 用户控制层
 * @author: ChangYue
 * @create: 2020-01-20 16:13
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;






}
