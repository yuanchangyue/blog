package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : 袁阊越
 * @description : 权限数据接口层
 * @date : 2020/2/18/018
*/
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {
}
