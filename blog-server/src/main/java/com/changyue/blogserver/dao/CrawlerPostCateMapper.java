package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerPostCate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的分类数据访问层
 * @date : 2020/3/28
 */
@Repository
@Mapper
public interface CrawlerPostCateMapper extends BaseMapper<CrawlerPostCate> {
    /**
     * 通过id查询siteIds
     *
     * @param id id
     * @return siteIds
     */
    String findSiteIdsById(@Param("id") Integer id);

}
