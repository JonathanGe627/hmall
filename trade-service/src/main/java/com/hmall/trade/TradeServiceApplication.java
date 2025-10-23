package com.hmall.trade;

import com.hmall.config.OpenfeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: TradeServiceApplication
 * @author: Jonathan Ge
 * @date: 2025/10/21 16:59
 * @Version: 1.0
 * @description:
 */
@EnableFeignClients(basePackages = "com.hmall.feign", defaultConfiguration = OpenfeignConfiguration.class)
@MapperScan("com.hmall.trade.mapper")
@SpringBootApplication
public class TradeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeServiceApplication.class, args);
    }
}
