package com.changyue.blogserver.controller;

import com.changyue.blogserver.serivce.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: blog-server
 * @description: 类别控制层
 * @author: 袁阊越
 * @create: 2020-01-22 17:43
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;




}
