package com.example.auth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.example.auth.config.AuthProperties;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.service.TokenService;
import com.example.auth.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * JWT Token服务实现类
 * 
 * @author example
 */
@Slf4j
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements TokenService {

    private final AuthProperties authProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_USER_INFO = "userInfo";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_UUID = "uuid";
    private static final String CLAIM_KEY_TYPE = "type";
    
    private static final String TOKEN_TYPE_ACCESS = "access";
    private static final String TOKEN_TYPE_REFRESH = "refresh";
    private static final String TOKEN_TYPE_REMEMBER = "remember";

    @Override
    public String createToken(LoginUserVO loginUser) {
        Map<String, Object> claims = new HashMap<>(8);
        claims.put(CLAIM_KEY_USER_ID, loginUser.getUserId());
        claims.put(CLAIM_KEY_USERNAME, loginUser.getUsername());
        claims.put(CLAIM_KEY_UUID, UUID.randomUUID().toString());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_TYPE, TOKEN_TYPE_ACCESS);
        
        try {
            claims.put(CLAIM_KEY_USER_INFO, objectMapper.writeValueAsString(loginUser));
        } catch (JsonProcessingException e) {
            log.error("序列化用户信息失败", e);
        }

        log.info("序列化用户信息authProperties："+authProperties.getToken().getSecret());
        return JwtUtils.createToken(claims, authProperties.getToken().getSecret(), 
                authProperties.getToken().getExpireTime());
    }

    @Override
    public String createRememberMeToken(LoginUserVO loginUser) {
        Map<String, Object> claims = new HashMap<>(8);
        claims.put(CLAIM_KEY_USER_ID, loginUser.getUserId());
        claims.put(CLAIM_KEY_USERNAME, loginUser.getUsername());
        claims.put(CLAIM_KEY_UUID, UUID.randomUUID().toString());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_TYPE, TOKEN_TYPE_REMEMBER);
        
        try {
            claims.put(CLAIM_KEY_USER_INFO, objectMapper.writeValueAsString(loginUser));
        } catch (JsonProcessingException e) {
            log.error("序列化用户信息失败", e);
        }
        
        return JwtUtils.createToken(claims, authProperties.getToken().getSecret(), 
                authProperties.getToken().getRememberMeExpireTime());
    }

    @Override
    public String createRefreshToken(LoginUserVO loginUser) {
        Map<String, Object> claims = new HashMap<>(8);
        claims.put(CLAIM_KEY_USER_ID, loginUser.getUserId());
        claims.put(CLAIM_KEY_USERNAME, loginUser.getUsername());
        claims.put(CLAIM_KEY_UUID, UUID.randomUUID().toString());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_TYPE, TOKEN_TYPE_REFRESH);
        
        try {
            claims.put(CLAIM_KEY_USER_INFO, objectMapper.writeValueAsString(loginUser));
        } catch (JsonProcessingException e) {
            log.error("序列化用户信息失败", e);
        }
        
        return JwtUtils.createToken(claims, authProperties.getToken().getSecret(), 
                authProperties.getToken().getRefreshTokenExpireTime());
    }

    @Override
    public LoginUserVO getLoginUser(String token) {
        if (!verifyToken(token)) {
            return null;
        }
        
        try {
            String userInfoJson = (String) JwtUtils.getValueFromToken(token, 
                    authProperties.getToken().getSecret(), CLAIM_KEY_USER_INFO);
            return objectMapper.readValue(userInfoJson, LoginUserVO.class);
        } catch (Exception e) {
            log.error("获取登录用户信息失败", e);
            return null;
        }
    }

    @Override
    public void refreshToken(String token) {
        // JWT是无状态的，无需刷新，由客户端决定是否使用刷新token获取新的token
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (!verifyToken(refreshToken)) {
            return null;
        }
        
        // 检查是否是刷新token
        Object tokenType = JwtUtils.getValueFromToken(refreshToken, 
                authProperties.getToken().getSecret(), CLAIM_KEY_TYPE);
        if (!TOKEN_TYPE_REFRESH.equals(tokenType)) {
            return null;
        }
        
        // 使用刷新token中的用户信息创建新的访问token
        LoginUserVO loginUser = getLoginUser(refreshToken);
        if (loginUser == null) {
            return null;
        }
        
        return createToken(loginUser);
    }

    @Override
    public boolean verifyToken(String token) {
        return JwtUtils.validateToken(token, authProperties.getToken().getSecret());
    }

    @Override
    public void deleteToken(String token) {
        // JWT是无状态的，不支持服务端主动删除，可以通过黑名单机制实现，此处暂不实现
    }

    @Override
    public void deleteUserTokens(Long userId) {
        // JWT是无状态的，不支持服务端主动删除，可以通过黑名单机制实现，此处暂不实现
    }
} 