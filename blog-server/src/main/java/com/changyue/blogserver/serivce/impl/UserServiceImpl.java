package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.UserMapper;
import com.changyue.blogserver.exception.UpdateException;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.Role;
import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.params.UserParam;
import com.changyue.blogserver.model.vo.UserVO;
import com.changyue.blogserver.serivce.RoleService;
import com.changyue.blogserver.serivce.UserAuthorityService;
import com.changyue.blogserver.serivce.UserService;
import com.changyue.blogserver.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 用户业务实现
 * @date : 2020/2/3/003
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserAuthorityService userAuthorityService;

    @Override
    public UserVO getUserForView(User user) {
        Assert.notNull(user, "用户没有找到");

        UserVO userVO = new UserVO();
        userVO.setUserDTO(convertTO(user));
        Role roleByUser = roleService.getRoleByUser();
        userVO.setRole(roleByUser);
        userVO.setMenuVos(userAuthorityService.getMenuTreeBy(roleByUser.getId()));
        userVO.setRouterVOS(userAuthorityService.getRouterList(roleByUser.getId()));

        return userVO;
    }

    @Override
    public UserDTO getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        user = userMapper.selectByPrimaryKey(user.getId()).orElse(null);
        Assert.notNull(user, "当前用户为空");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public User getByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return userMapper.findOneBySelective(user).orElse(null);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return userMapper.findOneBySelective(user);
    }

    @Override
    public List<UserDTO> listUser(String username) {
        List<User> listUser = userMapper.findListUser(username);
        return listUser.stream().map(this::convertTO).collect(Collectors.toList());
    }

    @Override
    public User updatePassword(String oldPassword, String newPassword, String againPassword, Integer userId) {

        Assert.hasText(oldPassword, "旧密码不能为空");
        Assert.hasText(newPassword, "新密码不能为空");
        Assert.notNull(userId, "用户ID不能为空");

        if (!org.apache.commons.lang3.StringUtils.equals(newPassword, againPassword)) {
            throw new UpdateException("密码修改失败,密码不一致");
        }

        //新旧密码不能相同
        if (oldPassword.equals(newPassword)) {
            throw new UpdateException("密码修改失败,新旧密码不能相同");
        }

        User user = this.getByUserId(userId);

        //旧密码是否正确
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new UpdateException("密码不正确");
        }

        this.setPassword(user, newPassword);

        //更新密码
        return this.update(user);
    }

    @Override
    @Transactional
    public User createBy(UserParam userParam) {

        Assert.notNull(userParam, "创建用户,用户属性不能为空!");

        //转换为用户实体
        User user = userParam.convertTo();

        setPassword(user, userParam.getPassword());

        //创建用户
        userMapper.insert(user);

        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        Assert.notNull(user, "更新的用户不能为空");
        user.setId(ShiroUtils.getUser().getId());
        if (userMapper.updateByPrimaryKeySelective(user) <= 0) {
            throw new UpdateException("用户更新失败");
        }
        return user;
    }

    /**
     * 设置密码(加密)
     *
     * @param user          用户
     * @param inputPassword 密码
     */
    private void setPassword(User user, String inputPassword) {

        Assert.notNull(user, "用户不能为空");
        Assert.hasText(inputPassword, "密码不能为空");

        user.setPassword(BCrypt.hashpw(inputPassword, BCrypt.gensalt()));
    }

    @Override
    public boolean passwordMatch(User user, String inputPassword) {
        Assert.notNull(user, "用户不能为空");
        return !StringUtils.isEmpty(inputPassword) && BCrypt.checkpw(inputPassword, user.getPassword());
    }

    @Override
    public boolean verifyUser(String username, String password) {

        Assert.hasText(username, "用户不能为空");
        Assert.hasText(password, "密码不能为空");

        return !userMapper.findByUsernameAndPassword(username, BCrypt.hashpw(password, BCrypt.gensalt())).isPresent();
    }

    @Override
    public User getByUserId(Integer userId) {
        Assert.notNull(userId, "用户id不能为空");
        return userMapper.selectByPrimaryKey(userId).orElse(null);
    }

    @Override
    public Set<String> getUserRoleNames(Integer userId) {
        Assert.notNull(userId, "用户Id不能为空");
        return userMapper.findUserRoleName(userId);
    }

    @Override
    public Set<String> getUserPermissionNames(Integer userId) {
        Assert.notNull(userId, "用户Id不能为空");
        return userMapper.findUserPermissionNames(userId);
    }

    @Override
    public UserDTO convertTO(User user) {
        Assert.notNull(user, "用户没有找到");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public boolean modifyAvatar(String url) {
        Assert.hasText(url, "头像地址不能为空");
        return userMapper.updateUserAvatar(url, ShiroUtils.getUser().getId()) >= 1;
    }

}
