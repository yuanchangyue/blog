package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.CrawlerPostSite;
import com.changyue.blogserver.model.params.SiteQuery;
import com.changyue.blogserver.model.vo.SiteVO;
import com.changyue.blogserver.serivce.base.BaseService;
import com.github.pagehelper.PageInfo;

import javax.annotation.Nonnull;
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
     * @return ids
     */
    List<String> listIds();

    /**
     * 站点列表
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @param siteQuery 站点查询
     */
    PageInfo<SiteVO> listByAll(@Nonnull Integer pageIndex, @Nonnull Integer pageSize, @Nonnull SiteQuery siteQuery);

    /**
     * 转化为视图对象
     *
     * @param crawlerPostSite 爬虫的站点信息
     * @return 视图对象
     */
    SiteVO convertTo(CrawlerPostSite crawlerPostSite);


}
