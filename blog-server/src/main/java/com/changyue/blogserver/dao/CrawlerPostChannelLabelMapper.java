package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerPostChannelLabel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : 袁阊越
 * @description :爬虫的文章频道数据访问层
 * @date : 2020-03-25 12:25
 */
@Repository
@Mapper
public interface CrawlerPostChannelLabelMapper extends BaseMapper<CrawlerPostChannelLabel> {

    /**
     * 根据labelId查询
     *
     * @param id id
     * @return 爬虫的文章频道
     */
    CrawlerPostChannelLabel findByLabelId(Integer id);

}
