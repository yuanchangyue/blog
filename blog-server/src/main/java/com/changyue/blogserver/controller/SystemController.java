package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.serivce.LogsService;
import com.changyue.blogserver.serivce.UserAuthorityService;
import com.changyue.blogserver.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : 袁阊越
 * @description : 系统控制层
 * @date : 2020-04-22 16:17
 */
@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogsService logsService;

    @Autowired
    private UserAuthorityService userAuthorityService;

    @GetMapping("/userlist")
    public Result listUser(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(name = "username", defaultValue = "") String username) {
        return Result.create(userService.listUser(pageIndex, pageSize, username));
    }

    @PutMapping("/changeuserrole/{userId}")
    public Result changeUserRole(@PathVariable("userId") Integer userId) {
        return Result.create(userAuthorityService.changeUserRole(userId));
    }

    @GetMapping("/logs")
    public Result listLog(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          @RequestParam(name = "username", defaultValue = "") String username) {
        return Result.create(logsService.listAll(pageIndex, pageSize, username));
    }

    @DeleteMapping("/logs/{logId}")
    public Result deleteLog(@PathVariable("logId") Integer logId) {
        logsService.removeById(logId);
        return Result.create("日志删除成功");
    }

}
