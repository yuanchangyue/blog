package com.changyue.blogserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author 袁阊越
 * @date 2020/1/18/018
 */
@SpringBootApplication
@MapperScan("com.changyue.blogserver.dao")
public class BlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServerApplication.class, args);
    }

}
