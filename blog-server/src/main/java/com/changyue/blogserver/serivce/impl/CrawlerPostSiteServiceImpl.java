package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CrawlerPostSiteMapper;
import com.changyue.blogserver.model.entity.CrawlerPostSite;
import com.changyue.blogserver.serivce.CrawlerPostSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的站点业务接口实现类
 * @date : 2020-03-28 13:16
 */
@Service
public class CrawlerPostSiteServiceImpl implements CrawlerPostSiteService {

    @Autowired
    private CrawlerPostSiteMapper crawlerPostSiteMapper;

    @Override
    public CrawlerPostSite create(CrawlerPostSite crawlerPostSite) {

        Assert.notNull(crawlerPostSite, "爬虫文章的站点不能空");

        CrawlerPostSite byId = crawlerPostSiteMapper.findById(crawlerPostSite.getId());
        if (null == byId) {
            crawlerPostSiteMapper.insertSelective(crawlerPostSite);
        }
        return crawlerPostSite;
    }

    @Override
    public List<CrawlerPostSite> listAll() {
        return crawlerPostSiteMapper.listAll();
    }

    @Override
    public List<String> listIds() {
        return crawlerPostSiteMapper.findListIds();
    }
}
