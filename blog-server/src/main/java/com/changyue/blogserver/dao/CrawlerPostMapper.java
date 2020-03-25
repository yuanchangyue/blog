package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerPost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的数据访问接口
 * @date : 2020-03-25 19:28
 */
@Repository
@Mapper
public interface CrawlerPostMapper extends BaseMapper<CrawlerPost> {

    /**
     * 按条件查 询所有数据
     *
     * @param crawlerPost 条件
     * @return 爬虫文章列表
     */
    List<CrawlerPost> findAll(CrawlerPost crawlerPost);

    /**
     * 通过url删除
     *
     * @param url 地址
     */
    void deleteByUrl(String url);

    /**
     * 通过id和状态查询爬虫文章
     *
     * @param param 参数
     * @return 爬虫文章
     */
    CrawlerPost findByIdAndStatus(CrawlerPost param);

}
