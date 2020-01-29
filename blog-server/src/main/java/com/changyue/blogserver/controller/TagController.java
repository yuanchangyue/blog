package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.model.params.TagParam;
import com.changyue.blogserver.repository.TagRepository;
import com.changyue.blogserver.serivce.PostTagService;
import com.changyue.blogserver.serivce.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: blog-server
 * @description: 标签控制层
 * @author: 袁阊越
 * @create: 2020-01-20 22:14
 */
@Slf4j
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private PostTagService postTagService;

    @PostMapping
    public TagDTO createTag(@Valid @RequestBody TagParam tagParam) {

        Tag tag = new Tag();

        BeanUtils.copyProperties(tagParam, tag);

        Tag createdTag = tagService.create(tag);

        log.debug("创建标签成功: [{}]", createdTag);

        return tagService.convertTo(createdTag);
    }

    @GetMapping("/{tagId}")
    public TagDTO getById(@PathVariable("tagId") Integer tagId) {
        return tagService.convertTo(tagService.getById(tagId));
    }

    @PutMapping("/{tagId}")
    public TagDTO updateBy(@PathVariable("tagId") Integer tagId,
                           @Valid @RequestBody TagParam tagParam) {

        //获得tag
        Tag tag = tagService.getById(tagId);

        //将tag中值更新为tagParam
        tagParam.update(tag);

        return tagService.convertTo(tagService.update(tag));
    }

    @DeleteMapping("/{tagTag}")
    public TagDTO deleteTag(@PathVariable("tagTag") Integer tagId) {

        //删除tag
        Tag tag = tagService.removeById(tagId);

        postTagService.removePostTag(tagId);

        return tagService.convertTo(tag);
    }
}
