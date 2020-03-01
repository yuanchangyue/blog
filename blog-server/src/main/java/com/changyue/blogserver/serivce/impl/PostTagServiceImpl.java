package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.PostTagMapper;
import com.changyue.blogserver.dao.TagMapper;
import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.serivce.PostTagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author : 袁阊越
 * @description : 文章标签业务接口实现
 * @date : 2020/2/7/007
 */
@Service
public class PostTagServiceImpl implements PostTagService {

    @Autowired
    private PostTagMapper postTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> listTags(@NonNull Integer postId) {

        Assert.notNull(postId, "文章的id不能为空");

        Set<Integer> allByPostId = postTagMapper.findAllByPostId(postId);

        return tagMapper.getTagByIds(allByPostId);
    }

    @Override
    public int removeByPostId(@Nonnull Integer postId) {

        Assert.notNull(postId, "文章的id不能为空");

        return postTagMapper.deleteByPostId(postId);
    }

    @Override
    public int removeByTagId(@Nonnull Integer tagId) {

        Assert.notNull(tagId, "标签的id不能为空");

        return postTagMapper.deleteByTagId(tagId);
    }

    /**
     * 全部列表
     *
     * @return List
     */
    @Override
    public List<PostTag> listAll() {
        return postTagMapper.listAll();
    }

    /**
     * 列出所有页面
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @return 分页列表
     */
    @Override
    public PageInfo<PostTag> listAll(Integer pageIndex, Integer pageSize) {

        Assert.notNull(pageIndex, "页索引不能为空");
        Assert.notNull(pageSize, "页数不能为空");

        PageHelper.startPage(pageIndex, 5);
        List<PostTag> postTags = postTagMapper.listAll();

        return new PageInfo<>(postTags, 3);
    }

    /**
     * 通过ID获取
     *
     * @param id id
     * @return Optional
     */
    @Override
    public PostTag getById(Integer id) {
        return postTagMapper.selectByPrimaryKey(id).orElse(null);
    }

    /**
     * 按实体保存
     *
     * @param postTag 实体
     * @return id
     */
    @Override
    public PostTag create(PostTag postTag) {

        Assert.notNull(postTag, "postTag不能为空");

        //新增文章标签
        int effectNum = postTagMapper.insert(postTag);
        if (effectNum <= 0) {
            throw new CreateException("文章的标签创建失败").setErrData(postTag);
        }
        return postTag;
    }

    /**
     * 通过实体更新
     *
     * @param postTag domain
     * @return DOMAIN
     */
    @Override
    public PostTag update(PostTag postTag) {

        Assert.notNull(postTag, "postTag不能为空");

        postTagMapper.updateByPrimaryKey(postTag);

        return postTag;
    }

    /**
     * 批量保存
     *
     * @param postTags domains
     * @return 列表
     */
    @Override
    public List<PostTag> createInBatch(Collection<PostTag> postTags) {
        Assert.notNull(postTags, "文章标签批量插入,列表不能为空");
        if (postTagMapper.insetInBatch(postTags) <= 0) {
            throw new CreateException("批量标签创建失败").setErrData(postTags);
        }
        return new ArrayList<>(postTags);
    }

}
