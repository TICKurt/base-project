package com.example.auth.exception;

/**
 * 未认证异常
 * 当用户未登录时抛出
 * 
 * @author example
 */
public class NotAuthenticatedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造方法
     */
    public NotAuthenticatedException() {
        super("未登录或登录已过期，请重新登录");
    }

    /**
     * 构造方法
     *
     * @param message 异常信息
     */
    public NotAuthenticatedException(String message) {
        super(message);
    }
} 