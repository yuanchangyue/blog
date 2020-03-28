package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.CrawlerPostSite;
import com.changyue.blogserver.serivce.base.BaseService;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的站点业务接口
 * @date : 2020-03-28 13:14
 */
public interface CrawlerPostSiteService extends BaseService<CrawlerPostSite, String> {

    /**
     * 全部id
     *
     * @return
     */
    List<String> listIds();

}
