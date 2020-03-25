package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerPostLabel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author : 袁阊越
 * @description : 爬虫的文章标签数据访问层
 * @date : 2020-03-25 12:10
 */
@Repository
@Mapper
public interface CrawlerPostLabelMapper extends BaseMapper<CrawlerPostLabel> {

    /**
     * 通过label名称获取
     *
     * @param labelList label name list
     * @return 文章标签的列表
     */
    List<CrawlerPostLabel> findPostLabelByLabelsName(List<String> labelList);

    /**
     * 通过label id获取
     *
     * @param labelList label id list
     * @return 文章标签的列表
     */
    List<CrawlerPostLabel> findPostLabelByLabelIds(List<String> labelList);

}
