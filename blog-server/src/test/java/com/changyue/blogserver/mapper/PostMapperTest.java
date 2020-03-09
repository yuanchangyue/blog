package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.PostMapper;
import com.changyue.blogserver.model.params.PostQuery;
import com.changyue.blogserver.model.vo.PostVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-05 22:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    public void test() {
        PostQuery postQuery = new PostQuery();
        List<PostVO> posts = postMapper.listAllByQuery(postQuery, 13);
        System.out.println(posts.size());
        posts.forEach(System.out::println);
    }
}
