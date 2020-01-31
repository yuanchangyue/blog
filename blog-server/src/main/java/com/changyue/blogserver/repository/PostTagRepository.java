package com.changyue.blogserver.repository;

import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.repository.base.BaseRepository;
import lombok.NonNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

/**
 * @author 袁阊越
 * @date 2020/1/22/022
 */
public interface PostTagRepository extends BaseRepository<PostTag, Integer> {

    /**
     * 通过postId查询全部postTagId
     *
     * @param postId 文章标签id
     * @return List<PostTag>
     */
    @NonNull
    //@Query("select pt.tagId from PostTag pt where pt.postId =?1")
    Set<Integer> findAllByPostId(@NonNull Integer postId);

    /**
     * 通过标签id删除文章标签
     *
     * @param tagId post id must not be null
     * @return a list of post tag deleted
     */
    List<PostTag> deleteByTagId(@Nonnull Integer tagId);

    /**
     * 通过文章id删除文章标签
     *
     * @param postId post id must not be null
     * @return a list of post tag deleted
     */
    List<PostTag> deleteByPostId(@Nonnull Integer postId);


}
