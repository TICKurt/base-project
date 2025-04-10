package com.example.auth.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 统一响应结果包装类
 *
 * @param <T> 数据类型
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param <T> 数据类型
     * @return 成功结果
     */
    public static <T> Result<T> ok() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功返回结果
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 成功结果
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功返回结果
     *
     * @param message 提示信息
     * @param data    数据
     * @param <T>     数据类型
     * @return 成功结果
     */
    public static <T> Result<T> ok(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 失败返回结果
     *
     * @param code    状态码
     * @param message 错误信息
     * @param <T>     数据类型
     * @return 失败结果
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 错误信息
     * @param <T>     数据类型
     * @return 失败结果
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return this.code == 200;
    }
} 