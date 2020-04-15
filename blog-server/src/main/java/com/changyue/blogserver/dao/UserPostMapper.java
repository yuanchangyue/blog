package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.UserPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 用户收藏文章中间表
 * @date : 2020/4/10
 */
@Repository
@Mapper
public interface UserPostMapper extends BaseMapper<UserPost> {

    /**
     * 查询全部的用户收藏列表
     *
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<UserPost> listByUserId(@Param("userId") Integer userId);
}
