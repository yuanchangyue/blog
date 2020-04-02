package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.serivce.CrawlerSmartisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 袁阊越
 * @description : 站点控制层
 * @date : 2020-03-30 21:25
 */
@RestController
@RequestMapping("/api/crawlerpost")
public class CrawlerPostController {

    @Autowired
    private CrawlerSmartisonService crawlerSmartisonService;

    @GetMapping("/{crawlerPostId}")
    public Result getPost(@PathVariable("crawlerPostId") Integer crawlerPostId) {
        return Result.create(crawlerSmartisonService.getById(crawlerPostId));
    }

}
