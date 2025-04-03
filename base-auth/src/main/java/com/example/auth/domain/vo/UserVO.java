package com.example.auth.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户视图对象
 * 用于向前端返回用户信息
 *
 * @author example
 * @date 2023-04-01
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别（0-未知，1-男，2-女）
     */
    private Integer gender;
    
    /**
     * 性别名称
     */
    private String genderName;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 状态（0-禁用，1-正常，2-锁定）
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 所属组织ID
     */
    private String orgId;
    
    /**
     * 所属组织名称
     */
    private String orgName;

    /**
     * 职位
     */
    private String position;

    /**
     * 工号
     */
    private String workNo;

    /**
     * 用户类型（0-超级管理员，1-管理员，2-普通用户）
     */
    private Integer userType;
    
    /**
     * 用户类型名称
     */
    private String userTypeName;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 角色ID列表
     */
    private List<String> roleIds;
    
    /**
     * 角色名称列表
     */
    private List<String> roleNames;

    /**
     * 备注
     */
    private String remark;
} 