package com.changyue.blogserver.dao;


import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerIpPool;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrawlerIpPoolMapper extends BaseMapper<CrawlerIpPool> {

    /**
     * 查询所有数据
     *
     * @param crawlerIpPool ipPool
     * @return List<IpPool>
     */
    List<CrawlerIpPool> findAll(CrawlerIpPool crawlerIpPool);

    /**
     * 查询可用的列表
     *
     * @param crawlerIpPool ipPool
     * @return List<IpPool>
     */
    List<CrawlerIpPool> findAvailableAll(CrawlerIpPool crawlerIpPool);

}
