package com.example.auth.exception;

/**
 * 未授权异常
 * 当用户没有权限访问某个资源时抛出
 * 
 * @author example
 */
public class NotAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造方法
     */
    public NotAuthorizedException() {
        super("权限不足，无法访问");
    }

    /**
     * 构造方法
     *
     * @param message 异常信息
     */
    public NotAuthorizedException(String message) {
        super(message);
    }
} 