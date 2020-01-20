package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.exception.AlreadyExistsException;
import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.repository.TagRepository;
import com.changyue.blogserver.serivce.TagService;
import com.changyue.blogserver.serivce.base.CurdServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.swing.CachedPainter;

import java.util.List;
import java.util.Objects;

/**
 * @program: blog-server
 * @description: 标签业务实现
 * @author: 袁阊越
 * @create: 2020-01-20 21:23
 */
@Slf4j
@Service
public class TagServiceImpl extends CurdServiceImpl<Tag, Integer> implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public Tag create(Tag tag) {
        long count = tagRepository.countByNameOrSlugName(tag.getName(), tag.getSlugName());
        log.debug("标签数量:[{}]", count);
        if (count > 0) {
            throw new AlreadyExistsException("该标签已存在").setErrData(tag);
        }
        return super.create(tag);
    }

    @Override
    public Tag getBySlugNameOfNonNull(String slugName) {
        return null;
    }

    @Override
    public Tag getBySlugName(String slugName) {
        return tagRepository.getBySlugName(slugName).orElse(null);
    }

    @Override
    public Tag getByName(String name) {
        return tagRepository.getByName(name).orElse(null);
    }

    @Override
    public TagDTO convertTo(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        BeanUtils.copyProperties(tag, tagDTO);
        return tagDTO;
    }

    @Override
    public List<TagDTO> convertTo(List<Tag> tags) {
        return null;
    }
}
