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
     * 通过站点id查找一篇文章
     *
     * @param siteId 站点id
     * @return 文章
     */
    CrawlerSmartisonPost findOnePostBySite(@Param("siteId") Integer siteId);


    /**
     * 查找简单的文章内容
     *
     * @param id id
     * @return 文章
     */
    CrawlerSmartisonPost findSimplyById(@Param("id") Integer id);


    /**
     * 查找简单的文章内容
     *
     * @param name name
     * @return 文章
     */
    List<CrawlerSmartisonPost> findSimplyByName(@Param("name") String name);


}
