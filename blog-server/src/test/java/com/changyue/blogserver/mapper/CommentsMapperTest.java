package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.CommentsMapper;
import com.changyue.blogserver.model.entity.Comments;
import com.changyue.blogserver.model.params.CommentParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-04-03 22:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsMapperTest {

    @Autowired
    private CommentsMapper commentsMapper;

    @Test
    public void test() {
        CommentParam commentParam = new CommentParam();
        commentParam.setUserId(13);
        List<Comments> comments = commentsMapper.listBy(commentParam);
        comments.forEach(System.out::println);
    }

}
