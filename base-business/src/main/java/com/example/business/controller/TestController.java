package com.example.business.controller;

import com.example.auth.annotation.AuthIgnore;
import com.example.auth.utils.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * 无需鉴权，用于测试系统是否正常运行
 * 
 * @author example
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    /**
     * 测试接口，无需鉴权
     */
    @GetMapping("/hello")
    @AuthIgnore
    public ResponseResult<String> hello() {
        return ResponseResult.success("Hello, Test Service is running!");
    }
} 