package com.changyue.blogserver.controller;

import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.params.PostParam;
import com.changyue.blogserver.model.params.PostQuery;
import com.changyue.blogserver.model.rep.CommonReturnType;
import com.changyue.blogserver.model.vo.PostVO;
import com.changyue.blogserver.serivce.PostService;
import com.changyue.blogserver.validator.ValidatorImpl;
import com.changyue.blogserver.validator.ValidatorResult;
import com.github.pagehelper.PageInfo;
import javafx.beans.binding.ObjectExpression;
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

    @Autowired
    private ValidatorImpl validator;

    @PostMapping
    public PostVO createBy(@RequestBody PostParam postParam) {
        ValidatorResult result = this.validator.validator(postParam);
        if (result.isHasError()) {
            log.debug("创建tag失败[{}]", result.getErrorMsgMap());
            throw new CreateException("创建tag失败:" + result.getErrMsg());
        }
        return postService.createBy(postParam.convertTo(), postParam.getTagIds(), postParam.getCategoryIds());
    }

    @GetMapping
    public PageInfo<PostVO> listPost(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return postService.pageBy(pageIndex, pageSize);
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

    @DeleteMapping
    public CommonReturnType<Object> deletePost(@RequestParam(name = "multipleDelete") Integer[] multipleDelete) {

        //去重复，装化成list
        List<Integer> multipleDeleteList = Arrays.stream(multipleDelete).distinct().collect(Collectors.toList());

        //执行删除
        postService.removeInBatch(multipleDeleteList);

        return CommonReturnType.create("文章删除成功");
    }

    @GetMapping("/{postId}")
    public CommonReturnType<Object> getPost(@PathVariable("postId")Integer postId) {
        //按照postId查找
        PostVO byPostId = postService.getByPostId(postId);
        return CommonReturnType.create(byPostId);
    }

}
