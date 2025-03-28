package com.example.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色实体类
 * 对应数据库表：sys_role
 * 
 * @author example
 */
@Data
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色类型
     */
    private Integer type;

    /**
     * 角色排序
     */
    private Integer sort;

    /**
     * 数据范围（1：全部数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    private Integer dataScope;

    /**
     * 状态(0禁用,1正常)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

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
     * 扩展属性: 角色关联的菜单ID列表
     * 非数据库字段，用于传输数据
     */
    @TableField(exist = false)
    private String[] menuIds;
    
    /**
     * 扩展属性: 角色关联的组织机构ID列表
     * 非数据库字段，用于数据权限控制
     */
    @TableField(exist = false)
    private String[] orgIds;
} 