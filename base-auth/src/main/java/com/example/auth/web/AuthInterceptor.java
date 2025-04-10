package com.example.auth.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.auth.annotation.AuthIgnore;
import com.example.auth.annotation.RequiresLogin;
import com.example.auth.annotation.RequiresPermission;
import com.example.auth.annotation.RequiresRole;
import com.example.auth.config.AuthProperties;
import com.example.auth.service.AuthService;
import com.example.auth.response.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证拦截器
 * 拦截和验证HTTP请求
 * 
 * @author example
 */
@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private final AuthProperties authProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果认证开关未开启，直接放行
        if (!authProperties.getEnabled()) {
            return true;
        }
        
        // 如果不是处理方法的调用，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        
        // 检查是否设置了忽略认证
        if (checkIgnore(handlerMethod)) {
            return true;
        }
        
        // 获取请求的URL路径
        String requestUrl = request.getRequestURI();
        
        // 检查是否在白名单中
        if (isInWhiteList(requestUrl)) {
            return true;
        }
        
        // 检查登录要求
        if (requiresLogin(handlerMethod) && !authService.isAuthenticated()) {
            handleNotAuthenticated(response, "未登录或登录已过期，请重新登录");
            return false;
        }
        
        // 检查权限要求
        RequiresPermission permissionAnnotation = getPermissionAnnotation(handlerMethod);
        if (permissionAnnotation != null && permissionAnnotation.value().length > 0) {
            if (!authService.isAuthenticated()) {
                handleNotAuthenticated(response, "未登录或登录已过期，请重新登录");
                return false;
            }
            
            if (!authService.hasPermissions(permissionAnnotation.value(), permissionAnnotation.logical().name())) {
                handleNotAuthorized(response, permissionAnnotation.message());
                return false;
            }
        }
        
        // 检查角色要求
        RequiresRole roleAnnotation = getRoleAnnotation(handlerMethod);
        if (roleAnnotation != null && roleAnnotation.value().length > 0) {
            if (!authService.isAuthenticated()) {
                handleNotAuthenticated(response, "未登录或登录已过期，请重新登录");
                return false;
            }
            
            if (!authService.hasRoles(roleAnnotation.value(), roleAnnotation.logical().name())) {
                handleNotAuthorized(response, roleAnnotation.message());
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 检查是否设置了忽略认证
     *
     * @param handlerMethod 处理方法
     * @return 是否忽略认证
     */
    private boolean checkIgnore(HandlerMethod handlerMethod) {
        // 获取方法上的AuthIgnore注解
        AuthIgnore methodAnnotation = handlerMethod.getMethodAnnotation(AuthIgnore.class);
        if (methodAnnotation != null && methodAnnotation.value()) {
            return true;
        }
        
        // 获取类上的AuthIgnore注解
        AuthIgnore classAnnotation = handlerMethod.getBeanType().getAnnotation(AuthIgnore.class);
        return classAnnotation != null && classAnnotation.value();
    }
    
    /**
     * 检查是否需要登录
     *
     * @param handlerMethod 处理方法
     * @return 是否需要登录
     */
    private boolean requiresLogin(HandlerMethod handlerMethod) {
        // 获取方法上的RequiresLogin注解
        RequiresLogin methodAnnotation = handlerMethod.getMethodAnnotation(RequiresLogin.class);
        if (methodAnnotation != null && methodAnnotation.value()) {
            return true;
        }
        
        // 获取类上的RequiresLogin注解
        RequiresLogin classAnnotation = handlerMethod.getBeanType().getAnnotation(RequiresLogin.class);
        return classAnnotation != null && classAnnotation.value();
    }
    
    /**
     * 获取权限注解
     *
     * @param handlerMethod 处理方法
     * @return 权限注解
     */
    private RequiresPermission getPermissionAnnotation(HandlerMethod handlerMethod) {
        // 获取方法上的RequiresPermission注解
        RequiresPermission methodAnnotation = handlerMethod.getMethodAnnotation(RequiresPermission.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        
        // 获取类上的RequiresPermission注解
        return handlerMethod.getBeanType().getAnnotation(RequiresPermission.class);
    }
    
    /**
     * 获取角色注解
     *
     * @param handlerMethod 处理方法
     * @return 角色注解
     */
    private RequiresRole getRoleAnnotation(HandlerMethod handlerMethod) {
        // 获取方法上的RequiresRole注解
        RequiresRole methodAnnotation = handlerMethod.getMethodAnnotation(RequiresRole.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        
        // 获取类上的RequiresRole注解
        return handlerMethod.getBeanType().getAnnotation(RequiresRole.class);
    }
    
    /**
     * 检查是否在白名单中
     *
     * @param requestUrl 请求URL
     * @return 是否在白名单中
     */
    private boolean isInWhiteList(String requestUrl) {
        for (String url : authProperties.getUrlWhitelist()) {
            if (requestUrl.startsWith(url)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 处理未认证异常
     *
     * @param response 响应对象
     * @param message 消息
     * @throws IOException IO异常
     */
    private void handleNotAuthenticated(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        Result<Void> result = Result.error(HttpStatus.UNAUTHORIZED.value(), message);
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(result));
        writer.flush();
    }
    
    /**
     * 处理未授权异常
     *
     * @param response 响应对象
     * @param message 消息
     * @throws IOException IO异常
     */
    private void handleNotAuthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        Result<Void> result = Result.error(HttpStatus.FORBIDDEN.value(), message);
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(result));
        writer.flush();
    }
} 