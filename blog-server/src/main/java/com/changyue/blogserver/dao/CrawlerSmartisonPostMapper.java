package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CrawlerSmartisonPostMapper extends BaseMapper<CrawlerSmartisonPost> {
}
