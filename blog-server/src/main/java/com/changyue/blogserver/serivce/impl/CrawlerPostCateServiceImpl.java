package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CrawlerPostCateMapper;
import com.changyue.blogserver.model.entity.CrawlerPostCate;
import com.changyue.blogserver.serivce.CrawlerPostCateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的分类业务接口实现类
 * @date : 2020-03-28 12:30
 */
@Slf4j
@Service
public class CrawlerPostCateServiceImpl implements CrawlerPostCateService {

    @Autowired
    private CrawlerPostCateMapper crawlerPostCateMapper;

    @Override
    public String getSiteIdsById(Integer id) {
        Assert.notNull(id, "crawler post cate 不能为空");
        return crawlerPostCateMapper.findSiteIdsById(id);
    }

    @Override
    public CrawlerPostCate create(CrawlerPostCate crawlerPostCate) {
        Assert.notNull(crawlerPostCate, "crawlerPostCate不能为空");
        CrawlerPostCate postCate = crawlerPostCateMapper.selectByPrimaryKey(crawlerPostCate.getId()).orElse(null);
        if (null != postCate) {
            crawlerPostCateMapper.insertSelective(crawlerPostCate);
        }
        return crawlerPostCate;
    }
}
