package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.entity.UsersRole;
import com.changyue.blogserver.model.enums.AuthorityStatus;
import com.changyue.blogserver.model.enums.ResultStatus;
import com.changyue.blogserver.model.params.LoginParam;
import com.changyue.blogserver.model.params.UserParam;
import com.changyue.blogserver.serivce.UserRoleService;
import com.changyue.blogserver.serivce.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private UserRoleService userRoleService;

    @PostMapping
    public Result createUser(@Valid @RequestBody UserParam userParam) {

        //创建用户
        User createdUser = userService.createBy(userParam);

        //默认普通用户
        userRoleService.create(new UsersRole(createdUser.getId(), AuthorityStatus.ORDINARY_USER.getAuthorityCode()));

        return Result.create(ResultStatus.OPERATION_SUCCESS);
    }

    @PostMapping("/login")
    public Result loginUser(@Valid @RequestBody LoginParam loginParam) {

        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(loginParam.getUsername(), loginParam.getPassword());
            subject.login(token);
            log.info("session id : [{}]", subject.getSession().getId());
        } catch (AuthenticationException e) {
            log.debug("登陆失败：[{}]", e.getMessage());
            return Result.create(ResultStatus.USER_LOGIN_ERROR, e.getMessage());
        }

        //转换为DTO
        User user = (User) subject.getPrincipal();
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);

        return Result.create(userDTO);
    }

    @RequestMapping("/logout")
    public Result loginUser() {
        Subject subject = SecurityUtils.getSubject();
        log.info("用户已经退出");
        subject.logout();
        return Result.create(ResultStatus.OPERATION_SUCCESS);
    }

}
