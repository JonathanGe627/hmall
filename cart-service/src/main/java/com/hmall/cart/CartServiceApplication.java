package com.hmall.cart;

import com.hmall.config.OpenfeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: CartServiceApplication
 * @author: Jonathan Ge
 * @date: 2025/10/21 16:27
 * @Version: 1.0
 * @description:
 */
@EnableFeignClients(basePackages = "com.hmall.feign", defaultConfiguration = OpenfeignConfiguration.class)
@MapperScan("com.hmall.cart.mapper")
@SpringBootApplication
public class CartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }
}
