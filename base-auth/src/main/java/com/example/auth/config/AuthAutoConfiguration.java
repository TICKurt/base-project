package com.example.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import javax.annotation.PostConstruct;
import org.springframework.core.env.Environment;

import com.example.auth.aspect.AuthAspect;
import com.example.auth.handler.GlobalExceptionHandler;
import com.example.auth.service.AuthService;
import com.example.auth.service.TokenService;
import com.example.auth.service.impl.AuthServiceImpl;
import com.example.auth.service.impl.DbTokenServiceImpl;
import com.example.auth.service.impl.JwtTokenServiceImpl;
import com.example.auth.service.impl.RedisTokenServiceImpl;
import com.example.auth.web.AuthInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证授权自动配置类
 *
 * @author example
 */
@Configuration
@Import({AuthProperties.class, GlobalExceptionHandler.class})
@ConditionalOnProperty(prefix = "auth", name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class AuthAutoConfiguration {
    @Autowired
    private AuthProperties authProperties;
    
    @Autowired
    private Environment environment;
    
    // 添加RedisTemplate字段，设为可选
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public AuthAutoConfiguration(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }
    
    // 添加可选的Autowired方法
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    /**
     * 初始化后处理，确保配置正确加载
     */
    @PostConstruct
    public void init() {
        // 检查token密钥配置
        if (authProperties.getToken().getSecret() == null || authProperties.getToken().getSecret().isEmpty()) {
            // 尝试从环境变量中读取
            String secret = environment.getProperty("auth.token.secret");
            if (secret != null && !secret.isEmpty()) {
                log.info("从环境变量加载JWT密钥配置");
                authProperties.getToken().setSecret(secret);
            } else {
                // 设置默认安全密钥
//                String defaultSecret = "base-project-default-jwt-secret-key-must-be-at-least-32-chars";
                log.warn("未找到JWT密钥配置，建议在生产环境中配置密钥auth:token:secret");
//                authProperties.getToken().setSecret(defaultSecret);
            }
        }
        
        // 记录当前使用的配置
        log.info("认证模块配置：enabled={}, tokenStoreType={}, secretConfigured={}", 
                authProperties.getEnabled(),
                authProperties.getToken().getStoreType(),
                authProperties.getToken().getSecret() != null);
    }

    /**
     * 根据配置创建相应的Token服务实现
     *
     * @return Token服务实现
     */
    @Bean
    @ConditionalOnMissingBean(TokenService.class)
    public TokenService tokenService() {
        // 根据配置的存储类型创建对应的Token服务实现
        Integer storeType = authProperties.getToken().getStoreType();

        if (storeType == 1 && redisTemplate != null) {
            // Redis存储，如果 RedisTemplate 可用
            return new RedisTokenServiceImpl(authProperties, redisTemplate);
        } else if (storeType == 2) {
            // JWT存储
            return new JwtTokenServiceImpl(authProperties);
        } else if (storeType == 3) {
            // 数据库存储
            return new DbTokenServiceImpl(authProperties);
        } else {
            // 如果默认是Redis但Redis不可用，则返回JWT实现
            return new JwtTokenServiceImpl(authProperties);
        }
    }

    /**
     * 认证服务
     *
     * @param tokenService Token服务
     * @return 认证服务实现
     */
    @Bean
    @ConditionalOnMissingBean(AuthService.class)
    public AuthService authService(TokenService tokenService) {
        return new AuthServiceImpl(tokenService, authProperties);
    }

    /**
     * 认证授权切面
     *
     * @param authService 认证服务
     * @return 认证授权切面
     */
    @Bean
    public AuthAspect authAspect(AuthService authService) {
        return new AuthAspect(authService, authProperties);
    }

    /**
     * 认证拦截器
     *
     * @param authService 认证服务
     * @return 认证拦截器
     */
    @Bean
    public AuthInterceptor authInterceptor(AuthService authService) {
        return new AuthInterceptor(authService, authProperties);
    }

    /**
     * Web配置
     *
     * @param authInterceptor 认证拦截器
     * @return Web配置
     */
    @Bean
    public WebMvcConfig webMvcConfig(AuthInterceptor authInterceptor) {
        return new WebMvcConfig(authInterceptor, authProperties);
    }
}