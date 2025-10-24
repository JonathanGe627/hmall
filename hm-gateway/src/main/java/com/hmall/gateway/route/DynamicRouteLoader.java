package com.hmall.gateway.route;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * @className: DynamicRouteLoader
 * @author: Jonathan Ge
 * @date: 2025/10/23 17:47
 * @Version: 1.0
 * @description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicRouteLoader {

    private final NacosConfigManager nacosConfigManager;

    private final RouteDefinitionWriter routeDefinitionWriter;

    private final String dataId = "gateway-routes.json";

    private final String group = "DEFAULT_GROUP";

    // 使用ConcurrentHashMap.newKeySet()防止并发安全问题
    private final Set<String> routeIdSet = ConcurrentHashMap.newKeySet();

    @PostConstruct
    public void initRouteConfigListener() throws NacosException {
        log.info("动态路由监听器初始化。。。");
        // 1.获取configService
        ConfigService configService = nacosConfigManager.getConfigService();
        // 2.读取配置并添加监听器
        String configInfo = configService.getConfigAndSignListener(dataId, group, 5000L, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("动态路由监听器被触发，更新路由。。。");
                // 4.监听器被触发，更新路由
                updateConfigInfo(configInfo);
            }
        });
        // 3.首次初始化，更新一次路由
        updateConfigInfo(configInfo);
    }

    private void updateConfigInfo(String configInfo) {
        // 1.如果是首次更新，routeIdSet可能为空，所以做非空判断
        if (CollUtil.isNotEmpty(routeIdSet)) {
            // 2.若不为空，说明并非第一次更新路由，则删除上一次的旧路由，并删除routeIdSet保存的routeId
            for (String routeId : routeIdSet) {
                routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
            }
            routeIdSet.clear();
        }
        // 2.反序列化得到的路由配置信息，并做非空校验
        List<RouteDefinition> routeList = JSONUtil.toList(configInfo, RouteDefinition.class);
        if (CollUtil.isEmpty(routeList)){
            // 3.若为空，则直接返回
            return;
        }
        for (RouteDefinition routeDefinition : routeList) {
            // 3.将本次更新的路由id保存到routeIdSet
            routeIdSet.add(routeDefinition.getId());
            // 4.更新路由信息
            routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        }
    }
}
