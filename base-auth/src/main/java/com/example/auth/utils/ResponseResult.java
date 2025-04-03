package com.example.auth.utils;

import java.io.Serializable;

import lombok.Data;

/**
 * 统一响应结果封装类
 *
 * @author example
 * @date 2023-04-01
 * @param <T> 数据类型
 */
@Data
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功标记
     */
    private boolean success;

    /**
     * 构造方法私有化，禁止直接创建
     */
    private ResponseResult() {
    }

    /**
     * 构造器
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @param success 成功标记
     */
    private ResponseResult(Integer code, String message, T data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    /**
     * 成功返回（无数据）
     *
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(200, "操作成功", null, true);
    }

    /**
     * 成功返回（带消息）
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success(String message) {
        return new ResponseResult<>(200, message, null, true);
    }

    /**
     * 成功返回（带数据）
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(200, "操作成功", data, true);
    }

    /**
     * 成功返回（带数据和消息）
     *
     * @param data    数据
     * @param message 消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success(T data, String message) {
        return new ResponseResult<>(200, message, data, true);
    }

    /**
     * 成功返回（自定义状态码、消息和数据）
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success(Integer code, String message, T data) {
        return new ResponseResult<>(code, message, data, true);
    }

    /**
     * 失败返回（无数据）
     *
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error() {
        return new ResponseResult<>(500, "操作失败", null, false);
    }

    /**
     * 失败返回（带消息）
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult<>(500, message, null, false);
    }

    /**
     * 失败返回（带状态码和消息）
     *
     * @param code    状态码
     * @param message 消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error(Integer code, String message) {
        return new ResponseResult<>(code, message, null, false);
    }

    /**
     * 失败返回（带状态码、消息和数据）
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error(Integer code, String message, T data) {
        return new ResponseResult<>(code, message, data, false);
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return this.success;
    }
} 