package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.UserPost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : 袁阊越
 * @description : 用户收藏文章中间表
 * @date : 2020/4/10
 */
@Repository
@Mapper
public interface UserPostMapper extends BaseMapper<UserPost> {
}
