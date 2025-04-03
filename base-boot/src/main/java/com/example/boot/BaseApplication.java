package com.example.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用程序启动类
 * 
 * @author example
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@MapperScan({"com.example.auth.mapper"})
@EnableTransactionManagement
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
        System.out.println("基础构建系统");
        System.out.println("启 动 成 功");
    }
} 