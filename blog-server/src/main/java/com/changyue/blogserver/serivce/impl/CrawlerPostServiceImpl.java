package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CrawlerPostMapper;
import com.changyue.blogserver.model.entity.CrawlerPost;
import com.changyue.blogserver.serivce.CrawlerPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的业务接口层实现类
 * @date : 2020-03-25 19:38
 */
@Service
public class CrawlerPostServiceImpl implements CrawlerPostService {

    @Autowired
    private CrawlerPostMapper crawlerPostMapper;

    @Override
    public void savePost(CrawlerPost crawlerPost) {
        Assert.notNull(crawlerPost, "爬虫文章不能为空");
        crawlerPostMapper.insertSelective(crawlerPost);
    }

    @Override
    public void modifyPost(CrawlerPost crawlerPost) {
        Assert.notNull(crawlerPost, "爬虫文章不能为空");
        crawlerPostMapper.updateByPrimaryKey(crawlerPost);
    }

    @Override
    public void deleteByUrl(String url) {
        Assert.notNull(url, "地址不能为空");
        crawlerPostMapper.deleteByUrl(url);
    }

    @Override
    public List<CrawlerPost> getList(CrawlerPost crawlerPost) {
        Assert.notNull(crawlerPost, "爬虫文章不能为空");
        return crawlerPostMapper.findAll(crawlerPost);
    }
}
