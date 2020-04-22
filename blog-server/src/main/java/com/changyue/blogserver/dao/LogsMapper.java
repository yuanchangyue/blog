package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Logs;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : 袁阊越
 * @description : 日志数据接口层
 * @date : 2020/4/22
 */
@Mapper
@Repository
public interface LogsMapper extends BaseMapper<Logs> {
}
