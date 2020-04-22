package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.LogsMapper;
import com.changyue.blogserver.model.entity.Logs;
import com.changyue.blogserver.serivce.LogsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 日志业务层接口实现
 * @date : 2020-04-22 20:30
 */
@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    private LogsMapper logsMapper;

    @Override
    public PageInfo<Logs> listAll(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Logs> logs = logsMapper.listAll();
        return new PageInfo<>(logs, 3);
    }

    @Override
    public Logs create(Logs logs) {
        Assert.notNull(logs, "日志不能为空");
        logsMapper.insertSelective(logs);
        return logs;
    }

}
