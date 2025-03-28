package com.example.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色菜单关联实体类
 * 对应数据库表：sys_role_menu
 * 
 * @author example
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

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
    public RoleMenu() {
    }
    
    /**
     * 扩展构造函数，便于代码中快速创建角色菜单关联对象
     * 
     * @param roleId 角色ID
     * @param menuId 菜单ID
     */
    public RoleMenu(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
} 