package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.model.entity.Tag;
import lombok.NonNull;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author : 袁阊越
 * @description : 文章标签数据接口
 * @date : 2020/2/3/003
 */
@Repository
public interface PostTagMapper extends BaseMapper<PostTag> {

    /**
     * 通过postId查询全部postTagId
     *
     * @param postId 文章标签id
     * @return List<PostTag>
     */
    @NonNull
    Set<Integer> findAllByPostId(@NonNull @Param("postId") Integer postId);

    /**
     * 通过用户id 查询到全部的用户列表
     *
     * @return 用户列表
     */
    List<TagDTO> findAllByUserId(@NonNull @Param("userId") Integer userId);

    /**
     * 通过标签id删除文章标签
     *
     * @param tagId 文章ID不能为null
     * @return 文章标签列表已删除
     */
    int deleteByTagId(@Nonnull @Param("tagId") Integer tagId);

    /**
     * 通过文章id删除文章标签
     *
     * @param postId 文章ID不能为null
     * @return 文章标签列表已删除
     */
    int deleteByPostId(@Nonnull @Param("postId") Integer postId);

    /**
     * 通过post id统计文章的标签数量
     *
     * @param postId 文章id
     * @return 文章的标签数量
     */
    long countPostTag(@Nonnull @Param("postId") Integer postId);

    /**
     * 批量插入文章的标签
     *
     * @param postTags 文章标签列表
     * @return 影响行数
     */
    int insetInBatch(@Param("postTags") Collection<PostTag> postTags);
}
