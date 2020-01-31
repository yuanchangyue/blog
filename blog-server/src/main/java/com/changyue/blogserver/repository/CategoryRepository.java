package com.changyue.blogserver.repository;

import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.repository.base.BaseRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * @program: blog-server
 * @description: 类别
 * @author: 袁阊越
 * @create: 2020-01-22 17:14
 */

public interface CategoryRepository extends BaseRepository<Category, Integer> {

    /**
     * 按类别名称计数。
     *
     * @param name 类别名称不能为空
     * @return 数量
     */
    long countByName(@Nonnull String name);

    /**
     * 按类别ID计数。
     *
     * @param id 类别ID不能为null
     * @return 数量
     */
    long countById(@NonNull Integer id);

    /**
     * 按别名获取类别
     *
     * @param slugName 别名
     * @return 类别
     */
    Optional<Category> getBySlugName(@NonNull String slugName);

    /**
     * 按名称获取类别。
     *
     * @param name 名字
     * @return 类别
     */
    Optional<Category> getByName(@NonNull String name);


    /**
     * 按父ID列出类别。
     *
     * @param id 父Id.
     * @return 类别列表
     */
    List<Category> findByParentId(@NonNull Integer id);
}
