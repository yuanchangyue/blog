package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CategoryMapper;
import com.changyue.blogserver.exception.AlreadyExistsException;
import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.exception.NotFindException;
import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.serivce.CategoryService;
import com.changyue.blogserver.serivce.PostCategoryService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 类别业务实现
 * @date : 2020/2/7/007
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private PostCategoryService postCategoryService;

    @Autowired
    private UserService userService;

    @Override
    public Category create(Category category) {

        Assert.notNull(category, "类别不能为空");
        Assert.notNull(category.getUserId(), "用户ID不能为空");

        //检查类别是否存在
        Category cateWithName = new Category();
        cateWithName.setName(category.getName());
        long countByName = categoryMapper.count(cateWithName);
        if (countByName > 0) {
            log.error("该分类已经存在{}", category);
            throw new AlreadyExistsException("该分类已经存在");
        }
        //检查父类别是否存在
        if (category.getParentId() != null) {
            Category cateWithParenId = new Category();
            cateWithParenId.setParentId(category.getParentId());
            long countById = categoryMapper.count(cateWithParenId);

            if (countById == 0) {
                log.error("父id: [{}] 没有找到", category.getParentId());
                throw new NotFindException("父id = " + category.getParentId() + " 没有找到");
            }
        }

        //插入数据库
        int effectNum = categoryMapper.insert(category);
        if (effectNum < 0) {
            throw new CreateException("创建类别失败").setErrData(category);
        }

        return category;
    }

    @Override
    public Category getBySlugName(String slugName) {
        return categoryMapper.findBySlugName(slugName).orElse(null);
    }

    @Override
    public Category getByName(String name) {
        return categoryMapper.findByName(name).orElse(null);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id).orElse(null);
    }

    @Override
    public List<Category> listByParentId(Integer id) {
        Assert.notNull(id, "类别Id不能为空");
        return categoryMapper.findByParentId(id);
    }

    @Override
    public List<Category> listAllByIds(List<Integer> ids) {
        return categoryMapper.findCategoryByIds(new ArrayList<>(ids));
    }

    @Override
    public PageInfo<CategoryDTO> list(Integer pageIndex, Integer pageSize) {

        Assert.notNull(pageIndex, "页索引不能为空");
        Assert.notNull(pageSize, "页数不能为空");

        //获得当前用户
        UserDTO currentUser = userService.getCurrentUser();

        //分页
        PageHelper.startPage(pageIndex, pageSize);
        List<Category> categoryList = categoryMapper.listAllByUserId(currentUser.getId());
        List<CategoryDTO> categoryDTOS = convertTo(categoryList);

        return new PageInfo<>(categoryDTOS, 3);
    }

    @Override
    @Transactional
    public void removeCategoryAndPostCategory(Integer categoryId) {

        //处理如果是父级类别被使用
        List<Category> categories = this.listByParentId(categoryId);
        if (!CollectionUtils.isEmpty(categories)) {
            categories.forEach(category -> {
                category.setParentId(0);
                this.update(category);
            });
        }

        //删除类别
        this.removeById(categoryId);
        //删除文章类别
        postCategoryService.removeByCategoryId(categoryId);
    }

    @Override
    public int removeById(Integer categoryId) {
        Assert.notNull(categoryId, "类别Id不能为空");
        return categoryMapper.deleteByPrimaryKey(categoryId);
    }

    @Override
    public Category update(Category category) {
        Assert.notNull(category, "类别不能为空");
        categoryMapper.updateByPrimaryKeySelective(category);
        return category;
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
