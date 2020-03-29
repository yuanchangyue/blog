package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Resource;
import com.changyue.blogserver.model.entity.UsersRole;
import com.changyue.blogserver.model.vo.MenuVo;
import com.changyue.blogserver.model.vo.RouterVO;
import com.changyue.blogserver.serivce.base.BaseService;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 用户权限业务接口层
 * @date : 2020-02-18 11:38
 */
public interface UserAuthorityService extends BaseService<UsersRole, Integer> {

    /**
     * 获得角色的资源
     *
     * @param roleId 角色ID
     * @return 资源
     */
    List<Resource> getResourceByRoleId(Integer roleId);


    List<MenuVo> getMenuTreeBy(Integer roleId);


    List<RouterVO> getRouterList(Integer roleId);

}
