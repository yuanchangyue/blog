package com.changyue.blogserver.dao;


import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.CrawlerPostAdditional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫附加信息数据接口层
 * @date : 2020/3/25
 */
@Repository
@Mapper
public interface CrawlerPostAdditionalMapper extends BaseMapper<CrawlerPostAdditional> {

    /**
     * 按条件查询所有数据
     *
     * @param crawlerPostAdditional 附家信息
     * @return List<PostAdditional>
     */
    List<CrawlerPostAdditional> findAll(CrawlerPostAdditional crawlerPostAdditional);

    /**
     * 获取需要更新的数据
     *
     * @param currentDate 当前时间
     * @return List<PostAdditional>
     */
    List<CrawlerPostAdditional> findListByNeedUpdate(Date currentDate);

}
