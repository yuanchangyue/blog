package com.changyue.blogserver.dao;


import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.UsersRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : 袁阊越
 * @description : 用户权限数据访问接口层
 * @date : 2020/2/18/018
 */
@Mapper
@Repository
public interface UsersRoleMapper extends BaseMapper<UsersRole> {
}
