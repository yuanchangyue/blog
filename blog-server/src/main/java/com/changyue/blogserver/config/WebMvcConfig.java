package com.changyue.blogserver.config;

import com.changyue.blogserver.config.properties.BlogProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author : 袁阊越
 * @description : 跨域请求配置
 * @date : 2020-02-05 10:31
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

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

    /**
     * 添加映射地址
     *
     * @param registry 注册器
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(BlogProperties.URL_SEPARATOR + BlogProperties.PREFIX + BlogProperties.URL_SEPARATOR + "**")
                .addResourceLocations(BlogProperties.FILE_PROTOCOL + BlogProperties.WORK_DIR + BlogProperties.PREFIX + BlogProperties.URL_SEPARATOR);
        super.addResourceHandlers(registry);
    }

}
