package com.changyue.blogserver.repository;

import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.repository.base.BaseRepository;
import lombok.NonNull;

import java.util.List;

public interface PostTagRepository extends BaseRepository<PostTag, Integer> {

    /**
     * 通过postTagId查询全部
     *
     * @param postTagId 文章标签id
     * @return List<PostTag>
     */
    @NonNull List<PostTag> findAllByPostId(@NonNull Integer postTagId);


}
