package com.hmall.pay;

import com.hmall.config.OpenfeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: PayServiceApplication
 * @author: Jonathan Ge
 * @date: 2025/10/21 16:42
 * @Version: 1.0
 * @description:
 */
@EnableFeignClients(basePackages = "com.hmall.feign", defaultConfiguration = OpenfeignConfiguration.class)
@MapperScan("com.hmall.pay.mapper")
@SpringBootApplication
public class PayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayServiceApplication.class, args);
    }
}
