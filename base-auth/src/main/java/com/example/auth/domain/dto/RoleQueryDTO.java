package com.example.auth.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色查询条件DTO
 * 用于接收前端传入的查询条件
 *
 * @author example
 * @date 2023-04-01
 */
@Data
public class RoleQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 状态（0-禁用，1-正常）
     */
    private Integer status;

    /**
     * 创建时间起始
     */
    private Date beginTime;

    /**
     * 创建时间截止
     */
    private Date endTime;

    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 排除的角色ID列表
     */
    private List<String> excludeRoleIds;
} 