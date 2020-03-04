package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.TagMapper;
import com.changyue.blogserver.exception.AlreadyExistsException;
import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.serivce.TagService;
import com.changyue.blogserver.serivce.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 标签业务实现
 * @date : 2020/2/3/003
 */
@Slf4j
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserService userService;

    /**
     * 全部列表
     *
     * @return List
     */
    @Override
    public List<Tag> listAll() {
        UserDTO currentUser = userService.getCurrentUser();
        return tagMapper.listAllByUserId(currentUser.getId());
    }

    /**
     * 列出所有页面
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @return 分页列表
     */
    @Override
    public PageInfo<Tag> listAll(Integer pageIndex, Integer pageSize) {
        UserDTO currentUser = userService.getCurrentUser();
        PageHelper.startPage(pageIndex, 5);
        List<Tag> tags = tagMapper.listAllByUserId(currentUser.getId());
        return new PageInfo<>(tags, 3);
    }

    /**
     * 按编号列出所有
     *
     * @param id id
     * @return 列表
     */
    @Override
    public List<Tag> listAllByIds(List<Integer> id) {
        return tagMapper.findTagByIds(id);
    }

    /**
     * 通过ID获取
     *
     * @param id id
     * @return Optional
     */
    @Override
    public Tag getById(Integer id) {
        Assert.notNull(id, "tag id 不能为空");
        return tagMapper.selectByPrimaryKey(id).orElse(null);
    }

    /**
     * 计算全部
     *
     * @return long
     */
    @Override
    public long count() {
        return tagMapper.countByNameOrSlugName(null, null);
    }

    @Transactional
    @Override
    public Tag create(Tag tag) {

        Assert.notNull(tag, "tag 不能为空");

        long count = tagMapper.countByNameOrSlugName(tag.getName(), tag.getSlugName());
        log.debug("标签数量:[{}]", count);
        if (count > 0) {
            throw new AlreadyExistsException("该标签已存在").setErrData(tag);
        }


        int effectNum = tagMapper.insert(tag);
        if (effectNum < 0) {
            throw new CreateException("标签创建失败").setErrData(tag);
        }
        return tag;
    }


    @Transactional
    @Override
    public Tag update(Tag tag) {
        Assert.notNull(tag, "tag 不能为空");
        tagMapper.updateByPrimaryKeySelective(tag);
        return tag;
    }

    @Override
    public int removeById(Integer id) {
        Assert.notNull(id, "tag id 不能为空");
        return tagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Tag getBySlugName(String slugName) {
        return tagMapper.findBySlugName(slugName).orElse(null);
    }

    @Override
    public Tag getByName(String name) {
        return tagMapper.findByName(name).orElse(null);
    }

    @Override
    public TagDTO convertTo(Tag tag) {
        Assert.notNull(tag, "不能为空");
        TagDTO tagDTO = new TagDTO();
        BeanUtils.copyProperties(tag, tagDTO);
        return tagDTO;
    }

    @Nonnull
    @Override
    public List<TagDTO> convertTo(List<Tag> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            return Collections.emptyList();
        }
        return tags.stream().map(this::convertTo).collect(Collectors.toList());
    }

}
