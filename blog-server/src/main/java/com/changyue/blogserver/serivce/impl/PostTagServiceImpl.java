package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.repository.PostTagRepository;
import com.changyue.blogserver.repository.TagRepository;
import com.changyue.blogserver.serivce.PostTagService;
import com.changyue.blogserver.serivce.base.CurdServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

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
    public @NonNull List<Post> listTags(@NonNull Integer postId) {
        postTagRepository.findAllByPostId(postId);
        return null;
    }

    @Override
    public @NonNull List<Post> removePostTag(@Nonnull Integer postId) {
        return null;
    }

}
