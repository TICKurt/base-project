package com.example.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户实体类
 * 对应数据库表: sys_user
 *
 * @author example
 * @date 2023-04-01
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（UUID）
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（加密存储）
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别（0-未知，1-男，2-女）
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 状态（0-禁用，1-正常，2-锁定）
     */
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
    private Integer userType;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 密码重置时间
     */
    private Date pwdResetTime;

    /**
     * 密码过期时间
     */
    private Date pwdExpireTime;

    /**
     * 账号是否未过期（1-未过期，0-已过期）
     */
    private Boolean accountNonExpired;

    /**
     * 账号是否未锁定（1-未锁定，0-已锁定）
     */
    private Boolean accountNonLocked;

    /**
     * 密码是否未过期（1-未过期，0-已过期）
     */
    private Boolean credentialsNonExpired;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否删除（0-否，1-是）
     */
    @TableLogic
    private Boolean isDeleted;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 备注
     */
    private String remark;
} 