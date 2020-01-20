package com.changyue.blogserver.serivce.base;

import com.changyue.blogserver.exception.NotFindException;
import com.changyue.blogserver.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @program: blog-server
 * @description:
 * @author: 袁阊越
 * @create: 2020-01-20 21:25
 */
public class CurdServiceImpl<DOMAIN, ID> implements CrudService<DOMAIN, ID> {

    @Autowired
    private BaseRepository<DOMAIN, ID> repository;

    @Override
    public List listAll() {
        return null;
    }

    @Override
    public List listAll(Sort sort) {
        return null;
    }

    @Override
    public Page listAll(Pageable pageable) {
        return null;
    }

    @Override
    public List listAllByIds(Collection collection) {
        return null;
    }

    @Override
    public List listAllByIds(Collection collection, Sort sort) {
        return null;
    }

    @Override
    public Optional fetchById(Object o) {
        return Optional.empty();
    }

    @Override
    public DOMAIN getById(ID id) {
        return repository.findById(id).orElseThrow(() -> new NotFindException("没有找到"));
    }

    @Override
    public boolean existsById(Object o) {
        return false;
    }

    @Override
    public void mustExistById(Object o) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public DOMAIN create(DOMAIN domain) {
        Assert.notNull(domain, "数据不能为空");
        return repository.save(domain);
    }

    @Override
    public List createInBatch(Collection collection) {
        return null;
    }

    @Override
    public DOMAIN update(DOMAIN domain) {
        Assert.notNull(domain, domain + " data must not be null");
        return repository.saveAndFlush(domain);
    }

    @Override
    public void flush() {

    }

    @Override
    public List updateInBatch(Collection collection) {
        return null;
    }

    @Override
    public DOMAIN removeById(ID id) {
        DOMAIN domain = this.getById(id);
        remove(domain);
        return domain;
    }


    @Override
    public void remove(DOMAIN domain) {
        repository.delete(domain);
    }

    @Override
    public void removeInBatch(Collection collection) {

    }

    @Override
    public void removeAll(Collection collection) {

    }

    @Override
    public void removeAll() {

    }
}
