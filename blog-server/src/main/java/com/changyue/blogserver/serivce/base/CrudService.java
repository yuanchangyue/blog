package com.changyue.blogserver.serivce.base;

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
 * CrudService interface contains some common methods.
 *
 * @param <DOMAIN> domain type
 * @param <ID>     id type
 * @author johnniang
 */
public interface CrudService<DOMAIN, ID> {

    /**
     * 全部列表
     *
     * @return List
     */
    @NonNull
    List<DOMAIN> listAll();

    /**
     * 按排序列出所有
     *
     * @param sort sort
     * @return List
     */
    @NonNull
    List<DOMAIN> listAll(@NonNull Sort sort);

    /**
     * 列出所有页面
     *
     * @param pageable 可分页的
     * @return 页
     */
    @NonNull
    Page<DOMAIN> listAll(@NonNull Pageable pageable);

    /**
     * 按编号列出所有
     *
     * @param ids ids
     * @return 列表
     */
    @NonNull
    List<DOMAIN> listAllByIds(@Nullable Collection<ID> ids);

    /**
     * 列出所有ID并排序
     *
     * @param ids  ids
     * @param sort sort
     * @return 列表
     */
    @NonNull
    List<DOMAIN> listAllByIds(@Nullable Collection<ID> ids, @NonNull Sort sort);

    /**
     * 通过ID获取
     *
     * @param id id
     * @return Optional
     */
    @NonNull
    Optional<DOMAIN> fetchById(@NonNull ID id);

    /**
     * 通过ID获取
     *
     * @param id id
     * @return 实体
     */
    @NonNull
    DOMAIN getById(@NonNull ID id);



    /**
     * 通过ID查找存在
     *
     * @param id id
     * @return boolean
     */
    boolean existsById(@NonNull ID id);

    /**
     * Must exist by id, or throw NotFoundException.
     *
     * @param id id
     */
    void mustExistById(@NonNull ID id);

    /**
     * 计算全部
     *
     * @return long
     */
    long count();

    /**
     * 按实体保存
     *
     * @param domain 实体
     * @return 实体
     */
    @NonNull
    @Transactional
    DOMAIN create(@NonNull DOMAIN domain);

    /**
     * 批量保存
     *
     * @param domains domains
     * @return 列表
     */
    @NonNull
    @Transactional
    List<DOMAIN> createInBatch(@NonNull Collection<DOMAIN> domains);

    /**
     * 通过实体更新
     *
     * @param domain domain
     * @return DOMAIN
     */
    @NonNull
    @Transactional
    DOMAIN update(@NonNull DOMAIN domain);

    /**
     * Flushes all pending changes to the database.
     */
    void flush();

    /**
     * Updates by domains
     *
     * @param domains domains
     * @return List
     */
    @NonNull
    @Transactional
    List<DOMAIN> updateInBatch(@NonNull Collection<DOMAIN> domains);

    /**
     * Removes by id
     *
     * @param id id
     * @return DOMAIN
     */
    @NonNull
    @Transactional
    DOMAIN removeById(@NonNull ID id);

    /**
     * Remove by domain
     *
     * @param domain domain
     */
    @Transactional
    void remove(@NonNull DOMAIN domain);

    /**
     * Remove by ids
     *
     * @param ids ids
     */
    @Transactional
    void removeInBatch(@NonNull Collection<ID> ids);

    /**
     * Remove all by domains
     *
     * @param domains domains
     */
    @Transactional
    void removeAll(@NonNull Collection<DOMAIN> domains);

    /**
     * Remove all
     */
    @Transactional
    void removeAll();
}
