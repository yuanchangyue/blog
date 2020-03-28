package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CrawlerCsdnPostMapper;
import com.changyue.blogserver.model.entity.CrawlerCsdnPost;
import com.changyue.blogserver.serivce.CrawlerCsdnPostService;
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
public class CrawlerCsdnPostServiceImpl implements CrawlerCsdnPostService {

    @Autowired
    private CrawlerCsdnPostMapper crawlerCsdnPostMapper;

    @Override
    public void savePost(CrawlerCsdnPost crawlerCsdnPost) {
        Assert.notNull(crawlerCsdnPost, "爬虫文章不能为空");
        crawlerCsdnPostMapper.insertSelective(crawlerCsdnPost);
    }

    @Override
    public void modifyPost(CrawlerCsdnPost crawlerCsdnPost) {
        Assert.notNull(crawlerCsdnPost, "爬虫文章不能为空");
        crawlerCsdnPostMapper.updateByPrimaryKey(crawlerCsdnPost);
    }

    @Override
    public void deleteByUrl(String url) {
        Assert.notNull(url, "地址不能为空");
        crawlerCsdnPostMapper.deleteByUrl(url);
    }

    @Override
    public List<CrawlerCsdnPost> getList(CrawlerCsdnPost crawlerCsdnPost) {
        Assert.notNull(crawlerCsdnPost, "爬虫文章不能为空");
        return crawlerCsdnPostMapper.findAll(crawlerCsdnPost);
    }
}
