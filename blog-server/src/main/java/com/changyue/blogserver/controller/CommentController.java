package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.enums.ResultStatus;
import com.changyue.blogserver.model.params.CommentParam;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.serivce.CommentsService;
import com.changyue.blogserver.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 评论控制层
 * @date : 2020-04-03 10:47
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping
    public Result createBy(@Valid @RequestBody CommentParam commentParam, HttpServletRequest request) {
        commentParam.setIpAddress(IpUtils.getIpAddr(request));
        commentsService.createBy(commentParam);
        return Result.create("评价创建成功");
    }

    @GetMapping
    public Result listLatest(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                             @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(name = "userId") Integer userId,
                             @RequestParam(name = "postId") Integer postId,
                             @RequestParam(name = "parentId") Long parentId,
                             @RequestParam(name = "content") String content) {
        CommentParam commentParam = new CommentParam(userId, content, postId, parentId);
        return Result.create(commentsService.getListLatest(pageIndex, pageSize, commentParam));
    }

    @PostMapping("/list")
    public Result listBy(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                         @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                         @RequestBody CommentParam commentParam) {
        return Result.create(commentsService.getCommentList(pageIndex, pageSize, commentParam));
    }

    @PostMapping("/changestatus")
    public Result changeStatus(@RequestBody CommentParam commentParam) {
        commentsService.changeStatus(commentParam);
        return Result.create("审核通过");
    }

    @PostMapping("/batchchangestatus")
    public Result batchChangeStatus(@RequestParam(name = "multiple") Long[] multiple) {
        List<Long> ids = Arrays.stream(multiple).distinct().collect(Collectors.toList());
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                CommentParam commentParam = new CommentParam();
                commentParam.setId(id);
                commentParam.setStatus(1);
                commentsService.changeStatus(commentParam);
            }
        } else {
            return Result.create(ResultStatus.OPERATION_ERROR, "传入审核的对象为空");
        }
        return Result.create("批量审核通过");
    }

    @DeleteMapping("/{commentId}")
    public Result delete(@PathVariable("commentId") Long commentId) {
        commentsService.removeById(commentId);
        return Result.create("删除成功");
    }

    @DeleteMapping("/batchremove")
    public Result batchDelete(@RequestParam(name = "multiple") Long[] multiple) {
        List<Long> ids = Arrays.stream(multiple).distinct().collect(Collectors.toList());
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                commentsService.removeById(id);
            }
        } else {
            return Result.create(ResultStatus.OPERATION_ERROR, "传入移除的对象为空");
        }
        return Result.create("批量删除成功");
    }

}
