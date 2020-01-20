package com.changyue.blogserver.repository.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @program: blog-server
 * @description: repository基类实现
 * @author: ChangYue
 * @create: 2020-01-20 16:28
 */
@Slf4j
public class BaseRepositoryImpl<DOMAIN, ID> extends SimpleJpaRepository<DOMAIN, ID> implements BaseRepository<DOMAIN, ID> {
    private final JpaEntityInformation<DOMAIN, ID> entityInformation;

    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<DOMAIN, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }


    @Override
    public List<DOMAIN> findAllByIdIn(Collection<ID> ids, Sort sort) {

        log.debug("findAllByIdIn 已经被调用");
        Assert.notNull(ids, "ids 不能未空");
        Assert.notNull(sort, "sort 信息不能空");

        //没有ids没有值返回一个空的collection
        if (!ids.iterator().hasNext()) {
            return Collections.emptyList();
        }

        return null;
    }

    @Override
    public Page findAllByIdIn(Collection collection, Pageable pageable) {
        return null;
    }

    @Override
    public long deleteByIdIn(Collection collection) {
        return 0;
    }
}
