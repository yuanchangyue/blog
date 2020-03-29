package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 权限数据接口层
 * @date : 2020/2/18/018
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过userID找到角色ID
     *
     * @param userId 用户ID
     * @return 角色id
     */
    List<Integer> findRoleIdByUserId(@Param("userId") Integer userId);
}
