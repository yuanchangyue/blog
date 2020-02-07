package com.changyue.blogserver.controller;

import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.model.params.CategoryParam;
import com.changyue.blogserver.serivce.CategoryService;
import com.changyue.blogserver.serivce.PostCategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : 袁阊越
 * @description : 类别控制层
 * @date : 2020/2/7/007
*/
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostCategoryService postCategoryService;

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
        //入参转化
        Category category = categoryParam.convertTo();
        //创建类别
        return categoryService.convertTo(categoryService.create(category));
    }


}
