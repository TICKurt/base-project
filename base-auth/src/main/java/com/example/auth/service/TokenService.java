package com.example.auth.service;

import com.example.auth.domain.vo.LoginUserVO;

/**
 * Token服务接口
 * 
 * @author example
 */
public interface TokenService {

    /**
     * 创建token
     *
     * @param loginUser 登录用户信息
     * @return token
     */
    String createToken(LoginUserVO loginUser);
    
    /**
     * 创建记住我token
     *
     * @param loginUser 登录用户信息
     * @return token
     */
    String createRememberMeToken(LoginUserVO loginUser);
    
    /**
     * 创建刷新token
     *
     * @param loginUser 登录用户信息
     * @return token
     */
    String createRefreshToken(LoginUserVO loginUser);

    /**
     * 获取登录用户信息
     *
     * @param token token
     * @return 登录用户信息
     */
    LoginUserVO getLoginUser(String token);

    /**
     * 刷新token有效期
     *
     * @param token token
     */
    void refreshToken(String token);
    
    /**
     * 使用刷新token获取新的访问token
     *
     * @param refreshToken 刷新token
     * @return 新的访问token
     */
    String refreshAccessToken(String refreshToken);

    /**
     * 验证token有效性
     *
     * @param token token
     * @return 是否有效
     */
    boolean verifyToken(String token);

    /**
     * 删除token
     *
     * @param token token
     */
    void deleteToken(String token);
    
    /**
     * 删除用户所有token
     *
     * @param userId 用户ID
     */
    void deleteUserTokens(Long userId);
} 