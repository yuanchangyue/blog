package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.UserSite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 用户站点中间表
 * @date : 2020/4/10
 */
@Repository
@Mapper
public interface UserSiteMapper extends BaseMapper<UserSite> {

    /**
     * 通过用户ID 查找订阅关系
     *
     * @param userId 用户id
     * @return 订阅关系
     */
    List<UserSite> findUserSiteByUserId(@Param("userId") Integer userId);

    /**
     * 通过用户和站点ID查找订阅关系
     *
     * @param userId 用户id
     * @param siteId 站点ID
     * @return 关系
     */
    UserSite findUserSite(@Param("userId") Integer userId, @Param("siteId") Integer siteId);

    /**
     * 移除用户和站点的关系
     *
     * @param userId 用户ID
     * @param siteId 站点ID
     * @return 影响行数
     */
    int deleteBy(@Param("userId") Integer userId, @Param("siteId") Integer siteId);

}
