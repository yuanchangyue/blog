package com.changyue.blogserver.service;

import com.changyue.blogserver.model.entity.UserPost;
import com.changyue.blogserver.serivce.UserPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-04-10 17:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPostServiceTest {

    @Autowired
    private UserPostService userPostService;


    @Test
    public void test() {
        UserPost userPost = new UserPost();
        userPost.setCrawlerPostId(13);
        userPost.setUserId(13);
        boolean b = userPostService.isExist(userPost);
        System.out.println(b);
    }

}
