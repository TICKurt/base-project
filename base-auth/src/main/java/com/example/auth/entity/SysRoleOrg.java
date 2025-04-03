package com.example.auth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色组织关联实体类
 *
 * @author 作者
 * @date 创建时间
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role_org")
public class SysRoleOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（UUID）
     */
    @TableId
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
    private LocalDateTime createTime;

    /**
     * 租户ID
     */
    private String tenantId;
} 