package com.changyue.blogserver.utils.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : 袁阊越
 * @description : 常量工具类
 * @date : 2020-03-01 15:59
 */
@Component
@ConfigurationProperties(prefix = "constant.return")
@PropertySource("classpath:constant.properties")
public class ConstantUtils {

    public static String URL;

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        ConstantUtils.URL = URL;
        }
}


