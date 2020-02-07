package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CategoryMapper;
import com.changyue.blogserver.dao.PostCategoryMapper;
import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.entity.PostCategory;
import com.changyue.blogserver.serivce.PostCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @program: blog-server
 * @description: 文章类业务实现
 * @author: 袁阊越
 * @create: 2020-01-22 21:36
 */
@Service
public class PostCategoryServiceImpl implements PostCategoryService {

    @Autowired
    private PostCategoryMapper postCategoryMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> listCategoryPostId(Integer postId) {

        Assert.notNull(postId, "文章id不能为空");

        Set<Integer> categoryIds = postCategoryMapper.findAllCategoryIdsByPostId(postId);

        return categoryMapper.findAllById(categoryIds);
    }

    @Override
    public List<Post> listPostByCategoryId(Integer categoryId) {

        Assert.notNull(categoryId, "类别ID不能为空");

        Set<Integer> postIds = postCategoryMapper.findAllPostIdsByCategoryId(categoryId);

        //return postRepository.findAllById(postIds);
        return null;
    }

    @Override
    public List<Post> listPostByCategoryIdAndStatus(Integer categoryId, Integer status) {

        Assert.notNull(categoryId, "类别ID不能为空");
        Assert.notNull(categoryId, "文章状态不得为空");

        Set<Integer> postIds = postCategoryMapper.findAllPostIdsByCategoryId(categoryId, status);

        //return postRepository.findAllById(postIds);
        return null;
    }

    @Override
    public PageInfo<Post> pagePostBy(Integer categoryId) {

        Assert.notNull(categoryId, "类别ID不能为空");


        Set<Integer> postIds = postCategoryMapper.findAllPostIdsByCategoryId(categoryId);

        return null;
    }

    @Override
    public List<PostCategory> removeByPostId(Integer postId) {
        Assert.notNull(postId, "postId不能为空");
        return postCategoryMapper.deleteByPostId(postId);
    }

    @Override
    public List<PostCategory> removeByCategoryId(Integer categoryId) {
        Assert.notNull(categoryId, "类别ID不能为空");
        return postCategoryMapper.deleteByCategoryId(categoryId);
    }

    /**
     * 全部列表
     *
     * @return List
     */
    @Override
    public List<PostCategory> listAll() {
        return postCategoryMapper.listAll();
    }

    /**
     * 列出所有页面
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @return 分页列表
     */
    @Override
    public PageInfo<PostCategory> listAll(Integer pageIndex, Integer pageSize) {
        Assert.notNull(pageIndex, "pageIndex 不能为空");
        Assert.notNull(pageSize, "pageSize 不能为空");

        PageHelper.startPage(pageIndex, 5);
        List<PostCategory> postCategories = postCategoryMapper.listAll();

        return new PageInfo<>(postCategories, 3);
    }

    /**
     * 通过ID获取
     *
     * @param id id
     * @return Optional
     */
    @Override
    public PostCategory getById(Integer id) {
        Assert.notNull(id, "id 不能为空");
        return postCategoryMapper.selectByPrimaryKey(id).orElse(null);
    }

    /**
     * 批量保存
     *
     * @param postCategories domains
     * @return 列表
     */
    @Override
    public List<PostCategory> createInBatch(Collection<PostCategory> postCategories) {

        Assert.notNull(postCategories, "文章类别批量插入,列表不能为空");

        if (postCategoryMapper.insertInBatch(postCategories) <= 0) {
            throw new CreateException("文章类别批量插入失败").setErrData(postCategories);
        }

        return new ArrayList<>(postCategories);
    }

}
