package com.changyue.blogserver.controller;

import com.changyue.blogserver.annotation.MyLog;
import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.model.entity.UserCategory;
import com.changyue.blogserver.model.params.CategoryParam;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.serivce.CategoryService;
import com.changyue.blogserver.serivce.PostCategoryService;
import com.changyue.blogserver.serivce.UserCategoryService;
import com.changyue.blogserver.serivce.UserService;
import com.changyue.blogserver.utils.ShiroUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    private UserCategoryService userCategoryService;

    @Autowired
    private UserService userService;

    @MyLog("查询分类列表")
    @GetMapping
    public PageInfo<CategoryDTO> listCategory(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                              @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        return categoryService.pageBy(pageIndex, pageSize);
    }
    @MyLog("查询单个分类信息")
    @GetMapping("/{categoryId}")
    public CategoryDTO getBy(@PathVariable("categoryId") Integer categoryId) {
        return categoryService.convertTo(categoryService.getById(categoryId));
    }

    @MyLog("查询最新分类")
    @GetMapping("/latest")
    public Result getLatest() {
        return Result.create(categoryService.getListLatest());
    }


    @MyLog("新增分类")
    @PostMapping
    public Result createCategory(@Valid @RequestBody CategoryParam categoryParam) {
        //入参转化
        Category category = categoryParam.convertTo();

        //创建类别
        Category createdCategory = categoryService.create(category);

        //创建用户类别的关联
        userCategoryService.create(new UserCategory(ShiroUtils.getUser().getId(), createdCategory.getId()));

        //创建类别
        return Result.create(categoryService.convertTo(createdCategory));
    }


    @MyLog("删除分类")
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.removeCategoryAndPostCategory(categoryId);
    }

    @MyLog("修改分类")
    @PutMapping("/{categoryId}")
    public void modifyCategory(@PathVariable("categoryId") Integer categoryId, @Valid @RequestBody CategoryParam categoryParam) {

        //查找需要修改类别
        Category category = categoryService.getById(categoryId);

        //入参转化
        categoryParam.update(category);

        //更新
        categoryService.update(category);
    }

    @GetMapping("/list")
    public Result listByUserId() {
        return Result.create(categoryService.getListCategoryByUserId());
    }

    @GetMapping("/parent")
    public Result listByNull() {
        return Result.create(categoryService.getListCategoryByNull());
    }

}

