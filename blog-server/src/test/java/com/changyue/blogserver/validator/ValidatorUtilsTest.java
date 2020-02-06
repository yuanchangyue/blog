package com.changyue.blogserver.validator;

import com.changyue.blogserver.model.params.UserParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author : 袁阊越
 * @description : 参数验证测试
 * @date : 2020-02-05 14:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatorUtilsTest {

    @Autowired
    private ValidatorImpl validator;

    @Test
    public void testUserParam() {
        ValidatorResult validator = this.validator.validator(new UserParam());
        if (validator.isHasError()) {
            Map<String, String> errorMsgMap = validator.getErrorMsgMap();
            System.out.println("errorMsgMap = " + errorMsgMap);
        }
    }

}
