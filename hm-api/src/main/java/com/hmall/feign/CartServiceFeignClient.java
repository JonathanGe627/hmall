package com.hmall.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @className: ItemServiceFeignClient
 * @author: Jonathan Ge
 * @date: 2025/10/21 17:53
 * @Version: 1.0
 * @description:
 */
@FeignClient("cart-service")
public interface CartServiceFeignClient {
    @DeleteMapping("/carts")
    void deleteCartItemByIds(@RequestParam("ids") List<Long> ids);
}
