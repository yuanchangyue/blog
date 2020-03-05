package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.CategoryMapper;
import com.changyue.blogserver.model.entity.Category;
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
        List<Category> categoryList = categoryMapper.listCategoryByPostId(7);
        categoryList.forEach(System.out::println);
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
