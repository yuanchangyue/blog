package com.changyue.blogserver.ulits;

import com.changyue.blogserver.utils.ReadJsonFileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : 袁阊越
 * @description : 读取json文件
 * @date : 2020-03-23 14:09
 */
@SpringBootTest
public class ReadJsonTest {
    @Test
    public void test() {
        System.out.println("ReadJsonFileUtils.readJsonFile() = " + ReadJsonFileUtils.readJsonFile());
    }
}
