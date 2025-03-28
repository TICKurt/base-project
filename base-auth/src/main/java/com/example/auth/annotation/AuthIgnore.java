package com.example.auth.annotation;

import java.lang.annotation.*;

/**
 * 忽略认证注解
 * 标记在方法或类上，表示忽略认证和授权检查
 * 
 * @author example
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {

    /**
     * 是否启用此注解
     * 
     * @return 是否启用
     */
    boolean value() default true;
} 