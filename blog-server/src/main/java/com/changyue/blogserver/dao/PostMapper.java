package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.params.PostQuery;
import com.changyue.blogserver.model.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : 文章数据接口层
 * @date : 2020/2/3/003
 */
@Mapper
@Repository
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 统计点赞数量
     *
     * @return 点赞数
     */
    Long countLike();

    /**
     * 通过状态统计文章
     *
     * @param status 状态
     * @return 文章数
     */
    Long countAllByStatus(@Nonnull Integer status);

    /**
     * 获得加密密码
     *
     * @param postId id
     * @return 加密密码
     */
    String findPasswordById(@Nonnull Integer postId);

    /**
     * 通过状态找到全部的文章
     *
     * @param postId 文章id
     * @return 文章
     */
    List<Post> findAllByStatus(@Nonnull Integer postId);

    /**
     * 通过状态用户Id找到全部的文章
     *
     * @param userId 用户id
     * @return 文章
     */
    List<Post> listAllByUserId(@Nonnull @Param("userId") Integer userId);

    /**
     * 通过状态用户Id找到全部的文章
     *
     * @param postQuery 文章查询条件
     * @param userId    用户id
     * @return 文章
     */
    List<PostVO> listAllByQuery(@Nonnull @Param("postQuery") PostQuery postQuery, @Param("userId") Integer userId);

    /**
     * 更新点赞。
     *
     * @param likes  点赞
     * @param postId 文章ID不能为null
     * @return 更新的行
     */
    int updateLikes(@Param("likes") long likes, @Param("postId") @Nonnull Integer postId);

    /**
     * 更新状态
     *
     * @param status 状态
     * @param postId 文章Id
     * @return 更新的行
     */
    int updateStatus(@Param("status") @NonNull Integer status, @Param("postId") @NonNull Integer postId);

    /**
     * 更新发布原始内容.
     *
     * @param content 内容可以为空，但不允许为空
     * @param postId  文章Id不能为null
     * @return 更新的行
     */
    int updateOriginalContent(@Param("content") @NonNull String content, @Param("postId") @NonNull Integer postId);


    /**
     * 通过文章Id更新文章带有格式的内容.
     *
     * @param formatContent 带有格式内容不能为空.
     * @param postId        文章ID不能为null.
     * @return 更新的行
     */
    int updateFormatContent(@Param("formatContent") @NonNull String formatContent, @Param("postId") @NonNull Integer postId);


}
