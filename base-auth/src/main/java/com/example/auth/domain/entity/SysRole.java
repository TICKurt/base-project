package com.example.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色实体类
 * 对应数据库表: sys_role
 *
 * @author example
 * @date 2023-04-01
 */
@Data
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（UUID）
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
     * 角色类型（1-系统角色，2-业务角色）
     */
    private Integer roleType;

    /**
     * 数据权限范围（1-全部数据，2-本部门及以下，3-本部门，4-仅本人，5-自定义）
     */
    private Integer dataScope;

    /**
     * 状态（0-禁用，1-正常）
     */
    private Integer status;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 是否内置（0-否，1-是，内置角色不可删除）
     */
    private Boolean isBuiltIn;

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