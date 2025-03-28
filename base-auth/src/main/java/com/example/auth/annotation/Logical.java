package com.example.auth.annotation;

/**
 * 逻辑校验类型
 * 用于权限或角色校验时的逻辑判断
 * 
 * @author example
 */
public enum Logical {
    /**
     * 必须同时满足所有条件
     */
    AND,
    
    /**
     * 只需满足其中一个条件
     */
    OR
} 