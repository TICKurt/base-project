package com.example.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库表：sys_user
 * 
 * @author example
 */
@Data
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别(0女,1男,2未知)
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 所属组织ID
     */
    private String orgId;

    /**
     * 所属组织名称
     */
    private String orgName;

    /**
     * 状态(0禁用,1正常)
     */
    private Integer status;

    /**
     * 是否为管理员(0否,1是)
     */
    private Integer isAdmin;

    /**
     * 账号锁定时间
     */
    private LocalDateTime lockTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除(0否,1是)
     * 逻辑删除字段
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 扩展属性: 用户类型
     * 可以根据业务需求扩展，例如：普通用户、VIP用户、临时用户等
     * 预留字段，便于后续扩展
     */
    @TableField(exist = false)
    private String userType;
    
    /**
     * 扩展属性: 当前用户的角色ID列表
     * 非数据库字段，用于传输数据
     */
    @TableField(exist = false)
    private String[] roleIds;
    
    /**
     * 扩展属性: 当前用户的角色名称列表
     * 非数据库字段，用于传输数据
     */
    @TableField(exist = false)
    private String[] roleNames;
    
    /**
     * 扩展属性: 当前用户的权限列表
     * 非数据库字段，用于传输数据
     */
    @TableField(exist = false)
    private String[] permissions;
} 