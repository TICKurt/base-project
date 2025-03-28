package com.example.auth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.example.auth.config.AuthProperties;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Redis Token服务实现类
 * 
 * @author example
 */
@Slf4j
@RequiredArgsConstructor
public class RedisTokenServiceImpl implements TokenService {

    private final AuthProperties authProperties;
    private final RedisTemplate<String, Object> redisTemplate;
    
    // Redis key前缀
    private static final String ACCESS_TOKEN_KEY_PREFIX = "auth:token:access:";
    private static final String REFRESH_TOKEN_KEY_PREFIX = "auth:token:refresh:";
    private static final String REMEMBER_TOKEN_KEY_PREFIX = "auth:token:remember:";
    private static final String USER_TOKEN_KEY_PREFIX = "auth:user:token:";

    @Override
    public String createToken(LoginUserVO loginUser) {
        log.info("序列化用户信息authProperties："+authProperties.getToken().getSecret());
        // 生成token
        String token = UUID.randomUUID().toString();
        
        // 构建缓存信息
        String tokenKey = ACCESS_TOKEN_KEY_PREFIX + token;
        
        // 存储到Redis
        redisTemplate.opsForValue().set(tokenKey, loginUser, 
                authProperties.getToken().getExpireTime(), TimeUnit.MINUTES);
        
        // 记录用户token，用于后续删除用户所有token
        String userTokenKey = USER_TOKEN_KEY_PREFIX + loginUser.getUserId();
        redisTemplate.opsForSet().add(userTokenKey, tokenKey);
        redisTemplate.expireAt(userTokenKey, getExpireDateFromNow(authProperties.getToken().getExpireTime()));
        
        // 设置token到loginUser
        loginUser.setToken(token);
        loginUser.setExpireTime(getExpireDateFromNow(authProperties.getToken().getExpireTime()));
        
        return token;
    }

    @Override
    public String createRememberMeToken(LoginUserVO loginUser) {
        // 生成token
        String token = UUID.randomUUID().toString();
        
        // 构建缓存信息
        String tokenKey = REMEMBER_TOKEN_KEY_PREFIX + token;
        
        // 存储到Redis
        redisTemplate.opsForValue().set(tokenKey, loginUser, 
                authProperties.getToken().getRememberMeExpireTime(), TimeUnit.MINUTES);
        
        // 记录用户token，用于后续删除用户所有token
        String userTokenKey = USER_TOKEN_KEY_PREFIX + loginUser.getUserId();
        redisTemplate.opsForSet().add(userTokenKey, tokenKey);
        redisTemplate.expireAt(userTokenKey, getExpireDateFromNow(authProperties.getToken().getRememberMeExpireTime()));
        
        return token;
    }

    @Override
    public String createRefreshToken(LoginUserVO loginUser) {
        // 生成token
        String token = UUID.randomUUID().toString();
        
        // 构建缓存信息
        String tokenKey = REFRESH_TOKEN_KEY_PREFIX + token;
        
        // 存储到Redis
        redisTemplate.opsForValue().set(tokenKey, loginUser, 
                authProperties.getToken().getRefreshTokenExpireTime(), TimeUnit.MINUTES);
        
        // 记录用户token，用于后续删除用户所有token
        String userTokenKey = USER_TOKEN_KEY_PREFIX + loginUser.getUserId();
        redisTemplate.opsForSet().add(userTokenKey, tokenKey);
        redisTemplate.expireAt(userTokenKey, getExpireDateFromNow(authProperties.getToken().getRefreshTokenExpireTime()));
        
        // 设置刷新token到loginUser
        loginUser.setRefreshToken(token);
        
        return token;
    }

    @Override
    public LoginUserVO getLoginUser(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        
        String tokenKey = ACCESS_TOKEN_KEY_PREFIX + token;
        Object cacheObject = redisTemplate.opsForValue().get(tokenKey);
        return cacheObject instanceof LoginUserVO ? (LoginUserVO) cacheObject : null;
    }

    @Override
    public void refreshToken(String token) {
        if (!StringUtils.hasText(token)) {
            return;
        }
        
        String tokenKey = ACCESS_TOKEN_KEY_PREFIX + token;
        Object cacheObject = redisTemplate.opsForValue().get(tokenKey);
        if (cacheObject instanceof LoginUserVO) {
            // 更新过期时间
            LoginUserVO loginUser = (LoginUserVO) cacheObject;
            Date expireTime = getExpireDateFromNow(authProperties.getToken().getExpireTime());
            loginUser.setExpireTime(expireTime);
            
            // 更新Redis中的信息
            redisTemplate.opsForValue().set(tokenKey, loginUser, 
                    authProperties.getToken().getExpireTime(), TimeUnit.MINUTES);
            
            // 更新用户token的过期时间
            String userTokenKey = USER_TOKEN_KEY_PREFIX + loginUser.getUserId();
            redisTemplate.expireAt(userTokenKey, expireTime);
        }
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            return null;
        }
        
        String tokenKey = REFRESH_TOKEN_KEY_PREFIX + refreshToken;
        Object cacheObject = redisTemplate.opsForValue().get(tokenKey);
        if (cacheObject instanceof LoginUserVO) {
            // 使用刷新token中的用户信息创建新的访问token
            LoginUserVO loginUser = (LoginUserVO) cacheObject;
            return createToken(loginUser);
        }
        
        return null;
    }

    @Override
    public boolean verifyToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        
        String tokenKey = ACCESS_TOKEN_KEY_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenKey));
    }

    @Override
    public void deleteToken(String token) {
        if (!StringUtils.hasText(token)) {
            return;
        }
        
        // 获取用户信息
        String tokenKey = ACCESS_TOKEN_KEY_PREFIX + token;
        LoginUserVO loginUser = (LoginUserVO) redisTemplate.opsForValue().get(tokenKey);
        if (loginUser != null) {
            // 从用户token集合中移除
            String userTokenKey = USER_TOKEN_KEY_PREFIX + loginUser.getUserId();
            redisTemplate.opsForSet().remove(userTokenKey, tokenKey);
        }
        
        // 删除token
        redisTemplate.delete(tokenKey);
    }

    @Override
    public void deleteUserTokens(Long userId) {
        if (userId == null) {
            return;
        }
        
        // 获取用户所有token
        String userTokenKey = USER_TOKEN_KEY_PREFIX + userId;
        
        // 删除所有token
        for (Object tokenKey : redisTemplate.opsForSet().members(userTokenKey)) {
            redisTemplate.delete(tokenKey.toString());
        }
        
        // 删除用户token集合
        redisTemplate.delete(userTokenKey);
    }
    
    /**
     * 获取指定分钟数之后的日期
     *
     * @param minutes 分钟数
     * @return 日期
     */
    private Date getExpireDateFromNow(int minutes) {
        return new Date(System.currentTimeMillis() + minutes * 60 * 1000L);
    }
}