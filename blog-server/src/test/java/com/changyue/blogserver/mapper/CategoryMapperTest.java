package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.CategoryMapper;
import com.changyue.blogserver.model.entity.Category;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-01-31 19:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void testSelectByPrimaryKey() {
        Optional<Category> category = categoryMapper.selectByPrimaryKey(25);
        System.out.println("category = " + category.get());
    }

    @Test
    public void testListCategoryByPostId() {
        PageHelper.startPage(1, 5);
        List<Category> categoryList = categoryMapper.listAllByUserId(13);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        System.out.println(pageInfo);
    }

    @Test
    public void testFindAllById() {

        ArrayList<Integer> list = new ArrayList<>();
        list.add(26);
        list.add(28);

        List<Category> allById = categoryMapper.listCategoryByIds(list);
        allById.forEach(System.out::println);

    }

}
