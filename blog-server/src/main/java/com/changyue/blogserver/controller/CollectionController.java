package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.entity.UserPost;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.serivce.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    public Result pageBy(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                         @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
                         @RequestParam(name = "userId") Integer userId) {
        return Result.create(userPostService.pageBy(pageIndex, pageSize, userId));
    }

    @PostMapping
    public Result createBy(@RequestBody UserPost userPost) {
        return Result.create(userPostService.create(userPost));
    }

    @PostMapping("/check")
    public Result check(@RequestBody UserPost userPost) {
        return Result.create(userPostService.isExist(userPost));
    }

    @DeleteMapping("/{userId}/{postId}")
    public Result delete(@PathVariable("userId") Integer userId, @PathVariable("postId") Integer postId) {
        UserPost userPost = new UserPost();
        userPost.setUserId(userId);
        userPost.setCrawlerPostId(postId);
        userPostService.remove(userPost);
        return Result.create("取消收藏！");
    }

}
