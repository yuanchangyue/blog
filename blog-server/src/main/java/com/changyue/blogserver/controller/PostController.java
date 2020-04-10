package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.enums.ResultStatus;
import com.changyue.blogserver.model.params.PostParam;
import com.changyue.blogserver.model.params.PostQuery;
import com.changyue.blogserver.model.vo.PostVO;
import com.changyue.blogserver.serivce.PostService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 文章控制器
 * @date : 2020-02-15 18:24
 */
@RestController
@Slf4j
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public PageInfo<PostVO> listPost(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return postService.pageBy(pageIndex, pageSize);
    }

    @GetMapping("/{postId}")
    public Result getPost(@PathVariable("postId") Integer postId) {
        //按照postId查找
        return Result.create(postService.getByPostId(postId));
    }

    @PostMapping
    public Result createBy(@Valid @RequestBody PostParam postParam) {
        return Result.create(postService.createBy(postParam.convertTo(), postParam.getTagIds(), postParam.getCategoryIds()));
    }

    @PostMapping("/query")
    public PageInfo<PostVO> listPostByQuery(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                            @RequestBody PostQuery postQuery) {
        if (postQuery == null) {
            return postService.pageBy(pageIndex, pageSize);
        }
        return postService.pageByQuery(pageIndex, pageSize, postQuery);
    }

    @GetMapping("/latest")
    public Result listLatest() {
        return Result.create(postService.latestPost());
    }


    @DeleteMapping
    public Result deletePost(@RequestParam(name = "multipleDelete") Integer[] multipleDelete) {

        //去重复，装化成list
        List<Integer> multipleDeleteList = Arrays.stream(multipleDelete).distinct().collect(Collectors.toList());
        //执行删除
        postService.removeInBatch(multipleDeleteList);

        return Result.create(ResultStatus.OPERATION_SUCCESS);
    }

    @PutMapping("/{postId}")
    public Result modifyPost(@PathVariable("postId") Integer postId,
                             @Valid @RequestBody PostParam postParam) {

        //转化为文章入参，转化为文章类型，并设置上Id
        Post postToUpdate = postParam.convertTo();
        postToUpdate.setId(postId);

        postService.updateBy(postToUpdate, postParam.getTagIds(), postParam.getCategoryIds());

        return Result.create(ResultStatus.OPERATION_SUCCESS);
    }

    @PostMapping("/status")
    public Result updateStatus(@RequestParam("postId") Integer postId, @RequestParam("status") Integer status) {
        postService.updateStatus(postId, status);
        return Result.create(ResultStatus.OPERATION_SUCCESS);
    }

    @PutMapping("{postId}/likes")
    public void likes(@PathVariable("postId") Integer postId) {
        postService.increaseLike(1L,postId);
    }

}
