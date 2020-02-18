package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.RoleMapper;
import com.changyue.blogserver.model.entity.Role;
import com.changyue.blogserver.serivce.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : 袁阊越
 * @description : 权限业务接口实现
 * @date : 2020-02-18 10:54
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getById(Integer id) {
        return roleMapper.selectByPrimaryKey(id).orElse(null);
    }

}
