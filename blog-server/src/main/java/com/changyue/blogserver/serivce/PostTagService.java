package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.serivce.base.CrudService;
import lombok.NonNull;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import java.util.List;


/**
 * @author 袁阊越
 * @date 2020/1/22/022
 */
public interface PostTagService extends CrudService<PostTag, Integer> {

    /**
     * 通过文章Id获得全部的文章标签
     *
     * @param postId 文章Id
     * @return List<Post>
     */
    @NonNull
    List<Tag> listTags(@NonNull Integer postId);

    /**
     * 根据文章Id删除文章标签
     *
     * @param postId 文章Id
     * @return List<Post>
     */
    @NonNull
    @Transactional
    List<PostTag> removeByPostTag(@Nonnull Integer postId);

    /**
     * 根据tag删除文章标签
     *
     * @param tagId Id
     * @return List<Post>
     */
    @NonNull
    @Transactional
    List<PostTag> removeByTagId(@Nonnull Integer tagId);

}
