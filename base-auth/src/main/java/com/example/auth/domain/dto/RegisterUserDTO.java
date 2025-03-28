package com.example.auth.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 注册用户DTO
 * 用于处理用户注册请求
 * 
 * @author example
 */
@Data
public class RegisterUserDTO {

    /**
     * 用户名
     * 必填，且需要符合指定格式
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 5, max = 30, message = "用户名长度必须在5-30个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{5,30}$", message = "用户名只能包含字母、数字、下划线和连字符，且长度在5-30个字符之间")
    private String username;

    /**
     * 密码
     * 必填，且需要符合长度要求
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    private String password;

    /**
     * 确认密码
     * 必填，且需要与密码一致
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 姓名/昵称
     * 必填
     */
    @NotBlank(message = "姓名不能为空")
    @Size(max = 30, message = "姓名长度不能超过30个字符")
    private String name;

    /**
     * 手机号
     * 选填，但如果填写需要符合手机号格式
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    /**
     * 邮箱
     * 选填，但如果填写需要符合邮箱格式
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 性别
     * 0：女
     * 1：男
     * 2：未知
     */
    private Integer gender;

    /**
     * 验证码
     * 必填
     */
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    /**
     * 验证码Key
     * 必填
     */
    @NotBlank(message = "验证码Key不能为空")
    private String captchaKey;

    /**
     * 注册类型
     * 1：用户名密码注册（默认）
     * 2：手机号注册
     * 3：邮箱注册
     * 4：第三方注册
     */
    private Integer registerType = 1;

    /**
     * 注册来源
     * 1：PC端（默认）
     * 2：移动端
     * 3：小程序
     * 4：其他终端
     */
    private Integer source = 1;
    
    /**
     * 扩展信息
     * 用于存储不同注册方式下的额外信息
     */
    private String extendInfo;
    
    /**
     * 组织ID
     * 选填，用于指定用户所属组织
     */
    private Long orgId;
    
    /**
     * 用户类型
     * 选填，可扩展为不同类型的用户
     */
    private Integer userType;
} 