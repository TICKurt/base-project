package com.example.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色组织关联实体类
 * 对应数据库表: sys_role_org
 *
 * @author example
 * @date 2023-04-01
 */
@Data
@Accessors(chain = true)
@TableName("sys_role_org")
public class SysRoleOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（UUID）
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 组织ID
     */
    private String orgId;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 租户ID
     */
    private String tenantId;
} 