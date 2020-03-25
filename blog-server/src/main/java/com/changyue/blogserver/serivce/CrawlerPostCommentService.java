package com.changyue.blogserver.serivce;


import com.changyue.blogserver.model.entity.CrawlerPostComment;

/**
 * @author : 袁阊越
 * @description :  爬虫文章的评论信息业务层接口
 * @date : 2020/3/25
 */
public interface CrawlerPostCommentService {

    /**
     * 保存
     *
     * @param crawlerPostComment 爬虫文章的评论信息
     */
    void saveCrawlerPostComment(CrawlerPostComment crawlerPostComment);
}
