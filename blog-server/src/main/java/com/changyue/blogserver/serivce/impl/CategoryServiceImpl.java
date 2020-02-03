package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CategoryMapper;
import com.changyue.blogserver.exception.AlreadyExistsException;
import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.exception.NotFindException;
import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.serivce.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: blog-server
 * @description: 类别业务实现
 * @author: 袁阊越
 * @create: 2020-01-22 17:27
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category create(Category category) {

        Assert.notNull(category, "类别不能为空");

        //检查类别是否存在
        long countByName = categoryMapper.countByNameOrId(category.getName(), null);
        if (countByName > 0) {
            log.error("该分类已经存在{}", category);
            throw new AlreadyExistsException("该分类已经存在");
        }
        //检查父类别是否存在
        if (category.getParentId() != null) {
            long countById = categoryMapper.countByNameOrId(null, category.getParentId());

            if (countById == 0) {
                log.error("父id: [{}] 没有找到", category.getParentId());
                throw new NotFindException("父id = " + category.getParentId() + " 没有找到");
            }
        }

        //插入数据库
        int insertNum = categoryMapper.insert(category);
        if (insertNum < 0) {
            throw new CreateException("创建类别失败").setErrData(category);
        }
        return category;
    }


    @Override
    public Category getBySlugName(String slugName) {
        return Objects.requireNonNull(categoryMapper.getBySlugName(slugName).orElse(null));
    }

    @Override
    public Category getByName(String name) {
        Assert.notNull(name, "类别名称不能为空");
        return categoryMapper.getByName(name).orElse(null);
    }

    @Override
    public List<Category> listByParentId(Integer id) {
        Assert.notNull(id, "类别Id不能为空");
        return categoryMapper.findByParentId(id);
    }

    @Override
    public CategoryDTO convertTo(Category category) {
        Assert.notNull(category, "Category 不能为空");
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        return categoryDTO;
    }

    /**
     * 装换为DTO
     *
     * @param categories 类别不能为空
     * @return 类别 dto
     */
    @Override
    public List<CategoryDTO> convertTo(List<Category> categories) {

        Assert.notNull(categories, "类别列表不能为空");

        return categories.stream().map(this::convertTo).collect(Collectors.toList());
    }


}
