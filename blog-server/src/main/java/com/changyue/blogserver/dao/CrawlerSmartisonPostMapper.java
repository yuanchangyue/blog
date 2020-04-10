package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CrawlerSmartisonPostMapper extends BaseMapper<CrawlerSmartisonPost> {

    /**
     * 通过siteId查找文章
     *
     * @param siteId 站点id
     * @return 文章列表
     */
    List<CrawlerSmartisonPost> findPostBySite(@Param("siteId") Integer siteId);


    /**
     * 拿到一篇文章
     *
     * @param siteId 站点id
     * @return 文章
     */
    CrawlerSmartisonPost findOnePostBySite(@Param("siteId") Integer siteId);


}
