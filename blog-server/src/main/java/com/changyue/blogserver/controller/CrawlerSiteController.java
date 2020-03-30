package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.params.SiteQuery;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.model.vo.SiteVO;
import com.changyue.blogserver.serivce.CrawlerPostSiteService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : 袁阊越
 * @description : 站点控制层
 * @date : 2020-03-30 21:25
 */
@RestController
@RequestMapping("/api/site")
public class CrawlerSiteController {

    @Autowired
    private CrawlerPostSiteService crawlerPostSiteService;

    @PostMapping
    public Result listSite(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                           @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
                           @RequestBody SiteQuery siteQuery) {
        PageInfo<SiteVO> siteVOPageInfo = crawlerPostSiteService.listByAll(pageIndex, pageSize, siteQuery);
        return Result.create(siteVOPageInfo);
    }

}
