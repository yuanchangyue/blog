package com.changyue.blogserver;

import com.changyue.blogserver.repository.base.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * @author 袁阊越
 * @date 2020/1/18/018
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.changyue.blogserver.repository", repositoryBaseClass = BaseRepositoryImpl.class)
public class BlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServerApplication.class, args);
    }

}
