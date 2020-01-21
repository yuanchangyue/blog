package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.serivce.base.CrudService;
import lombok.NonNull;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import java.util.List;


public interface PostTagService extends CrudService<PostTag, Integer> {

    /**
     * 通过文章Id获得全部的文章标签
     *
     * @param postId 文章Id
     * @return List<Post>
     */
    @NonNull
    List<Post> listTags(@NonNull Integer postId);

    /**
     * 删除文章标签
     *
     * @param postId 文章Id
     * @return List<Post>
     */
    @NonNull
    @Transactional
    List<Post> removePostTag(@Nonnull Integer postId);


}
