package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.model.params.TagParam;
import com.changyue.blogserver.model.rep.CommonReturnType;
import com.changyue.blogserver.serivce.PostTagService;
import com.changyue.blogserver.serivce.TagService;
import com.changyue.blogserver.validator.ValidatorImpl;
import com.changyue.blogserver.validator.ValidatorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : 标签控制层
 * @date : 2020-01-20 22:14
 */
@Slf4j
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private PostTagService postTagService;

    @Autowired
    private ValidatorImpl validator;

    @GetMapping
    public List<TagDTO> listTags(@RequestParam(name = "more", required = false, defaultValue = "false") Boolean more) {
        return tagService.convertTo(tagService.listAll());
    }

    @PostMapping
    @Transactional
    public TagDTO createTag(@Valid @RequestBody TagParam tagParam) {

        ValidatorResult result = this.validator.validator(tagParam);
        if (result.isHasError()) {
            System.out.println(result.getErrorMsgMap());
            log.debug("创建tag失败[{}]",result.getErrorMsgMap());
        }

        Tag tag = new Tag();

        BeanUtils.copyProperties(tagParam, tag);

        Tag createdTag = tagService.create(tag);

        log.debug("创建标签成功: [{}]", createdTag);

        return tagService.convertTo(createdTag);
    }

    @GetMapping("/{tagId}")
    public TagDTO getById(@PathVariable("tagId") Integer tagId) {
        return tagService.convertTo(tagService.getById(tagId).orElse(null));
    }

    @PutMapping("/{tagId}")
    @Transactional
    public TagDTO updateBy(@PathVariable("tagId") Integer tagId,
                           @Valid @RequestBody TagParam tagParam) {

        //获得tag
        Tag tag = tagService.getById(tagId).orElse(null);

        //将tag中值更新为tagParam
        tagParam.update(tag);

        return tagService.convertTo(tagService.update(tag));
    }

    @DeleteMapping("/{tagTag}")
    @Transactional
    public CommonReturnType<Integer> deleteTag(@PathVariable("tagTag") Integer tagId) {

        //删除tag
        tagService.removeById(tagId);

        //删除文章标签的关联
        postTagService.removeByTagId(tagId);

        return CommonReturnType.create(tagId);
    }
}
