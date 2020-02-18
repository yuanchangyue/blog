package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.UsersRoleMapper;
import com.changyue.blogserver.model.entity.UsersRole;
import com.changyue.blogserver.serivce.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author : 袁阊越
 * @description : 用户权限业务接口实现
 * @date : 2020-02-18 11:40
 */
@Slf4j
@Service
public class UserAuthorityServiceImpl implements UserRoleService {

    @Autowired
    private UsersRoleMapper usersRoleMapper;

    @Override
    @Transactional
    public Integer create(UsersRole usersRole) {
        Assert.notNull(usersRole, "用户权限信息不能为空！");
        return usersRoleMapper.insert(usersRole);
    }

    @Override
    public UsersRole getById(Integer id) {
        Assert.notNull(id, "用户权限ID不能为空");
        return usersRoleMapper.selectByPrimaryKey(id).orElse(null);
    }

}
