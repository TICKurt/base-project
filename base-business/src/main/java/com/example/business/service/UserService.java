package com.example.business.service;

import com.example.auth.domain.vo.LoginUserVO;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录用户信息
     */
    LoginUserVO login(String username, String password);
} 