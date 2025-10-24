package com.hmall.feign;


import com.hmall.dto.ItemDTO;
import com.hmall.dto.OrderDetailDTO;
import com.hmall.feign.fallback.ItemFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "item-service", fallbackFactory = ItemFeignClientFallbackFactory.class)
public interface ItemServiceFeignClient {

    @GetMapping("/items")
    List<ItemDTO> queryItemByIds(@RequestParam("ids") List<Long> ids);

    @PutMapping("/items/stock/deduct")
    void deductStock(@RequestBody List<OrderDetailDTO> items);
}
