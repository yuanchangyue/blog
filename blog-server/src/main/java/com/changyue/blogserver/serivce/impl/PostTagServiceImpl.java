package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.repository.PostTagRepository;
import com.changyue.blogserver.repository.TagRepository;
import com.changyue.blogserver.serivce.PostTagService;
import com.changyue.blogserver.serivce.base.CurdServiceImpl;
import lombok.NonNull;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

/**
 * @program: blog-server
 * @description: 文章标签业务接口实现
 * @author: ChangYue
 * @create: 2020-01-21 09:11
 */
@Service
public class PostTagServiceImpl extends CurdServiceImpl<PostTag, Integer> implements PostTagService {

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> listTags(@NonNull Integer postId) {
        Assert.notNull(postId, "文章的id不能为空");
        Set<Integer> allByPostId = postTagRepository.findAllByPostId(postId);
        return tagRepository.findAllById(allByPostId);
    }

    @Override
    public List<PostTag> removeByPostTag(@Nonnull Integer postId) {
        Assert.notNull(postId, "文章的id不能为空");
        return postTagRepository.deleteByPostId(postId);
    }

    @Override
    public List<PostTag> removeByTagId(@Nonnull Integer tagId) {
        Assert.notNull(tagId, "标签的id不能为空");
        return postTagRepository.deleteByTagId(tagId);
    }

}
