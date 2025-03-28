package com.example.auth.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.example.auth.annotation.AuthIgnore;
import com.example.auth.annotation.Logical;
import com.example.auth.annotation.RequiresLogin;
import com.example.auth.annotation.RequiresPermission;
import com.example.auth.annotation.RequiresRole;
import com.example.auth.config.AuthProperties;
import com.example.auth.exception.NotAuthenticatedException;
import com.example.auth.exception.NotAuthorizedException;
import com.example.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 认证授权切面
 * 实现对注解的AOP拦截和处理
 * 
 * @author example
 */
@Aspect
@RequiredArgsConstructor
@Slf4j
public class AuthAspect {

    private final AuthService authService;
    private final AuthProperties authProperties;

    /**
     * 定义切点
     * 拦截所有使用RequiresLogin注解的方法
     */
    @Pointcut("@annotation(com.example.auth.annotation.RequiresLogin) || @within(com.example.auth.annotation.RequiresLogin)")
    public void loginPointCut() {}

    /**
     * 定义切点
     * 拦截所有使用RequiresPermission注解的方法
     */
    @Pointcut("@annotation(com.example.auth.annotation.RequiresPermission) || @within(com.example.auth.annotation.RequiresPermission)")
    public void permissionPointCut() {}

    /**
     * 定义切点
     * 拦截所有使用RequiresRole注解的方法
     */
    @Pointcut("@annotation(com.example.auth.annotation.RequiresRole) || @within(com.example.auth.annotation.RequiresRole)")
    public void rolePointCut() {}

    /**
     * 环绕通知
     * 拦截所有需要登录的方法
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 执行异常
     */
    @Around("loginPointCut()")
    public Object loginAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 如果认证开关未开启，直接放行
        if (!authProperties.getEnabled()) {
            return joinPoint.proceed();
        }
        
        // 如果设置了忽略认证，直接放行
        if (checkIgnore(joinPoint)) {
            return joinPoint.proceed();
        }
        
        // 获取方法上的RequiresLogin注解
        RequiresLogin methodAnnotation = getMethodAnnotation(joinPoint, RequiresLogin.class);
        // 获取类上的RequiresLogin注解
        RequiresLogin classAnnotation = getClassAnnotation(joinPoint, RequiresLogin.class);
        
        // 判断是否需要登录认证
        RequiresLogin annotation = methodAnnotation != null ? methodAnnotation : classAnnotation;
        if (annotation != null && annotation.value()) {
            if (!authService.isAuthenticated()) {
                throw new NotAuthenticatedException(annotation.message());
            }
        }
        
        return joinPoint.proceed();
    }

    /**
     * 环绕通知
     * 拦截所有需要权限的方法
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 执行异常
     */
    @Around("permissionPointCut()")
    public Object permissionAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 如果认证开关未开启，直接放行
        if (!authProperties.getEnabled()) {
            return joinPoint.proceed();
        }
        
        // 如果设置了忽略认证，直接放行
        if (checkIgnore(joinPoint)) {
            return joinPoint.proceed();
        }
        
        // 获取方法上的RequiresPermission注解
        RequiresPermission methodAnnotation = getMethodAnnotation(joinPoint, RequiresPermission.class);
        // 获取类上的RequiresPermission注解
        RequiresPermission classAnnotation = getClassAnnotation(joinPoint, RequiresPermission.class);
        
        // 判断是否需要权限认证
        RequiresPermission annotation = methodAnnotation != null ? methodAnnotation : classAnnotation;
        if (annotation != null && annotation.value().length > 0) {
            if (!authService.isAuthenticated()) {
                throw new NotAuthenticatedException("请先登录后再进行操作");
            }
            
            if (!authService.hasPermissions(annotation.value(), annotation.logical().name())) {
                throw new NotAuthorizedException(annotation.message());
            }
        }
        
        return joinPoint.proceed();
    }

    /**
     * 环绕通知
     * 拦截所有需要角色的方法
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 执行异常
     */
    @Around("rolePointCut()")
    public Object roleAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 如果认证开关未开启，直接放行
        if (!authProperties.getEnabled()) {
            return joinPoint.proceed();
        }
        
        // 如果设置了忽略认证，直接放行
        if (checkIgnore(joinPoint)) {
            return joinPoint.proceed();
        }
        
        // 获取方法上的RequiresRole注解
        RequiresRole methodAnnotation = getMethodAnnotation(joinPoint, RequiresRole.class);
        // 获取类上的RequiresRole注解
        RequiresRole classAnnotation = getClassAnnotation(joinPoint, RequiresRole.class);
        
        // 判断是否需要角色认证
        RequiresRole annotation = methodAnnotation != null ? methodAnnotation : classAnnotation;
        if (annotation != null && annotation.value().length > 0) {
            if (!authService.isAuthenticated()) {
                throw new NotAuthenticatedException("请先登录后再进行操作");
            }
            
            if (!authService.hasRoles(annotation.value(), annotation.logical().name())) {
                throw new NotAuthorizedException(annotation.message());
            }
        }
        
        return joinPoint.proceed();
    }

    /**
     * 检查是否设置了忽略认证
     *
     * @param joinPoint 连接点
     * @return 是否忽略认证
     */
    private boolean checkIgnore(ProceedingJoinPoint joinPoint) {
        // 获取方法上的AuthIgnore注解
        AuthIgnore methodAnnotation = getMethodAnnotation(joinPoint, AuthIgnore.class);
        if (methodAnnotation != null && methodAnnotation.value()) {
            return true;
        }
        
        // 获取类上的AuthIgnore注解
        AuthIgnore classAnnotation = getClassAnnotation(joinPoint, AuthIgnore.class);
        return classAnnotation != null && classAnnotation.value();
    }

    /**
     * 获取方法上的注解
     *
     * @param joinPoint 连接点
     * @param annotationClass 注解类
     * @param <T> 注解类型
     * @return 注解对象
     */
    private <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return AnnotationUtils.findAnnotation(method, annotationClass);
    }

    /**
     * 获取类上的注解
     *
     * @param joinPoint 连接点
     * @param annotationClass 注解类
     * @param <T> 注解类型
     * @return 注解对象
     */
    private <T extends Annotation> T getClassAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        return AnnotationUtils.findAnnotation(targetClass, annotationClass);
    }
} 