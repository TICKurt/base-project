package com.example.auth.annotation;

import java.lang.annotation.*;

/**
 * 需要登录注解
 * 标记在方法或类上，表示需要登录才能访问
 * 
 * @author example
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresLogin {

    /**
     * 是否启用此注解
     * 
     * @return 是否启用
     */
    boolean value() default true;
    
    /**
     * 认证失败提示信息
     * 
     * @return 提示信息
     */
    String message() default "请先登录后再进行操作";
} 