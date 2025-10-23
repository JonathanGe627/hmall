package com.hmall.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import com.hmall.common.exception.UnauthorizedException;
import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @className: UserInfoFilter
 * @author: Jonathan Ge
 * @date: 2025/10/22 15:46
 * @Version: 1.0
 * @description:
 */
@Component
@RequiredArgsConstructor
public class UserInfoFilter implements GlobalFilter {

    private final JwtTool jwtTool;

    private final AuthProperties authProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求
        ServerHttpRequest request = exchange.getRequest();
        // 2.判断是否需要做拦截
        if (isExcludePath(request.getPath().toString())){
            // 如果是不需要拦截的路径，则直接放行
            return chain.filter(exchange);
        }
        // 2.解析token
        String token = null;
        List<String> headers = request.getHeaders().get("authorization");
        if (CollUtil.isNotEmpty(headers)){
            token = headers.get(0);
        }
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);
        } catch (UnauthorizedException e) {
            // 如果解析错误，抛出异常，在这里捕获并返回异常码401未授权
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // 3.封装传递用户信息
        String userInfo = String.valueOf(userId);
        ServerWebExchange serverWebExchange = exchange
                .mutate().request(builder -> builder.header("user-info", userInfo)).build();
        // 4.放行
        return chain.filter(serverWebExchange);
    }

    private boolean isExcludePath(String string) {
        for (String excludePath : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(excludePath, string)){
                return true;
            }
        }
        return false;
    }
}
