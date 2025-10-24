package com.hmall.config;

import com.hmall.feign.fallback.ItemFeignClientFallbackFactory;
import com.hmall.interceptor.UserInfoRequestInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @className: OpenfeignConfiguration
 * @author: Jonathan Ge
 * @date: 2025/10/22 8:36
 * @Version: 1.0
 * @description:
 */
public class OpenfeignConfiguration {
    // feign开启⽇志
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    RequestInterceptor userInfoRequestInterceptor(){
        return new UserInfoRequestInterceptor();
    }

    @Bean
    ItemFeignClientFallbackFactory itemFeignClientFallbackFactory(){
        return new ItemFeignClientFallbackFactory();
    }
}
