package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.UserSiteMapper;
import com.changyue.blogserver.model.entity.UserSite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-04-10 16:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSiteMapperTest {

    @Autowired
    private UserSiteMapper userSiteMapper;

    @Test
    public void test() {
        UserSite userSite = userSiteMapper.findUserSite(13, 1334);
        System.out.println("userSite = " + userSite);
    }
}
