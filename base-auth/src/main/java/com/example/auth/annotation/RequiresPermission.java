package com.example.auth.annotation;

import java.lang.annotation.*;

/**
 * 需要权限注解
 * 标记在方法或类上，表示需要特定权限才能访问
 * 
 * @author example
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermission {

    /**
     * 需要的权限标识
     * 多个权限标识使用逗号分隔，例如：user:add,user:edit
     * 
     * @return 权限标识数组
     */
    String[] value() default {};
    
    /**
     * 权限校验逻辑
     * 
     * @return 校验逻辑
     */
    Logical logical() default Logical.AND;
    
    /**
     * 认证失败提示信息
     * 
     * @return 提示信息
     */
    String message() default "权限不足，无法访问";
} 