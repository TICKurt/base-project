package com.example.auth.annotation;

import java.lang.annotation.*;

/**
 * 需要角色注解
 * 标记在方法或类上，表示需要特定角色才能访问
 * 
 * @author example
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresRole {

    /**
     * 需要的角色标识
     * 多个角色标识，例如：admin,system
     * 
     * @return 角色标识数组
     */
    String[] value() default {};
    
    /**
     * 角色校验逻辑
     * 
     * @return 校验逻辑
     */
    Logical logical() default Logical.AND;
    
    /**
     * 认证失败提示信息
     * 
     * @return 提示信息
     */
    String message() default "角色权限不足，无法访问";
} 