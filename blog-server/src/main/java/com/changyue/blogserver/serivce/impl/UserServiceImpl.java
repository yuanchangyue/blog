package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.even.UserEven;
import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.params.UserParam;
import com.changyue.blogserver.repository.UserRepository;
import com.changyue.blogserver.serivce.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @program: blog-server
 * @description: 用户业务实现
 * @author: ChangYue
 * @create: 2020-01-20 16:24
 */
@Service
public class UserServiceImpl implements UserService {


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
        //User saveUser = userRepository.save(user);
        //applicationEventPublisher.publishEvent(new UserEven(this, saveUser.getId()));

        return user;
    }

    @Override
    public boolean passwordMatch(User user, String plainPassword) {
        return false;
    }

    /**
     * 全部列表
     *
     * @return List
     */
    @Override
    public List<User> listAll() {
        return null;
    }

    /**
     * 列出所有页面
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @return 分页列表
     */
    @Override
    public PageInfo<User> listAll(Integer pageIndex, Integer pageSize) {
        return null;
    }

    /**
     * 按编号列出所有
     *
     * @param integers id
     * @return 列表
     */
    @Override
    public List<User> listAllByIds(Collection<Integer> integers) {
        return null;
    }

    /**
     * 通过ID获取
     *
     * @param integer id
     * @return Optional
     */
    @Override
    public Optional<User> getById(Integer integer) {
        return Optional.empty();
    }

    /**
     * 计算全部
     *
     * @return long
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * 按实体保存
     *
     * @param user 实体
     * @return 实体
     */
    @Override
    public User create(User user) {
        return null;
    }

    /**
     * 批量保存
     *
     * @param users domains
     * @return 列表
     */
    @Override
    public List<User> createInBatch(Collection<User> users) {
        return null;
    }

    /**
     * 通过实体更新
     *
     * @param user domain
     * @return DOMAIN
     */
    @Override
    public User update(User user) {
        return null;
    }

    /**
     * 按ID删除
     *
     * @param integer id
     * @return DOMAIN
     */
    @Override
    public int removeById(Integer integer) {
        return 0;
    }

    /**
     * 按实体删除
     *
     * @param user domain
     */
    @Override
    public void remove(User user) {

    }

    /**
     * 批量删除
     *
     * @param integers ids
     */
    @Override
    public void removeInBatch(Collection<Integer> integers) {

    }

    /**
     * 移除所有
     */
    @Override
    public void removeAll() {

    }
}
