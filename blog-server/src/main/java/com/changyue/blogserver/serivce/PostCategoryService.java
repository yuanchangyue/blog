package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.entity.PostCategory;
import com.changyue.blogserver.serivce.base.CrudService;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: blog-server
 * @description: 文章类别业务接口
 * @author: 袁阊越
 * @create: 2020-01-22 21:37
 */
public interface PostCategoryService extends CrudService<PostCategory, Integer> {
    /**
     * 按文章ID列出类别。
     *
     * @param postId 文章ID不能为null
     * @return 类别列表
     */
    @NonNull
    List<Category> listCategoryPostId(@NonNull Integer postId);

    /**
     * 通过类别id 查询全部的文章
     *
     * @param categoryId 类别id
     * @return 文章id
     */
    @NonNull
    List<Post> listPostByCategoryId(@NonNull Integer categoryId);


    /**
     * 按类别ID和文章状态列出文章。
     *
     * @param categoryId 类别ID不能为null
     * @param status     状态
     * @return 文章列表
     */
    @NonNull
    List<Post> listPostByCategoryIdAndStatus(@NonNull Integer categoryId, @NonNull Integer status);

    /**
     * 按类别信息名称命名(分页)。
     *
     * @param categoryId 类别ID不能为null
     * @param pageable   分页
     * @return 文章页面
     */
    @NonNull
    Page<Post> pagePostBy(@NonNull Integer categoryId, Pageable pageable);

    /**
     * 通过帖子ID删除文章类别。
     *
     * @param postId 文章ID不能为null
     * @return 文章类别列表已删除
     */
    @NonNull
    @Transactional
    List<PostCategory> removeByPostId(@NonNull Integer postId);

    /**
     * 通过类别id删除文章类别
     *
     * @param categoryId 类别ID不能为null
     * @return 文章类别列表已删除
     */
    @NonNull
    @Transactional
    List<PostCategory> removeByCategoryId(@NonNull Integer categoryId);
}
