package com.example.core.response;

import java.io.Serializable;

/**
 * 统一响应结果
 * 
 * @author example
 * @param <T> 数据类型
 */
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 状态码
     */
    private Integer code;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * 数据
     */
    private T data;
    
    /**
     * 响应时间戳
     */
    private Long timestamp;
    
    /**
     * 请求成功的响应状态码
     */
    public static final int SUCCESS_CODE = 200;
    
    /**
     * 请求失败的响应状态码
     */
    public static final int FAIL_CODE = 500;
    
    public Result() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    
    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * 返回成功
     * 
     * @param <T> 数据类型
     * @return 统一响应结果
     */
    public static <T> Result<T> ok() {
        return new Result<>(SUCCESS_CODE, "操作成功");
    }
    
    /**
     * 返回成功
     * 
     * @param data 数据
     * @param <T> 数据类型
     * @return 统一响应结果
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(SUCCESS_CODE, "操作成功", data);
    }
    
    /**
     * 返回成功
     * 
     * @param message 消息
     * @param data 数据
     * @param <T> 数据类型
     * @return 统一响应结果
     */
    public static <T> Result<T> ok(String message, T data) {
        return new Result<>(SUCCESS_CODE, message, data);
    }
    
    /**
     * 返回失败
     * 
     * @param <T> 数据类型
     * @return 统一响应结果
     */
    public static <T> Result<T> fail() {
        return new Result<>(FAIL_CODE, "操作失败");
    }
    
    /**
     * 返回失败
     * 
     * @param message 消息
     * @param <T> 数据类型
     * @return 统一响应结果
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(FAIL_CODE, message);
    }
    
    /**
     * 返回失败
     * 
     * @param code 状态码
     * @param message 消息
     * @param <T> 数据类型
     * @return 统一响应结果
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message);
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public Long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
} 