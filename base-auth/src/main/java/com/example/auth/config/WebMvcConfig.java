package com.example.auth.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.auth.web.AuthInterceptor;

import lombok.RequiredArgsConstructor;

/**
 * Web MVC配置类
 * 用于注册认证拦截器
 * 
 * @author example
 */
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AuthProperties authProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册认证拦截器，拦截所有请求
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(authProperties.getUrlWhitelist());
    }
} 