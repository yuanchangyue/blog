package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.PostCategory;
import lombok.NonNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author : 袁阊越
 * @description : 文章类别数据接口
 * @date : 2020/2/3/003
 */
@Mapper
@Repository
public interface PostCategoryMapper extends BaseMapper<PostCategory> {

    /**
     * 通过文章id 查询全部的类别
     *
     * @param postId 文章id
     * @return 文章类别列表
     */
    @NonNull
    Set<Integer> findAllCategoryIdsByPostId(@NonNull @Param("postId") Integer postId);

    /**
     * 通过类别id 查询全部的文章
     *
     * @param categoryId 类别id
     * @return 文章id
     */
    @NonNull
    Set<Integer> findAllPostIdsByCategoryId(@NonNull @Param("categoryId") Integer categoryId);

    /**
     * 按类别ID和文章状态查找所有文章ID。
     *
     * @param categoryId 类别ID不能为null
     * @param status     状态不能为空
     * @return 文章id列表
     */
    @NonNull
    Set<Integer> findAllPostIdsByCategoryId(@NonNull @Param("categoryId") Integer categoryId,
                                            @NonNull @Param("status") Integer status);

    /**
     * 通过文章ID查找所有文章类别。
     *
     * @param postId 文章ID不能为null
     * @return 文章类别列表
     */
    @NonNull
    List<PostCategory> findAllByPostId(@NonNull @Param("postId") Integer postId);

    /**
     * 按文章ID删除文章类别。
     *
     * @param postId 文章ID不能为null
     * @return 文章类别列表已删除
     */
    @NonNull
    int deleteByPostId(@NonNull @Param("postId") Integer postId);

    /**
     * 按类别ID删除文章类别。
     *
     * @param categoryId 类别ID不能为null
     * @return 文章类别列表已删除
     */
    @NonNull
    int deleteByCategoryId(@NonNull @Param("categoryId") Integer categoryId);

    /**
     * 批量插入文章类别
     *
     * @param postCategories 文章类别
     * @return 影响行数
     */
    int insertInBatch(@Param("postCategories") Collection<PostCategory> postCategories);

}
