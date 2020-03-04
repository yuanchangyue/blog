package com.changyue.blogserver.controller;

import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.params.PostParam;
import com.changyue.blogserver.model.vo.PostVO;
import com.changyue.blogserver.serivce.PostService;
import com.changyue.blogserver.validator.ValidatorImpl;
import com.changyue.blogserver.validator.ValidatorResult;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

}
