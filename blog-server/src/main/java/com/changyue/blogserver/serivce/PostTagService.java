package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.model.entity.Tag;
import org.omg.CORBA.INTERNAL;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;


/**
 * @author 袁阊越
 * @date 2020/1/22/022
 */
public interface PostTagService extends BaseService<PostTag, Integer> {

    /**
     * 根据文章id获得全部标签id
     *
     * @param postId 文章ID
     * @return id集合
     */
    Set<Integer> listTagsByPostId(@NonNull Integer postId);

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
    int removeByPostId(@NonNull Integer postId);

    /**
     * 根据tag删除文章标签
     *
     * @param tagId Id
     * @return List<Post>
     */
    @NonNull
    int removeByTagId(@NonNull Integer tagId);

}
