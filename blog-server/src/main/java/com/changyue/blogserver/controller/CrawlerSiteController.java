package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.entity.CrawlerPostCate;
import com.changyue.blogserver.model.params.SiteQuery;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.model.vo.SiteVO;
import com.changyue.blogserver.serivce.CrawlerPostCateService;
import com.changyue.blogserver.serivce.CrawlerPostSiteService;
import com.changyue.blogserver.serivce.CrawlerSmartisonService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CrawlerPostCateService crawlerPostCateService;

    @Autowired
    private CrawlerSmartisonService crawlerSmartisonService;

    @PostMapping
    public Result listSite(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                           @RequestParam(name = "pageSize", defaultValue = "16") Integer pageSize,
                           @RequestBody SiteQuery siteQuery) {

        Map<String, Object> modelMap = new HashMap<>();

        PageInfo<SiteVO> siteVOPageInfo = crawlerPostSiteService.listByAll(pageIndex, pageSize, siteQuery);
        List<CrawlerPostCate> crawlerPostCates = crawlerPostCateService.getListAll();

        modelMap.put("siteVOPageInfo", siteVOPageInfo);
        modelMap.put("crawlerPostCates", crawlerPostCates);

        return Result.create(modelMap);
    }

    @GetMapping
    public Result getPostBySite(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(name = "siteId") Integer siteId) {
        return Result.create(crawlerSmartisonService.getPostList(pageIndex,pageSize,siteId));
    }

    @GetMapping("/{siteId}")
    public Result getSite(@PathVariable("siteId") String siteId) {
        return Result.create(crawlerPostSiteService.getById(siteId));
    }

}
