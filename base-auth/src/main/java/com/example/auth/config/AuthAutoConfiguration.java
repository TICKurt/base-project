package com.example.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

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

/**
 * 认证授权自动配置类
 *
 * @author example
 */
@Configuration
@Import({AuthProperties.class, GlobalExceptionHandler.class})
@ConditionalOnProperty(prefix = "auth", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AuthAutoConfiguration {

    private final AuthProperties authProperties;
    
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