package com.changyue.blogserver.model.vo;

import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 用户前端视图对象
 * @date : 2020-03-29 15:01
 */
@Data
public class UserVO {

    /**
     * 用户基本信息
     */
    private UserDTO userDTO;

    /**
     * 用户角色
     */
    private Role role;

    /**
     * 用户拥有菜单
     */
    private List<MenuVo> menuVos;

    /**
     * 用户拥有路由
     */
    private List<RouterVO> routerVOS;

}
