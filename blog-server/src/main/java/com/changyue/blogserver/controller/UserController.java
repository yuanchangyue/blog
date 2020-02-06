package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.params.UserParam;
import com.changyue.blogserver.model.rep.CommonReturnType;
import com.changyue.blogserver.serivce.UserService;
import com.changyue.blogserver.validator.ValidatorImpl;
import com.changyue.blogserver.validator.ValidatorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : 袁阊越
 * @description : 用户控制层
 * @date : 2020/2/5/005
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidatorImpl validator;


    @CrossOrigin
    @PostMapping
    public CommonReturnType<Object> createUser(@RequestBody UserParam userParam) {

        //检查入参
        ValidatorResult validatorResult = this.validator.validator(userParam);
        if (validatorResult.isHasError()) {
            return CommonReturnType.create(HttpStatus.BAD_REQUEST.value(), validatorResult.getErrorMsgMap());
        }

        //创建用户
        userService.createBy(userParam);

        return CommonReturnType.create("用户注册成功");
    }

}
