package com.example.auth.service.impl;

import java.util.Set;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.auth.annotation.Logical;
import com.example.auth.config.AuthProperties;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.service.AuthService;
import com.example.auth.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证服务实现类
 * 
 * @author example
 */
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenService tokenService;
    private final AuthProperties authProperties;

    @Override
    public LoginUserVO getLoginUser() {
        // 获取请求携带的Token
        String token = getToken();
        if (!StringUtils.hasText(token)) {
            return null;
        }
        
        // 根据Token获取用户信息
        return tokenService.getLoginUser(token);
    }

    @Override
    public boolean isAuthenticated() {
        return getLoginUser() != null;
    }

    @Override
    public boolean hasPermission(String permission) {
        // 校验权限
        if (!StringUtils.hasText(permission)) {
            return false;
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = getLoginUser();
        if (loginUser == null || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        
        // 管理员拥有所有权限
        if (loginUser.getIsAdmin() != null && loginUser.getIsAdmin() == 1) {
            return true;
        }
        
        // 判断是否拥有该权限
        return loginUser.getPermissions().contains(permission);
    }

    @Override
    public boolean hasPermissions(String[] permissions, String logical) {
        // 校验权限
        if (permissions == null || permissions.length == 0) {
            return false;
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = getLoginUser();
        if (loginUser == null || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        
        // 管理员拥有所有权限
        if (loginUser.getIsAdmin() != null && loginUser.getIsAdmin() == 1) {
            return true;
        }
        
        // 权限集合
        Set<String> permissionSet = loginUser.getPermissions();
        
        // 验证权限
        if (Logical.AND.name().equalsIgnoreCase(logical)) {
            // 必须全部满足
            for (String permission : permissions) {
                if (!permissionSet.contains(permission)) {
                    return false;
                }
            }
            return true;
        } else {
            // 满足一个即可
            for (String permission : permissions) {
                if (permissionSet.contains(permission)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean hasRole(String role) {
        // 校验角色
        if (!StringUtils.hasText(role)) {
            return false;
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = getLoginUser();
        if (loginUser == null || CollectionUtils.isEmpty(loginUser.getRoles())) {
            return false;
        }
        
        // 管理员拥有所有角色权限
        if (loginUser.getIsAdmin() != null && loginUser.getIsAdmin() == 1) {
            return true;
        }
        
        // 判断是否拥有该角色
        return loginUser.getRoles().contains(role);
    }

    @Override
    public boolean hasRoles(String[] roles, String logical) {
        // 校验角色
        if (roles == null || roles.length == 0) {
            return false;
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = getLoginUser();
        if (loginUser == null || CollectionUtils.isEmpty(loginUser.getRoles())) {
            return false;
        }
        
        // 管理员拥有所有角色权限
        if (loginUser.getIsAdmin() != null && loginUser.getIsAdmin() == 1) {
            return true;
        }
        
        // 角色集合
        Set<String> roleSet = loginUser.getRoles();
        
        // 验证角色
        if (Logical.AND.name().equalsIgnoreCase(logical)) {
            // 必须全部满足
            for (String role : roles) {
                if (!roleSet.contains(role)) {
                    return false;
                }
            }
            return true;
        } else {
            // 满足一个即可
            for (String role : roles) {
                if (roleSet.contains(role)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public String getToken() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        
        // 从请求头获取Token
        String token = request.getHeader(authProperties.getToken().getHeader());
        
        // 如果请求头中没有Token，则从请求参数中获取
        if (!StringUtils.hasText(token)) {
            token = request.getParameter(authProperties.getToken().getHeader());
        }
        
        // 判断是否需要去除前缀
        if (StringUtils.hasText(token) && token.startsWith(authProperties.getToken().getPrefix())) {
            token = token.substring(authProperties.getToken().getPrefix().length()).trim();
        }
        
        return token;
    }
    
    /**
     * 获取HttpServletRequest对象
     *
     * @return HttpServletRequest对象
     */
    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }
} 