package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerPostComment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的评论数据访问接口
 * @date : 2020-03-25 19:05
 */
@Mapper
@Repository
public interface CrawlerPostCommentMapper extends BaseMapper<CrawlerPostComment> {

    /**
     * 按条件查询所有爬虫文章的评论数据
     *
     * @param crawlerPostComment 条件
     * @return 爬虫文章的评论数据
     */
    List<CrawlerPostComment> findAll(CrawlerPostComment crawlerPostComment);

}
