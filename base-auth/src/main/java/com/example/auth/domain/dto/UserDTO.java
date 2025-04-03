package com.example.auth.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户数据传输对象
 * 用于接收前端传入的用户信息
 *
 * @author example
 * @date 2023-04-01
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID（更新时使用）
     */
    private String id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, max = 20, message = "用户名长度必须在4-20个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "用户名只能包含字母、数字、下划线和中划线")
    private String username;

    /**
     * 密码（创建时必填）
     */
    private String password;

    /**
     * 确认密码（创建时必填）
     */
    private String confirmPassword;

    /**
     * 昵称
     */
    @Length(max = 50, message = "昵称长度不能超过50个字符")
    private String nickname;

    /**
     * 真实姓名
     */
    @Length(max = 50, message = "真实姓名长度不能超过50个字符")
    private String realName;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    /**
     * 性别（0-未知，1-男，2-女）
     */
    @Min(value = 0, message = "性别值不正确")
    @Max(value = 2, message = "性别值不正确")
    private Integer gender;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 状态（0-禁用，1-正常，2-锁定）
     */
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 2, message = "状态值不正确")
    private Integer status;

    /**
     * 所属组织ID
     */
    private String orgId;

    /**
     * 职位
     */
    private String position;

    /**
     * 工号
     */
    private String workNo;

    /**
     * 用户类型（0-超级管理员，1-管理员，2-普通用户）
     */
    @Min(value = 0, message = "用户类型值不正确")
    @Max(value = 2, message = "用户类型值不正确")
    private Integer userType;

    /**
     * 角色ID列表
     */
    private List<String> roleIds;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 备注
     */
    private String remark;
} 