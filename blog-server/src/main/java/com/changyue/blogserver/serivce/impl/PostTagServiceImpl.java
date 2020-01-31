package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.PostTagMapper;
import com.changyue.blogserver.dao.TagMapper;
import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.repository.PostTagRepository;
import com.changyue.blogserver.serivce.PostTagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @program: blog-server
 * @description: 文章标签业务接口实现
 * @author: ChangYue
 * @create: 2020-01-21 09:11
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
    public int removeByPostTag(@Nonnull Integer postId) {

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
    public Optional<PostTag> getById(Integer id) {
        return postTagMapper.selectByPrimaryKey(id);
    }

    /**
     * 按实体保存
     *
     * @param postTag 实体
     * @return 实体
     */
    @Override
    public PostTag create(PostTag postTag) {

        Assert.notNull(postTag, "postTag不能为空");

        postTagMapper.insert(postTag);

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

}
