package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Role;
import com.changyue.blogserver.serivce.base.BaseService;

/**
 * @author : 袁阊越
 * @description : 权限业务接口
 * @date : 2020-02-18 10:52
 */
public interface RoleService extends BaseService<Role, Integer> {

    /**
     * 通过用户找到角色
     *
     * @return 角色
     */
    Role getRoleByUser();

}
