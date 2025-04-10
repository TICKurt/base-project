package com.example.business.controller;

import com.example.auth.annotation.AuthIgnore;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.service.TokenService;
import com.example.auth.response.Result;
import com.example.business.domain.dto.LoginDTO;
import com.example.business.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 提供登录、注销等认证相关接口
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    /**
     * 登录接口
     *
     * @param loginDTO 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    @AuthIgnore
    public Result<Map<String, Object>> login(@Validated @RequestBody LoginDTO loginDTO) {
        // 用户认证
        LoginUserVO loginUser = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        
        // 生成token
        String token = tokenService.createToken(loginUser);
        
        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", loginUser);
        
        return Result.ok("登录成功", result);
    }
} 