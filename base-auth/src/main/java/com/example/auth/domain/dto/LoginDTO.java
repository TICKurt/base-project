package com.example.auth.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 登录参数DTO
 * 用于处理用户登录请求
 * 
 * @author example
 */
@Data
public class LoginDTO {

    /**
     * 用户名
     * 必填字段
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 30, message = "用户名长度必须在2-30个字符之间")
    private String username;

    /**
     * 密码
     * 必填字段
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    private String password;

    /**
     * 验证码
     * 根据系统配置可能为必填字段
     */
    private String captcha;

    /**
     * 验证码Key
     * 用于标识服务端缓存的验证码信息
     */
    private String captchaKey;

    /**
     * 记住我
     * 用于延长Token有效期
     */
    private Boolean rememberMe = false;

    /**
     * 登录类型
     * 1：用户名密码登录（默认）
     * 2：手机号验证码登录
     * 3：邮箱验证码登录
     * 4：第三方登录
     */
    private Integer loginType = 1;

    /**
     * 登录平台
     * 1：PC端登录（默认）
     * 2：移动端登录
     * 3：小程序登录
     * 4：其他终端登录
     */
    private Integer platform = 1;
    
    /**
     * 扩展信息
     * 用于存储不同登录方式下的额外信息
     * 例如：第三方登录的code、state
     */
    private String extendInfo;
} 