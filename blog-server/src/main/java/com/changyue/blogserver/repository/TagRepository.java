package com.changyue.blogserver.repository;

import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.repository.base.BaseRepository;
import org.springframework.lang.NonNull;


import java.util.Optional;

/**
 * @program: blog-server
 * @description: 标签repository
 * @author: 袁阊越
 * @create: 2020-01-20 21:07
 */
public interface TagRepository extends BaseRepository<Tag, Integer> {

    /**
     * 按名称或别名统计数量
     *
     * @param name     标签名称不能为空
     * @param slugName 标签别名不能为null
     * @return 标签数量
     */
    long countByNameOrSlugName(@NonNull String name, @NonNull String slugName);

    /**
     * 按别名获取标签
     *
     * @param slugName 别名
     * @return 标签
     */
    Optional<Tag> getBySlugName(@NonNull String slugName);

    /**
     * 按名称获取标签
     *
     * @param name name
     * @return 标签
     */
    Optional<Tag> getByName(@NonNull String name);

}
