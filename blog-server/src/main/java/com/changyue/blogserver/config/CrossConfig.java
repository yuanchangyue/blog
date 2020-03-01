package com.changyue.blogserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author : 袁阊越
 * @description : 跨域请求配置
 * @date : 2020-02-05 10:31
 */
@Configuration
public class CrossConfig extends WebMvcConfigurationSupport {

    /**
     * 添加跨域
     *
     * @param registry 注册器
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

}
