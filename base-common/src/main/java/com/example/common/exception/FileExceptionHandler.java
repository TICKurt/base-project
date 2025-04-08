package com.example.common.exception;

import com.example.core.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传异常处理
 * 
 * @author example
 */
@RestControllerAdvice
@Slf4j
public class FileExceptionHandler {

    /**
     * 处理文件上传大小超出限制异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<Map<String, Object>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return Result.fail("上传文件大小超过限制");
    }

    /**
     * 处理文件上传异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(MultipartException.class)
    public Result<Map<String, Object>> handleMultipartException(MultipartException e) {
        log.error("文件上传失败", e);
        return Result.fail("文件上传失败" + e.getMessage());
    }

    /**
     * 处理参数异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数错误", e);
        return Result.fail("参数错误" + e.getMessage());
    }

    /**
     * 处理通用异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    public Result<Map<String, Object>> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.fail("系统异常" + e.getMessage());
    }
} 