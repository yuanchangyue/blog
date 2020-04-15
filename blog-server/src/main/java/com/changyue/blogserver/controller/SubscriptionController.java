package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.entity.UserSite;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.serivce.UserSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : 袁阊越
 * @description : 订阅控制层
 * @date : 2020-04-10 15:53
 */
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private UserSiteService userSiteService;


    @PostMapping
    public Result createBy(@RequestBody UserSite userSite) {
        userSiteService.create(userSite);
        return Result.create("订阅成功！");
    }

    @PostMapping("/check")
    public Result check(@RequestBody UserSite userSite) {
        return Result.create(userSiteService.isExist(userSite));
    }

    @GetMapping("/{userId}")
    public Result listBy(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                         @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
                         @PathVariable(name = "userId") Integer userId) {
        return Result.create(userSiteService.pageBy(pageIndex, pageSize, userId));
    }

}
