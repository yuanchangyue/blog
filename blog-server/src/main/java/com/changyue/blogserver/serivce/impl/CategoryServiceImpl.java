package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.exception.AlreadyExistsException;
import com.changyue.blogserver.exception.NotFindException;
import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.repository.CategoryRepository;
import com.changyue.blogserver.serivce.CategoryService;
import com.changyue.blogserver.serivce.base.CurdServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * @program: blog-server
 * @description: 类别业务实现
 * @author: 袁阊越
 * @create: 2020-01-22 17:27
 */
@Slf4j
@Service
public class CategoryServiceImpl extends CurdServiceImpl<Category, Integer> implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {

        Assert.notNull(category, "类别不能为空");

        //检查类别是否存在
        long countByName = categoryRepository.countByName(category.getName());
        if (countByName > 0) {
            log.error("该分类已经存在{}", category);
            throw new AlreadyExistsException("该分类已经存在");
        }
        //检查父类别是否存在
        if (category.getParentId() != null) {
            long countById = categoryRepository.countById(category.getParentId());

            if (countById == 0) {
                log.error("父id: [{}] 没有找到", category.getParentId());
                throw new NotFindException("父id = " + category.getParentId() + " 没有找到");
            }
        }

        return super.create(category);
    }

    @Override
    public Category getBySlugName(String slugName) {
        return Objects.requireNonNull(categoryRepository.getBySlugName(slugName).orElse(null));
    }

    @Override
    public Category getByName(String name) {
        Assert.notNull(name, "类别名称不能为空");
        return categoryRepository.getByName(name).orElse(null);
    }

    @Override
    public List<Category> listByParentId(Integer id) {
        Assert.notNull(id, "类别Id不能为空");
        return categoryRepository.findByParentId(id);
    }

    @Override
    public CategoryDTO convertTo(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        return categoryDTO;
    }
}
