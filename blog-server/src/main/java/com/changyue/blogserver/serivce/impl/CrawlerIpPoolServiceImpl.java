package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.crawler.model.CrawlerProxy;
import com.changyue.blogserver.dao.CrawlerIpPoolMapper;
import com.changyue.blogserver.model.entity.CrawlerIpPool;
import com.changyue.blogserver.serivce.CrawlerIpPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫代理ip池业务逻辑接口实现类
 * @date : 2020-03-25 16:09
 */
@Service
public class CrawlerIpPoolServiceImpl implements CrawlerIpPoolService {

    @Autowired
    private CrawlerIpPoolMapper crawlerIpPoolMapper;

    @Override
    public void saveCrawlerIpPool(CrawlerIpPool crawlerIpPool) {
        Assert.notNull(crawlerIpPool, "ip代理池不能为空");
        crawlerIpPoolMapper.insertSelective(crawlerIpPool);
    }

    @Override
    public boolean checkExist(String host, int port) {
        CrawlerIpPool crawlerIpPool = new CrawlerIpPool();
        crawlerIpPool.setPort(port);
        crawlerIpPool.setIp(host);
        List<CrawlerIpPool> crawlerIpPools = crawlerIpPoolMapper.findAll(crawlerIpPool);
        return null != crawlerIpPools && !crawlerIpPools.isEmpty();
    }

    @Override
    public void updateCrawlerIpPool(CrawlerIpPool crawlerIpPool) {
        Assert.notNull(crawlerIpPool, "ip代理池不能为空");
        crawlerIpPoolMapper.updateByPrimaryKey(crawlerIpPool);
    }

    @Override
    public List<CrawlerIpPool> queryList(CrawlerIpPool crawlerIpPool) {
        Assert.notNull(crawlerIpPool, "ip代理池不能为空");
        return crawlerIpPoolMapper.findAll(crawlerIpPool);
    }

    @Override
    public List<CrawlerIpPool> queryUsefulList(CrawlerIpPool crawlerIpPool) {
        Assert.notNull(crawlerIpPool, "ip代理池不能为空");
        return crawlerIpPoolMapper.findAvailableAll(crawlerIpPool);
    }

    @Override
    public void delete(CrawlerIpPool crawlerIpPool) {
        Assert.notNull(crawlerIpPool, "ip代理池不能为空");
        Assert.notNull(crawlerIpPool.getId(), "ip代理池ID,不能为空");
        crawlerIpPoolMapper.deleteByPrimaryKey(crawlerIpPool.getId());
    }

    @Override
    public void unUsefulProxy(CrawlerProxy proxy, String errorMsg) {
        CrawlerIpPool crawlerIpPool = new CrawlerIpPool();
        crawlerIpPool.setPort(proxy.getPort());
        crawlerIpPool.setIp(proxy.getHost());
        crawlerIpPool.setIsEnable(true);
        List<CrawlerIpPool> crawlerIpPools = crawlerIpPoolMapper.findAll(crawlerIpPool);
        if (null != crawlerIpPools && !crawlerIpPools.isEmpty()) {
            for (CrawlerIpPool crawlerIpPool1 : crawlerIpPools) {
                crawlerIpPool1.setIsEnable(false);
                crawlerIpPool1.setError(errorMsg);
                //修改不可用
                updateCrawlerIpPool(crawlerIpPool1);
            }
        }
    }
}
