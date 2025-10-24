package com.hmall.feign.fallback;

import com.hmall.common.exception.BizIllegalException;
import com.hmall.dto.ItemDTO;
import com.hmall.dto.OrderDetailDTO;
import com.hmall.feign.ItemServiceFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collections;
import java.util.List;

/**
 * @className: ItemFeignClientFallbackFactory
 * @author: Jonathan Ge
 * @date: 2025/10/23 19:44
 * @Version: 1.0
 * @description:
 */
@Slf4j
public class ItemFeignClientFallbackFactory implements FallbackFactory<ItemServiceFeignClient> {
    @Override
    public ItemServiceFeignClient create(Throwable cause) {
        return new ItemServiceFeignClient() {
            @Override
            public List<ItemDTO> queryItemByIds(List<Long> ids) {
                log.error("远程调用ItemServiceFeignClient#queryItemByIds方法出现异常，参数：{}", ids, cause);
                return Collections.emptyList();
            }

            @Override
            public void deductStock(List<OrderDetailDTO> items) {
                log.error("远程调用ItemServiceFeignClient#deductStock方法出现异常");
                throw new BizIllegalException(cause);
            }
        };
    }
}
