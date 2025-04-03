package com.example.core.exception;

/**
 * 业务异常
 * 用于表示业务逻辑错误
 * 
 * @author 作者
 * @date 创建时间
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    private Integer code;
    
    /**
     * 默认构造函数
     */
    public BusinessException() {
        super("业务处理异常");
        this.code = 500;
    }
    
    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }
    
    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误信息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause 异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }
    
    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误信息
     * @param cause 异常
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public Integer getCode() {
        return code;
    }
    
    /**
     * 设置错误码
     *
     * @param code 错误码
     */
    public void setCode(Integer code) {
        this.code = code;
    }
} 