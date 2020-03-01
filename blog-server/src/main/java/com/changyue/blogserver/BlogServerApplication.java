package com.changyue.blogserver;

import com.changyue.blogserver.utils.constant.ConstantProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * @author 袁阊越
 * @date 2020/1/18/018
 */
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.changyue.blogserver.repository", repositoryBaseClass = BaseRepositoryImpl.class)
@MapperScan("com.changyue.blogserver.dao")
@EnableConfigurationProperties(ConstantProperties.class)
public class BlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServerApplication.class, args);
    }

}
