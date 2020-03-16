package com.changyue.blogserver.ulits;

import com.changyue.blogserver.config.properties.BlogProperties;
import com.changyue.blogserver.utils.LocalImgUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author : 袁阊越
 * @description : 其他测试
 * @date : 2020-03-15 14:04
 */
public class OtherTest {
    public static void main(String[] args) {
        System.out.println(BlogProperties.URL_SEPARATOR + BlogProperties.PREFIX + BlogProperties.URL_SEPARATOR + "**");
        System.out.println(BlogProperties.FILE_PROTOCOL + BlogProperties.WORK_DIR + BlogProperties.PREFIX + BlogProperties.URL_SEPARATOR);

        Path path = Paths.get(BlogProperties.WORK_DIR,"upload/2020/MARCH/test.jpg" );
        Path fileName = path.getFileName();
        System.out.println("fileName = " + fileName);

        LocalImgUtils.delete("upload/2020/MARCH/test.jpg");
    }
}
