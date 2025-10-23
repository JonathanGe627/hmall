package com.hmall.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: ItemServiceApplication
 * @author: Jonathan Ge
 * @date: 2025/10/21 16:42
 * @Version: 1.0
 * @description:
 */
@MapperScan("com.hmall.item.mapper")
@SpringBootApplication
public class ItemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemServiceApplication.class, args);
    }
}
