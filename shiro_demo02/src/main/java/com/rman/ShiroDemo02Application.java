package com.rman;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.rman.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class ShiroDemo02Application {

    public static void main(String[] args) {
        SpringApplication.run(ShiroDemo02Application.class, args);
    }

}
