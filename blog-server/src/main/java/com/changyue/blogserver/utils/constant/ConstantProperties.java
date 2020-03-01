package com.changyue.blogserver.utils.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : 袁阊越
 * @description : 常量工具类
 * @date : 2020-03-01 15:59
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "constant",ignoreUnknownFields = false)
@PropertySource(value = "classpath:constant.properties")
@Data
public class ConstantProperties {

    private  String URL;

    private String LOGIN;

}



