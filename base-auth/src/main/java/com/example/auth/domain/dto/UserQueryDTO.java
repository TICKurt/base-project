package com.example.auth.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户查询条件DTO
 * 用于接收前端传入的查询条件
 *
 * @author example
 * @date 2023-04-01
 */
@Data
public class UserQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态（0-禁用，1-正常，2-锁定）
     */
    private Integer status;

    /**
     * 用户类型（0-超级管理员，1-管理员，2-普通用户）
     */
    private Integer userType;

    /**
     * 所属组织ID
     */
    private String orgId;

    /**
     * 是否包含子组织用户
     */
    private Boolean includeChildOrg;

    /**
     * 角色ID
     */
    private String roleId;

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
     * 排除的用户ID列表
     */
    private List<String> excludeUserIds;
} 