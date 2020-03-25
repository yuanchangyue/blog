package com.changyue.blogserver.serivce;


import com.changyue.blogserver.crawler.model.CrawlerProxy;
import com.changyue.blogserver.model.entity.CrawlerIpPool;

import java.util.List;

/**
 * @author : 袁阊越
 * @description :   爬虫代理ip池业务逻辑接口
 * @date : 2020/3/25
 */
public interface CrawlerIpPoolService {

    /**
     * 保存
     *
     * @param crawlerIpPool ip池
     */
    void saveCrawlerIpPool(CrawlerIpPool crawlerIpPool);

    /**
     * 检查ip是否存在
     *
     * @return boolean
     */
    boolean checkExist(String host, int port);

    /**
     * 更新方法
     *
     * @param crawlerIpPool ip池
     */
    void updateCrawlerIpPool(CrawlerIpPool crawlerIpPool);

    /**
     * 查询所有数据
     *
     * @param crawlerIpPool ip池
     * @return List<IpPool>
     */
    List<CrawlerIpPool> queryList(CrawlerIpPool crawlerIpPool);

    /**
     * 查询可用的ip列表
     *
     * @param crawlerIpPool ip池
     * @return List<IpPool>
     */
    List<CrawlerIpPool> queryUsefulList(CrawlerIpPool crawlerIpPool);

    /**
     * 删除
     *
     * @param crawlerIpPool ip池
     */
    void delete(CrawlerIpPool crawlerIpPool);

    /**
     * 设置某个ip不可用
     *
     * @param proxy    代理
     * @param errorMsg 错误信息
     */
    void unUsefulProxy(CrawlerProxy proxy, String errorMsg);
}
