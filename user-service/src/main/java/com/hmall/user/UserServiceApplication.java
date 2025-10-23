package com.hmall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: UserServiceApplication
 * @author: Jonathan Ge
 * @date: 2025/10/21 16:59
 * @Version: 1.0
 * @description:
 */
@MapperScan("com.hmall.user.mapper")
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
