package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CrawlerPostCommentMapper;
import com.changyue.blogserver.model.entity.CrawlerPostComment;
import com.changyue.blogserver.serivce.CrawlerPostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * @author : 袁阊越
 * @description : 爬虫文章的评论信息业务层接口实现类
 * @date : 2020-03-25 19:14
 */
@Service
public class CrawlerPostCommentServiceImpl implements CrawlerPostCommentService {

    @Autowired
    private CrawlerPostCommentMapper crawlerPostCommentMapper;

    @Override
    public void saveCrawlerPostComment(CrawlerPostComment crawlerPostComment) {
        Assert.notNull(crawlerPostComment, "爬虫文章的评论信息不能为空");

        //插入数据库
        crawlerPostCommentMapper.insertSelective(crawlerPostComment);
    }
}
