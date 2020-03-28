package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.CrawlerPostCate;
import com.changyue.blogserver.serivce.base.BaseService;
import org.apache.ibatis.annotations.Param;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的分类业务接口
 * @date : 2020-03-28 12:17
 */
public interface CrawlerPostCateService extends BaseService<CrawlerPostCate,Integer> {

    /**
     * 通过id查询siteIds
     *
     * @param id id
     * @return siteIds
     */
    String getSiteIdsById(@Param("id") Integer id);

}
