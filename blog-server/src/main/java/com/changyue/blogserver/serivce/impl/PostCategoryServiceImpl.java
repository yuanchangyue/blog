package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.entity.PostCategory;
import com.changyue.blogserver.repository.CategoryRepository;
import com.changyue.blogserver.repository.PostCategoryRepository;
import com.changyue.blogserver.repository.PostRepository;
import com.changyue.blogserver.serivce.PostCategoryService;
import com.changyue.blogserver.serivce.base.CurdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * @program: blog-server
 * @description: 文章类业务实现
 * @author: 袁阊越
 * @create: 2020-01-22 21:36
 */
public class PostCategoryServiceImpl extends CurdServiceImpl<PostCategory, Integer> implements PostCategoryService {

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategoryPostId(Integer postId) {

        Assert.notNull(postId, "文章id不能为空");

        Set<Integer> categoryIds = postCategoryRepository.findAllCategoryIdsByPostId(postId);

        return categoryRepository.findAllById(categoryIds);
    }

    @Override
    public List<Post> listPostByCategoryId(Integer categoryId) {

        Assert.notNull(categoryId, "类别ID不能为空");

        Set<Integer> postIds = postCategoryRepository.findAllPostIdsByCategoryId(categoryId);

        return postRepository.findAllById(postIds);
    }

    @Override
    public List<Post> listPostByCategoryIdAndStatus(Integer categoryId, Integer status) {

        Assert.notNull(categoryId, "类别ID不能为空");
        Assert.notNull(categoryId, "文章状态不得为空");

        Set<Integer> postIds = postCategoryRepository.findAllPostIdsByCategoryId(categoryId, status);

        return postRepository.findAllById(postIds);
    }

    @Override
    public Page<Post> pagePostBy(Integer categoryId, Pageable pageable) {

        Assert.notNull(categoryId, "类别ID不能为空");
        Assert.notNull(pageable, "页面信息不能为空");

        Set<Integer> postIds = postCategoryRepository.findAllPostIdsByCategoryId(categoryId);

        return postRepository.findAllByIdIn(postIds, pageable);
    }

    @Override
    public List<PostCategory> removeByPostId(Integer postId) {
        Assert.notNull(postId, "postId不能为null");
        return postCategoryRepository.deleteByPostId(postId);
    }

    @Override
    public List<PostCategory> removeByCategoryId(Integer categoryId) {
        Assert.notNull(categoryId, "类别ID不能为空");
        return postCategoryRepository.deleteByCategoryId(categoryId);
    }
}
