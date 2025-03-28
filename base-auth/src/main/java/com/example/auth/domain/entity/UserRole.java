package com.example.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色关联实体类
 * 对应数据库表：sys_user_role
 * 
 * @author example
 */
@Data
@TableName("sys_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

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
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 扩展构造函数，便于代码中创建对象
     */
    public UserRole() {
    }
    
    /**
     * 扩展构造函数，便于代码中快速创建用户角色关联对象
     * 
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    public UserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
} 