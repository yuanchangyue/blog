package com.changyue.blogserver.security;

import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.serivce.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-02-18 17:33
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        User user = (User) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //添加用户角色
        info.addRoles(userService.getUserRoleNames(user.getId()));

        Set<String> userPermissionNames = userService.getUserPermissionNames(user.getId());

        Set<String> permissions = new HashSet<String>();
        userPermissionNames.forEach(s -> Collections.addAll(permissions, s.split(",")));

        //添加用户权限
        info.addStringPermissions(permissions);

        log.info("授权成功！");

        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();
        String password = new String(token.getPassword());

        User user = userService.getByUsername(username);

        if (user == null || user.getId() == null) {
            throw new UnknownAccountException("用户名不存在");
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new CredentialsException("密码错误");
        }

        log.info("认证成功！");

        return new SimpleAuthenticationInfo(user, token.getCredentials(), getName());
    }

}
