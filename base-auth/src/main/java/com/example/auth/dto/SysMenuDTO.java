package com.example.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 菜单数据传输对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysMenuDTO {

    /**
     * 菜单ID
     */
    private String id;

    /**
     * 父菜单ID
     */
    private String parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String name;

    /**
     * 路由路径
     */
    @Size(max = 100, message = "路由路径长度不能超过100个字符")
    private String path;

    /**
     * 组件路径
     */
    @Size(max = 100, message = "组件路径长度不能超过100个字符")
    private String component;

    /**
     * 权限标识
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    private String perms;

    /**
     * 菜单图标
     */
    @Size(max = 100, message = "菜单图标长度不能超过100个字符")
    private String icon;

    /**
     * 菜单类型（0-目录，1-菜单，2-按钮/权限）
     */
    private Integer menuType;

    /**
     * 是否可见（0-隐藏，1-显示）
     */
    private Integer visible;

    /**
     * 状态（0-禁用，1-正常）
     */
    private Integer status;

    /**
     * 是否缓存（0-不缓存，1-缓存）
     */
    private Integer keepAlive;

    /**
     * 是否总是显示（适用于一级菜单）
     */
    private Integer alwaysShow;

    /**
     * 打开方式（_self-当前页面，_blank-新窗口）
     */
    private String target;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过255个字符")
    private String remark;

    // 查询参数

    /**
     * 菜单名称（模糊查询）
     */
    private String nameLike;

    /**
     * 状态（精确查询）
     */
    private Integer statusFilter;

    /**
     * 菜单类型（精确查询）
     */
    private Integer menuTypeFilter;

    /**
     * 是否包含按钮
     */
    private Boolean includeButtons;

    /**
     * 是否只查询顶级菜单
     */
    private Boolean onlyRoot;

    /**
     * 是否获取计算字段（children和hasChildren）
     */
    private Boolean withCalcFields;
} 