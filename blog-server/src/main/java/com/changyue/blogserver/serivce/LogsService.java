package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Logs;
import com.changyue.blogserver.serivce.base.BaseService;
import com.github.pagehelper.PageInfo;

/**
 * @author : 袁阊越
 * @description :  日志业务层接口
 * @date : 2020-04-22 20:29
 */
public interface LogsService extends BaseService<Logs, Integer> {
    /**
     * 获得日志的分页列表
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @param username  用户名
     * @return 日志分页列表
     */
    PageInfo<Logs> listAll(Integer pageIndex, Integer pageSize, String username);
}
