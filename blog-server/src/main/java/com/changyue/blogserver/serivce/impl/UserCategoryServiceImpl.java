package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.UserCategoryMapper;
import com.changyue.blogserver.model.entity.UserCategory;
import com.changyue.blogserver.serivce.UserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : 袁阊越
 * @description : 用户类别业务实现
 * @date : 2020/3/2
 */
@Service
public class UserCategoryServiceImpl implements UserCategoryService {

    @Autowired
    private UserCategoryMapper userCategoryMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param userCategoryId 主键
     * @return 实例对象
     */
    @Override
    public UserCategory getById(Integer userCategoryId) {
        return this.userCategoryMapper.selectByPrimaryKey(userCategoryId).orElse(null);
    }

    /**
     * 新增数据
     *
     * @param userCategory 实例对象
     * @return 实例对象
     */
    @Override
    public UserCategory create(UserCategory userCategory) {
        this.userCategoryMapper.insert(userCategory);
        return userCategory;
    }

    /**
     * 通过主键删除数据
     *
     * @param userCategoryId 主键
     * @return 是否成功
     */
    @Override
    public int removeById(Integer userCategoryId) {
        return this.userCategoryMapper.deleteByPrimaryKey(userCategoryId);
    }
}
