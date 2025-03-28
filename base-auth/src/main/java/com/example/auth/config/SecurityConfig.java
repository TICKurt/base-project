package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Spring Security 配置类
 * 禁用 Spring Security 默认的 HTTP Basic 认证
 * 
 * @author example
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置 HTTP 安全规则
     * 禁用默认的 HTTP Basic 认证，允许所有请求通过
     * 实际的认证和授权由我们自定义的拦截器和切面处理
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 禁用 CSRF 保护
            .csrf().disable()
            // 禁用 HTTP Basic 和表单登录
            .httpBasic().disable()
            .formLogin().disable()
            // 禁用登出页面
            .logout().disable()
            // 设置所有请求都允许匿名访问
            .authorizeRequests()
            .anyRequest().permitAll()
            // 不创建会话
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
} 