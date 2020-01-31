package com.changyue.blogserver.serivce;

import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author 袁阊越
 * @title: BaseService
 * @package com.changyue.blogserver.serivce
 * @description 基础业务接口类
 * @date 2020/1/31/031
 */
public interface BaseService<DOMAIN, ID> {

    /**
     * 全部列表
     *
     * @return List
     */
    @Nullable
    default List<DOMAIN> listAll() {
        return null;
    }

    /**
     * 列出所有页面
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @return 分页列表
     */
    @NonNull
    default PageInfo<DOMAIN> listAll(@NonNull Integer pageIndex, @NonNull Integer pageSize) {
        return null;
    }

    /**
     * 按编号列出所有
     *
     * @param ids id
     * @return 列表
     */
    @NonNull
    default List<DOMAIN> listAllByIds(@Nullable Collection<ID> ids) {
        return null;
    }


    /**
     * 通过ID获取
     *
     * @param id id
     * @return Optional
     */
    @NonNull
    default Optional<DOMAIN> getById(@NonNull ID id) {
        return null;
    }


    /**
     * 通过ID查找存在
     *
     * @param id id
     * @return boolean
     */
    default boolean existsById(@NonNull ID id) {
        return false;
    }


    /**
     * 计算全部
     *
     * @return long
     */
    default long count() {
        return 0;
    }

    /**
     * 按实体保存
     *
     * @param domain 实体
     * @return 实体
     */
    @NonNull
    @Transactional
    default DOMAIN create(@NonNull DOMAIN domain) {
        return domain;
    }

    /**
     * 批量保存
     *
     * @param domains domains
     * @return 列表
     */
    @NonNull
    @Transactional
    default List<DOMAIN> createInBatch(@NonNull Collection<DOMAIN> domains) {
        return null;
    }

    /**
     * 通过实体更新
     *
     * @param domain domain
     * @return DOMAIN
     */
    @NonNull
    @Transactional
    default DOMAIN update(@NonNull DOMAIN domain) {
        return domain;
    }

    /**
     * 按ID删除
     *
     * @param id id
     * @return DOMAIN
     */
    @NonNull
    @Transactional
    default int removeById(@NonNull ID id) {
        return 0;
    }

    /**
     * 按实体删除
     *
     * @param domain domain
     */
    @Transactional(rollbackFor = Exception.class)
    default void remove(@NonNull DOMAIN domain) {}

    /**
     * 批量删除
     *
     * @param ids ids
     */
    @Transactional
    default void removeInBatch(@NonNull Collection<ID> ids) {
    }

    /**
     * 移除所有
     */
    @Transactional
    default void removeAll() {
    }
}
