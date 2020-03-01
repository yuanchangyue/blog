package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.model.params.CategoryParam;
import com.changyue.blogserver.serivce.CategoryService;
import com.changyue.blogserver.serivce.PostCategoryService;
import com.changyue.blogserver.validator.ValidatorImpl;
import com.changyue.blogserver.validator.ValidatorResult;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : 袁阊越
 * @description : 类别控制层
 * @date : 2020/2/7/007
 */
@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostCategoryService postCategoryService;

    @Autowired
    private ValidatorImpl validator;

    @GetMapping
    public PageInfo<CategoryDTO> listCategory(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                              @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        return categoryService.list(pageIndex, pageSize);
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getBy(@PathVariable("categoryId") Integer categoryId) {
        return categoryService.convertTo(categoryService.getById(categoryId));
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryParam categoryParam) {

        ValidatorResult result = this.validator.validator(categoryParam);
        if (result.isHasError()) {
            log.debug("创建category失败[{}]", result.getErrorMsgMap());
        }
        //入参转化
        Category category = categoryParam.convertTo();

        //创建类别
        return categoryService.convertTo(categoryService.create(category));
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.removeCategoryAndPostCategory(categoryId);
    }

    @PutMapping("/{categoryId}")
    public void modifyCategory(@PathVariable("categoryId") Integer categoryId, @RequestBody CategoryParam categoryParam) {

        ValidatorResult result = this.validator.validator(categoryParam);
        if (result.isHasError()) {
            log.debug("修改category失败[{}]", result.getErrorMsgMap());
        }

        Category category = categoryService.getById(categoryId);

        //入参转化
        categoryParam.update(category);

        //更新
        categoryService.update(category);
    }
}

