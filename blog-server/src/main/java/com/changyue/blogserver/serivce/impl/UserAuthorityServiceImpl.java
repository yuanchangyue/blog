package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.ResourceMapper;
import com.changyue.blogserver.dao.UsersRoleMapper;
import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.model.entity.Resource;
import com.changyue.blogserver.model.entity.UsersRole;
import com.changyue.blogserver.model.vo.MenuVo;
import com.changyue.blogserver.model.vo.RouterVO;
import com.changyue.blogserver.serivce.UserAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 用户权限业务接口实现
 * @date : 2020-02-18 11:40
 */
@Slf4j
@Service
public class UserAuthorityServiceImpl implements UserAuthorityService {

    @Autowired
    private UsersRoleMapper usersRoleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @Transactional
    public UsersRole create(UsersRole usersRole) {
        Assert.notNull(usersRole, "用户权限信息不能为空！");
        int effect = usersRoleMapper.insert(usersRole);
        if (effect <= 0) {
            throw new CreateException("用户的权限创建失败");
        }
        return usersRole;
    }


    @Override
    public UsersRole getById(Integer id) {
        Assert.notNull(id, "用户权限ID不能为空");
        return usersRoleMapper.selectByPrimaryKey(id).orElse(null);
    }

    @Override
    public List<Resource> getResourceByRoleId(Integer roleId) {
        Assert.notNull(roleId, "角色的ID不能为空");
        return resourceMapper.findListByRoleId(roleId);
    }

    @Override
    public List<MenuVo> getMenuTreeBy(Integer roleId) {
        List<Resource> resourceByRoleId = getResourceByRoleId(roleId);
        if (null == resourceByRoleId) {
            return null;
        }
        return resourceByRoleId.stream().map(resource -> {
            List<Resource> listByParentId = resourceMapper.findListByParentId(resource.getId());
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(resource, menuVo);
            if (null != listByParentId && !listByParentId.isEmpty()) {
                ArrayList<MenuVo> childrenList = new ArrayList<>();
                for (Resource r : listByParentId) {
                    MenuVo children = new MenuVo();
                    BeanUtils.copyProperties(r, children);
                    childrenList.add(children);
                }
                menuVo.setChildren(childrenList);
            }
            return menuVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RouterVO> getRouterList(Integer roleId) {
        List<Resource> resourceByRoleId = getResourceByRoleId(roleId);
        List<RouterVO> routerVOS = new ArrayList<>();
        if (null != resourceByRoleId && !resourceByRoleId.isEmpty()) {
            for (Resource resource : resourceByRoleId) {
                RouterVO routerVO = new RouterVO();
                BeanUtils.copyProperties(resource, routerVO);
                routerVOS.add(routerVO);
                List<Resource> listByParentId = resourceMapper.findListByParentId(resource.getId());
                for (Resource r : listByParentId) {
                    RouterVO subRouterVO = new RouterVO();
                    BeanUtils.copyProperties(r, subRouterVO);
                    routerVOS.add(subRouterVO);
                }
            }
        }
        return routerVOS;
    }

    @Override
    public boolean changeUserRole(Integer userId) {
        return usersRoleMapper.updateUserRoleToManage(userId) >= 1;
    }

}
