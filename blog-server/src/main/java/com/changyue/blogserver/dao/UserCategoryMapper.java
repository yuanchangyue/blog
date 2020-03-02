package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.UserCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 用户类别接口
 * @date : 2020/3/2
 */
@Mapper
@Repository
public interface UserCategoryMapper extends BaseMapper<UserCategory> {

}
