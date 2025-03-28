package com.example.auth.domain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import lombok.Data;

/**
 * 登录用户视图对象
 * 登录成功后返回的用户信息
 * 
 * @author example
 */
@Data
public class LoginUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户头像
     */
    private String avatar;
    
    /**
     * 组织ID
     */
    private Long orgId;
    
    /**
     * 组织名称
     */
    private String orgName;
    
    /**
     * 用户类型（可扩展，例如：0系统用户，1普通用户，2商家用户）
     */
    private Integer userType;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表（角色代码）
     */
    private Set<String> roles;
    
    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户Token
     * 前端请求接口时需要带上用于权限验证
     */
    private String token;
    
    /**
     * 过期时间
     */
    private Date expireTime;
    
    /**
     * 刷新Token
     * 可以使用刷新Token获取新的访问Token
     */
    private String refreshToken;
    
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    
    /**
     * 是否为管理员（1是，0否）
     */
    private Integer isAdmin;
    
    /**
     * 是否为租户管理员
     */
    private Boolean isTenantAdmin;
    
    /**
     * 数据权限范围
     * 0：全部数据权限
     * 1：自定义数据权限
     * 2：本部门数据权限
     * 3：本部门及以下数据权限
     * 4：仅本人数据权限
     */
    private Integer dataScope;
    
    /**
     * 数据权限对应的组织ID列表
     */
    private Set<Long> dataScopeOrgIds;
} 