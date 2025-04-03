package com.example.auth.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色视图对象
 * 用于向前端返回角色信息
 *
 * @author example
 * @date 2023-04-01
 */
@Data
public class RoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
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
     * 角色类型名称
     */
    private String roleTypeName;

    /**
     * 数据权限范围（1-全部数据，2-本部门及以下，3-本部门，4-仅本人，5-自定义）
     */
    private Integer dataScope;
    
    /**
     * 数据权限范围名称
     */
    private String dataScopeName;

    /**
     * 状态（0-禁用，1-正常）
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 是否内置（0-否，1-是，内置角色不可删除）
     */
    private Boolean isBuiltIn;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 关联的菜单ID列表
     */
    private List<String> menuIds;
    
    /**
     * 关联的菜单名称列表
     */
    private List<String> menuNames;

    /**
     * 关联的组织ID列表（用于数据权限控制，当dataScope=5时使用）
     */
    private List<String> orgIds;
    
    /**
     * 关联的组织名称列表
     */
    private List<String> orgNames;
    
    /**
     * 关联的用户数量
     */
    private Long userCount;

    /**
     * 备注
     */
    private String remark;
} 