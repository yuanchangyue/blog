package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.even.UserEven;
import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.params.UserParam;
import com.changyue.blogserver.repository.UserRepository;
import com.changyue.blogserver.serivce.UserService;
import com.changyue.blogserver.serivce.base.CurdServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @program: blog-server
 * @description: 用户业务实现
 * @author: ChangYue
 * @create: 2020-01-20 16:24
 */
@Service
public class UserServiceImpl extends CurdServiceImpl<User, Integer> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Optional<User> getCurrentUser() {
        return Optional.empty();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public User getByUsernameOfNonNull(String username) {
        return null;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public User getByEmailOfNonNull(String email) {
        return null;
    }

    @Override
    public User updatePassword(String oldPassword, String newPassword, Integer userId) {
        return null;
    }

    @Override
    public User createBy(UserParam userParam) {

        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        User saveUser = userRepository.save(user);
        applicationEventPublisher.publishEvent(new UserEven(this, saveUser.getId()));
        return user;
    }

    @Override
    public boolean passwordMatch(User user, String plainPassword) {
        return false;
    }
}
