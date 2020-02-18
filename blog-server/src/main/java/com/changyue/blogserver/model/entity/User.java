package com.changyue.blogserver.model.entity;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 用户
 * @date : 2020/2/18/018
 */
@Data
public class User {

    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户描述
     */
    private String description;

    /**
     * 用户权限
     */
    private List<Role> authorities;

 /*   *//**
     * 获取权限信息
     *
     * @return 角色列表
     *//*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/

}
