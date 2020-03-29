package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 用户权限数据访问接口
 * @date : 2020/3/29
 */
@Mapper
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 通过角色查找资源
     *
     * @param roleId 角色ID
     * @return 资源
     */
    List<Resource> findListByRoleId(@Param("roleId") Integer roleId);

    /**
     * 通过父级查找资源
     *
     * @param parentId 父级ID
     * @return 资源
     */
    List<Resource> findListByParentId(@Param("parentId") Integer parentId);

}
