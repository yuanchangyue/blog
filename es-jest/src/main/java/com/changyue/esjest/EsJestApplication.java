package com.changyue.esjest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EsJestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsJestApplication.class, args);
    }

}
