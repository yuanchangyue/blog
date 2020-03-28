package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerPostSite;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的站点
 * @date : 2020/3/28
 */
@Repository
@Mapper
public interface CrawlerPostSiteMapper extends BaseMapper<CrawlerPostSite> {

    CrawlerPostSite findById(String id);

    List<String> findListIds();

}
