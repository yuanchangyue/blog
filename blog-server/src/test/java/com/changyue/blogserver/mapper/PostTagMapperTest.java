package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.PostTagMapper;
import com.changyue.blogserver.dao.TagMapper;
import com.changyue.blogserver.model.entity.PostTag;
import com.changyue.blogserver.model.entity.Tag;
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
public class PostTagMapperTest {

    @Autowired
    private PostTagMapper postTagMapper;

    @Test
    public void testFindTagByIds() {
        ArrayList<PostTag> collect = new ArrayList<>();
        collect.add(new PostTag(1, 3));
        collect.add(new PostTag(1, 2));

        postTagMapper.insetInBatch(collect);
    }

    @Test
    public  void test() {
        postTagMapper.findAllByPostId(26).forEach(System.out::println);
    }

}
