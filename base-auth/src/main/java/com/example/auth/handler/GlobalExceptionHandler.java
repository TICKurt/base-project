package com.example.auth.handler;

import com.example.core.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.auth.exception.NotAuthenticatedException;
import com.example.auth.exception.NotAuthorizedException;
import com.example.auth.utils.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理类
 * 处理认证授权相关异常
 * 
 * @author example
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理未认证异常
     * 
     * @param e 未认证异常
     * @return 统一响应结果
     */
    @ExceptionHandler(NotAuthenticatedException.class)
    public Result<ResponseResult<Void>> handleNotAuthenticatedException(NotAuthenticatedException e) {
        log.warn("未认证异常: {}", e.getMessage());
        return Result.fail(401, e.getMessage());
    }
    
    /**
     * 处理未授权异常
     * 
     * @param e 未授权异常
     * @return 统一响应结果
     */
    @ExceptionHandler(NotAuthorizedException.class)
    public Result<ResponseResult<Void>> handleNotAuthorizedException(NotAuthorizedException e) {
        log.warn("未授权异常: {}", e.getMessage());
        return Result.fail(403, e.getMessage());
    }
} 