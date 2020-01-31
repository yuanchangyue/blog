package com.changyue.blogserver.repository;

import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.repository.base.BaseRepository;
import com.tdunning.math.stats.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.awt.print.Pageable;
import java.util.List;

/**
 * @author 袁阊越
 * @title: PostRepository
 * @package com.changyue.blogserver.repository
 * @description:
 * @date 2020/1/22/022
 */
public interface PostRepository extends BaseRepository<Post, Integer> {

    /**
     * 统计点赞数量
     *
     * @return 点赞数
     */
    //@Query("select sum(likes) from Post")
    Long countLike();

    /**
     * 通过状态统计文章
     *
     * @param status 状态
     * @return 文章数
     */
    Long countAllByStatus(@Nonnull Integer status);


    /**
     * 通过状态找到全部的文章
     *
     * @param postId 文章id
     * @return 文章
     */
    List<Post> findAllByStatus(@Nonnull Integer postId);

    /**
     * 通过状态找到全部的文章
     *
     * @param postId 文章id
     * @param sort   排序
     * @return 文章
     */
    List<Post> findAllByStatus(@Nonnull Integer postId, Sort sort);

    /**
     * 通过状态找到全部的文章
     *
     * @param postId   文章id
     * @param pageable 分页
     * @return 文章
     */
    List<Post> findAllByStatus(@Nonnull Integer postId, Pageable pageable);

    /**
     * 更新点赞。
     *
     * @param likes  点赞
     * @param postId 文章ID不能为null
     * @return 更新的行
     */
    //@Modifying
    ////@Query("update Post p set p.likes=p.likes + :likes where p.id= :postId")
    int updateLikes(@Param("likes") long likes, @Param("postId") @Nonnull Integer postId);

    /**
     * 更新状态
     *
     * @param status 状态
     * @param postId 文章Id
     * @return 更新的行
     */
    //@Modifying
    //@Query("update Post p set p.status = :status where p.id = :postId")
    int updateStatus(@Param("status") @NonNull Integer status, @Param("postId") @NonNull Integer postId);

    /**
     * 更新发布原始内容.
     *
     * @param content 内容可以为空，但不允许为空
     * @param postId  文章Id不能为null
     * @return 更新的行
     */
    //@Modifying
    //@Query("update Post p set p.originalContent = :content where p.id = :postId")
    int updateOriginalContent(@Param("content") @NonNull String content, @Param("postId") @NonNull Integer postId);


    /**
     * 通过文章Id更新文章带有格式的内容.
     *
     * @param formatContent 带有格式内容不能为空.
     * @param postId        文章ID不能为null.
     * @return 更新的行
     */
    //@Modifying
    //@Query("update Post p set p.formatContent = :formatContent where p.id = :postId")
    int updateFormatContent(@Param("formatContent") @NonNull String formatContent, @Param("postId") @NonNull Integer postId);

}
