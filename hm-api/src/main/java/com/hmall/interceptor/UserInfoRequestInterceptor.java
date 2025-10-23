package com.hmall.interceptor;

import cn.hutool.core.util.StrUtil;
import com.hmall.common.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @className: userInfoRequestInterceptor
 * @author: Jonathan Ge
 * @date: 2025/10/22 17:24
 * @Version: 1.0
 * @description:
 */
public class UserInfoRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String userIdStr = String.valueOf(UserContext.getUser());
        if (StrUtil.isNotBlank(userIdStr)){
            template.header("user-info", String.valueOf(userIdStr));
        }
    }
}
