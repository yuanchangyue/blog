package com.changyue.blogserver.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-22 19:50
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.elasticsearch.jest")
@PropertySource("classpath:elasticsearch.properties")
public class EsProperties {
    /**
     * es访问地址
     */
    private String url;

    /**
     * 读超时
     */
    private Integer readTimeout;

    /**
     * 连接超时
     */
    private Integer connectionTimeout;
}
