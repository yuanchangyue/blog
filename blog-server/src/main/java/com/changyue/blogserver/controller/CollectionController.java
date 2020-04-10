package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.entity.UserPost;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.serivce.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 袁阊越
 * @description : 用户收藏控制层
 * @date : 2020-04-10 23:48
 */
@RestController
@RequestMapping("/api/collection")
public class CollectionController {

    @Autowired
    private UserPostService userPostService;

    @PostMapping
    public Result createBy(@RequestBody UserPost userPost) {
        userPostService.create(userPost);
        return Result.create("收藏成功！");
    }

    @PostMapping("/check")
    public Result check(@RequestBody UserPost userPost) {
        return Result.create(userPostService.isExist(userPost));
    }


}
