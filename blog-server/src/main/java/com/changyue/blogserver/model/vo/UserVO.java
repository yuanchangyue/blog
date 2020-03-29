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

    private UserDTO userDTO;

    private Role role;

    private List<MenuVo> menuVos;

    private List<RouterVO> routerVOS;

}
