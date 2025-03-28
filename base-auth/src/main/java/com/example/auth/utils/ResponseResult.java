package com.example.auth.utils;

import java.io.Serializable;

import lombok.Data;

/**
 * 统一响应结果类
 * 
 * @author example
 * @param <T> 数据类型
 */
@Data
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功
     *
     * @param <T> 数据类型
     * @return 成功响应结果
     */
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T> 数据类型
     * @return 成功响应结果
     */
    public static <T> ResponseResult<T> success(T data) {
        return success(200, "操作成功", data);
    }

    /**
     * 成功
     *
     * @param code 状态码
     * @param message 消息
     * @param data 数据
     * @param <T> 数据类型
     * @return 成功响应结果
     */
    public static <T> ResponseResult<T> success(int code, String message, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @param <T> 数据类型
     * @return 失败响应结果
     */
    public static <T> ResponseResult<T> fail() {
        return fail(500, "操作失败");
    }

    /**
     * 失败
     *
     * @param message 消息
     * @param <T> 数据类型
     * @return 失败响应结果
     */
    public static <T> ResponseResult<T> fail(String message) {
        return fail(500, message);
    }

    /**
     * 失败
     *
     * @param code 状态码
     * @param message 消息
     * @param <T> 数据类型
     * @return 失败响应结果
     */
    public static <T> ResponseResult<T> fail(int code, String message) {
        return fail(code, message, null);
    }

    /**
     * 失败
     *
     * @param code 状态码
     * @param message 消息
     * @param data 数据
     * @param <T> 数据类型
     * @return 失败响应结果
     */
    public static <T> ResponseResult<T> fail(int code, String message, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
} 