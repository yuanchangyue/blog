package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.UserPostMapper;
import com.changyue.blogserver.model.entity.UserPost;
import com.changyue.blogserver.serivce.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author : 袁阊越
 * @description : 用户收藏文章业务实现类
 * @date : 2020-04-10 23:36
 */
@Service
public class UserPostServiceImpl implements UserPostService {

    @Autowired
    private UserPostMapper userPostMapper;

    @Override
    public boolean isExist(UserPost userPost) {
        return userPostMapper.findOneBySelective(userPost).isPresent();
    }

    @Override
    public UserPost create(UserPost userPost) {
        Assert.notNull(userPost, "用户收藏信息不能为空");
        if (!isExist(userPost)) {
            userPostMapper.insertSelective(userPost);
        }
        return userPost;
    }

    @Override
    public int removeById(Integer id) {
        Assert.notNull(id, "id不能为空");
        return userPostMapper.deleteByPrimaryKey(id);
    }
}
