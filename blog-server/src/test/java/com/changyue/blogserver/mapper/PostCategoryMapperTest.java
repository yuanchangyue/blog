package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.PostCategoryMapper;
import com.changyue.blogserver.dao.PostTagMapper;
import com.changyue.blogserver.model.entity.PostCategory;
import com.changyue.blogserver.model.entity.PostTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-01-31 19:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostCategoryMapperTest {

    @Autowired
    private PostCategoryMapper  postCategoryMapper;

    @Test
    public void testInsertInBatch() {

        ArrayList<PostCategory> collect = new ArrayList<>();

        collect.add(new PostCategory(1, 3));
        collect.add(new PostCategory(1, 2));

        postCategoryMapper.insertInBatch(collect);

    }

    @Test
    public void test() {
        postCategoryMapper.findAllCategoryIdsByPostId(26).forEach(System.out::println);
    }

}
