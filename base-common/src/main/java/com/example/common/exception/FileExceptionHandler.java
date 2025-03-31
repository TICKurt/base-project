package com.example.common.exception;

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
    public ResponseEntity<Map<String, Object>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("文件上传大小超出限制", e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", "上传文件大小超过限制");
        
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(result);
    }

    /**
     * 处理文件上传异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Map<String, Object>> handleMultipartException(MultipartException e) {
        log.error("文件上传失败", e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", "文件上传失败");
        
        return ResponseEntity.badRequest().body(result);
    }

    /**
     * 处理参数异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数错误", e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", e.getMessage());
        
        return ResponseEntity.badRequest().body(result);
    }

    /**
     * 处理通用异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        log.error("系统异常", e);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", "系统异常，请稍后重试");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
} 