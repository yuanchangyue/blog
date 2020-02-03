package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.params.PostParam;
import com.changyue.blogserver.model.params.PostQuery;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author : 袁阊越
 * @description : 文章业务层接口
 * @date : 2020/2/3/003
 */
public interface PostService extends BaseService<Post, Integer> {

    /**
     * 分页的文章
     *
     * @param postQuery 文章查询的条件
     * @return 分页的文章
     */
    @Nonnull
    PageInfo<Post> pageBy(@NonNull PostQuery postQuery, @NotNull Pageable pageable);

    /**
     * 创建文章
     *
     * @param postParam  文化内容
     * @param tagIds     标题Id集合
     * @param categoryId 类别Id集合
     * @return 文章
     */
    @Nonnull
    Post createBy(@Nonnull PostParam postParam, Set<Integer> tagIds, Set<Integer> categoryId);


    /**
     * 按文章，标签ID集和类别ID集更新文章。
     *
     * @param postToUpdate 要更新的文章不能为空
     * @param tagIds       标签ID集
     * @param categoryIds  类别ID集
     * @return 更新的文章
     */
    @NonNull
    Post updateBy(@NonNull Post postToUpdate, Set<Integer> tagIds, Set<Integer> categoryIds);

    /**
     * 按文章状态和网址获取文章。
     *
     * @param status 文章状态不能为空
     * @param url    文章网址不能为空
     * @return 文章信息
     */
    @NonNull
    Post getBy(@NonNull Integer status, @NonNull String url);


    /**
     * 统计点赞数
     *
     * @return 点赞数
     */
    long countLike();

    /**
     * 按状态计数文章。
     *
     * @param status status
     * @return 文章数
     */
    long countByStatus(Integer status);


}
