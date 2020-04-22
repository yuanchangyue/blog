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

    /**
     * 获得角色菜单树形
     *
     * @param roleId 角色id
     * @return 角色菜单树形
     */
    List<MenuVo> getMenuTreeBy(Integer roleId);

    /**
     * 获得角色路由树形
     *
     * @param roleId 角色ID
     * @return 角色路由树形
     */
    List<RouterVO> getRouterList(Integer roleId);

    /**
     * 升级的为管理员
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean changeUserRole(Integer userId);

}
