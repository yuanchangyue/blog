package com.changyue.blogserver.repository.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * @program: blog-server
 * @description: repository基类
 * @author: ChangYue
 * @create: 2020-01-20 15:53
 */
//
public interface BaseRepository<DOMAIN, ID> {
    /**
     * 通过id查找全部
     *
     * @param ids  id列表 (不能为空)
     * @param sort 排序 (不能为空)
     * @return 查询的实体列表
     */
    @NonNull
    List<DOMAIN> findAllByIdIn(@NonNull Collection<ID> ids, @NonNull Sort sort);

    /**
     * 按ID列表查找全部
     *
     * @param ids      id列表 不能为null
     * @param pageable 分页 不能为null
     * @return 实体列表
     */
    @NonNull
    Page<DOMAIN> findAllByIdIn(@NonNull Collection<ID> ids, @NonNull Pageable pageable);

    /**
     * 按ID删除列表.
     *
     * @param ids ID列表 不能为空
     * @return 受影响的行数
     */
    long deleteByIdIn(@NonNull Collection<ID> ids);
}
