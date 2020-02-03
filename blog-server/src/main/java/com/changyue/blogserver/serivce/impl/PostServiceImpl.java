package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.PostMapper;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.params.PostParam;
import com.changyue.blogserver.model.params.PostQuery;
import com.changyue.blogserver.serivce.*;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * @author : 袁阊越
 * @description : 文章业务接口实现
 * @date : 2020-02-03 11:28
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostTagService postTagService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostCategoryService postCategoryService;

    @Autowired
    private CategoryService categoryService;


    @Override
    public PageInfo<Post> pageBy(PostQuery postQuery, @NotNull Pageable pageable) {
        return null;
    }


    @Override
    public Post createBy(PostParam postParam, Set<Integer> tagIds, Set<Integer> categoryId) {
        return null;
    }

    @Override
    public Post updateBy(Post postToUpdate, Set<Integer> tagIds, Set<Integer> categoryIds) {
        postToUpdate.setEditTime(new Date());

        return null;
    }

    @Override
    public Post getBy(Integer status, String url) {
        Assert.notNull(status, "文章状态不能为空");
        Assert.notNull(url, "文章的地址不能为空");

        return null;
    }

    @Override
    public long countLike() {
        return Optional.ofNullable(postMapper.countLike()).orElse(0L);
    }

    @Override
    public long countByStatus(Integer status) {
        return Optional.ofNullable(postMapper.countAllByStatus(status)).orElse(0L);
    }
}
