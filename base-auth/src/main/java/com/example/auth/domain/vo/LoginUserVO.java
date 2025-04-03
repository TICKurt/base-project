package com.example.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.Data;

/**
 * 登录用户视图对象
 * 包含用户基本信息、角色、权限等信息
 *
 * @author example
 * @date 2023-04-01
 */
@Data
public class LoginUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;
    
    /**
     * 用户类型（0-超级管理员，1-管理员，2-普通用户）
     */
    private Integer userType;

    /**
     * 所属组织ID
     */
    private String orgId;

    /**
     * 所属组织名称
     */
    private String orgName;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 角色ID列表
     */
    private List<String> roleIds;

    /**
     * 角色编码列表
     */
    private List<String> roleCodes;

    /**
     * 角色名称列表
     */
    private List<String> roleNames;

    /**
     * 权限标识集合
     */
    private Set<String> permissions;

    /**
     * 数据权限范围（1-全部数据，2-本部门及以下，3-本部门，4-仅本人，5-自定义）
     */
    private Integer dataScope;
    
    /**
     * 可访问的组织ID列表（当dataScope=5时使用）
     */
    private List<String> orgIds;
    
    /**
     * 令牌
     */
    private String token;
    
    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;
} 