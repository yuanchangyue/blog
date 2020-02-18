package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * @author : 袁阊越
 * @description : 用户数据接口层
 * @date : 2020/2/3/003
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查找用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    Optional<User> findByUsernameAndPassword(@NonNull @Param("username") String username, @NonNull @Param("password") String password);

    /**
     * 查找用户角色
     *
     * @param userId 用户id
     * @return 用户角色集
     */
    Set<String> findUserRoleName(@NonNull @Param("userId") Integer userId);

    /**
     * 根据id查询用户权限名称的集合
     *
     * @param userId 用户id
     * @return 用户权限集
     */
    Set<String> findUserPermissionNames(@NonNull @Param("userId") Integer userId);
}
