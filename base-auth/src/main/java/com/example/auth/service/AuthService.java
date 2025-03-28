package com.example.auth.service;

import com.example.auth.domain.vo.LoginUserVO;

/**
 * 认证服务接口
 * 定义认证和授权相关的方法
 * 
 * @author example
 */
public interface AuthService {

    /**
     * 获取当前登录用户信息
     *
     * @return 登录用户信息
     */
    LoginUserVO getLoginUser();

    /**
     * 检查用户是否已登录
     *
     * @return 是否已登录
     */
    boolean isAuthenticated();

    /**
     * 检查用户是否具有指定权限
     *
     * @param permission 权限标识
     * @return 是否具有权限
     */
    boolean hasPermission(String permission);

    /**
     * 检查用户是否具有指定权限（支持多个权限）
     *
     * @param permissions 权限标识数组
     * @param logical 逻辑运算符（AND 或 OR）
     * @return 是否具有权限
     */
    boolean hasPermissions(String[] permissions, String logical);

    /**
     * 检查用户是否具有指定角色
     *
     * @param role 角色标识
     * @return 是否具有角色
     */
    boolean hasRole(String role);

    /**
     * 检查用户是否具有指定角色（支持多个角色）
     *
     * @param roles 角色标识数组
     * @param logical 逻辑运算符（AND 或 OR）
     * @return 是否具有角色
     */
    boolean hasRoles(String[] roles, String logical);

    /**
     * 获取用户Token
     *
     * @return token
     */
    String getToken();
} 