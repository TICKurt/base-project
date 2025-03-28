//package com.example.auth.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import com.example.auth.service.TokenService;
//import com.example.auth.service.impl.RedisTokenServiceImpl;
//import com.example.auth.service.impl.JwtTokenServiceImpl;
//import com.example.auth.service.impl.DbTokenServiceImpl;
//
///**
// * 认证配置类
// *
// * @author example
// */
//@Configuration
//public class AuthConfig {
//
//    private final AuthProperties authProperties;
//
//    // 将 RedisTemplate 设为可选参数
//    private RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * 构造函数，AuthProperties 是必需的，RedisTemplate 是可选的
//     */
//    @Autowired
//    public AuthConfig(AuthProperties authProperties) {
//        this.authProperties = authProperties;
//    }
//
//    /**
//     * 可选注入 RedisTemplate
//     */
//    @Autowired(required = false)
//    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    /**
//     * 根据配置创建相应的Token服务实现
//     *
//     * @return Token服务实现
//     */
//    @Bean
//    @ConditionalOnMissingBean(TokenService.class)
//    public TokenService tokenService() {
//        // 根据配置的存储类型创建对应的Token服务实现
//        Integer storeType = authProperties.getToken().getStoreType();
//
//        if (storeType == 1 && redisTemplate != null) {
//            // Redis存储，如果 RedisTemplate 可用
//            return new RedisTokenServiceImpl(authProperties, redisTemplate);
//        } else if (storeType == 2) {
//            // JWT存储
//            return new JwtTokenServiceImpl(authProperties);
//        } else if (storeType == 3) {
//            // 数据库存储
//            return new DbTokenServiceImpl(authProperties);
//        } else {
//            // 默认使用JWT存储（当 Redis 不可用时）
//            return new JwtTokenServiceImpl(authProperties);
//        }
//    }
//}