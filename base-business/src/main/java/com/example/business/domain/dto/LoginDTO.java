package com.example.business.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录参数DTO
 */
@Data
public class LoginDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 记住我
     */
    private Boolean rememberMe = false;
} 