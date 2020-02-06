package com.changyue.blogserver.ulits;

import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.params.UserParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description : param装换工具类
 * @date : 2020-02-05 15:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertorUtlis {

    @Test
    public void testConvertTo() {
        UserParam userParam = new UserParam();
        userParam.setUsername("changyue");
        userParam.setDescription("changyue");
        userParam.setNickname("changyue");
        userParam.setEmail("changyue");
        userParam.setPassword("123");

        User user = userParam.convertTo();

        System.out.println(user);
    }

}
