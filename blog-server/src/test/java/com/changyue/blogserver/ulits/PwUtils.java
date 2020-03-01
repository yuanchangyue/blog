package com.changyue.blogserver.ulits;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author : 袁阊越
 * @description : 密码解密测试
 * @date : 2020-02-28 10:22
 */
public class PwUtils {

    @Test
    public void passwordTest() {
        boolean checkpw = BCrypt.checkpw("yuanwu05", "$2a$10$VMKv/Ps9dGNUY3JQ8yCYxOOfcr8Oqstq.YH8brHnAIkYf3F8A3bS2");
        System.out.println("checkpw = " + checkpw);
    }

}
