package com.example.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.auth.web.AuthInterceptor;

import lombok.RequiredArgsConstructor;

/**
 * 认证模块的 Web MVC 配置类
 * 用于注册认证拦截器和跨域配置
 * 
 * @author example
 */
@Configuration
@RequiredArgsConstructor
public class AuthWebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AuthProperties authProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 允许来自前端开发服务器的请求（Vue默认端口3000）
                .allowedOriginPatterns("http://localhost:3000", "http://127.0.0.1:3000")
                // 允许的HTTP方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许的请求头
                .allowedHeaders("*")
                // 是否允许发送Cookie
                .allowCredentials(true)
                // 预检请求的有效期，单位为秒
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册认证拦截器，拦截所有请求
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(authProperties.getUrlWhitelist());
    }
} 