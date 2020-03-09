package com.changyue.blogserver.controller;

import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.model.params.TagParam;
import com.changyue.blogserver.model.rep.CommonReturnType;
import com.changyue.blogserver.serivce.*;
import com.changyue.blogserver.serivce.impl.UserServiceImpl;
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
    private UserService userService;

    @Autowired
    private ValidatorImpl validator;

    @GetMapping
    public List<TagDTO> listTags() {
        return tagService.convertTo(tagService.listAll());
    }

    @PostMapping
    public TagDTO createTag(@RequestBody TagParam tagParam) {

        ValidatorResult result = this.validator.validator(tagParam);
        if (result.isHasError()) {
            log.debug("创建tag失败[{}]", result.getErrorMsgMap());
            throw new CreateException("创建tag失败:" + result.getErrMsg());
        }

        Tag tag = tagParam.convertTo();

        UserDTO currentUser = userService.getCurrentUser();

        tag.setUserId(currentUser.getId());

        Tag createdTag = tagService.create(tag);

        log.debug("创建标签成功: [{}]", createdTag.getName());

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
    public CommonReturnType<Integer> deleteTag(@PathVariable("tagTag") Integer tagId) {

        //删除tag
        tagService.removeById(tagId);

        //删除文章标签的关联
        postTagService.removeByTagId(tagId);


        return CommonReturnType.create(tagId);
    }

    @GetMapping("/list")
    public CommonReturnType<List<TagDTO>> lisTagsByUserId() {
        return CommonReturnType.create(tagService.getListByUserId());
    }
}
