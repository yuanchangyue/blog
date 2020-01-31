package com.changyue.blogserver.repository;


import com.changyue.blogserver.model.entity.PostCategory;
import com.changyue.blogserver.repository.base.BaseRepository;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

/**
 * @program: blog-server
 * @description: 文章类别业务接口
 * @author: 袁阊越
 * @create: 2020-01-22 21:06
 */
public interface PostCategoryRepository extends BaseRepository<PostCategory, Integer> {

    /**
     * 通过文章id 查询全部的类别
     *
     * @param postId 文章id
     * @return 文章类别列表
     */
    @NonNull
   /* @Query("select pc.categoryId from PostCategory pc where pc.postId =?1")*/
    Set<Integer> findAllCategoryIdsByPostId(@NonNull Integer postId);

    /**
     * 通过类别id 查询全部的文章
     *
     * @param categoryId 类别id
     * @return 文章id
     */
    @NonNull
    //@Query("select pc.postId from PostCategory pc where pc.categoryId = ?1")
    Set<Integer> findAllPostIdsByCategoryId(@NonNull Integer categoryId);

    /**
     * 按类别ID和文章状态查找所有文章ID。
     *
     * @param categoryId 类别ID不能为null
     * @param status     状态不能为空
     * @return 文章id列表
     */
    @NonNull
    //@Query("select postCategory.postId from PostCategory postCategory, Post post where postCategory.categoryId = ?1 and post.id = postCategory.postId and post.status = ?2")
    Set<Integer> findAllPostIdsByCategoryId(@NonNull Integer categoryId, @NonNull Integer status);

    /**
     * 通过文章ID查找所有文章类别。
     *
     * @param postId 文章ID不能为null
     * @return 文章类别列表
     */
    @NonNull
    List<PostCategory> findAllByPostId(@NonNull Integer postId);

    /**
     * 按文章ID删除文章类别。
     *
     * @param postId 文章ID不能为null
     * @return 文章类别列表已删除
     */
    @NonNull
    List<PostCategory> deleteByPostId(@NonNull Integer postId);

    /**
     * 按类别ID删除文章类别。
     *
     * @param categoryId 类别ID不能为null
     * @return 文章类别列表已删除
     */
    @NonNull
    List<PostCategory> deleteByCategoryId(@NonNull Integer categoryId);

}
