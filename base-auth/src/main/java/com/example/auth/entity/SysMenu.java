package com.example.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限实体类
 *
 * @author 作者
 * @date 创建时间
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（UUID）
     */
    @TableId
    private String id;

    /**
     * 父级菜单ID
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由路径（浏览器地址栏URL）
     */
    private String path;

    /**
     * 组件路径（前端组件相对路径）
     */
    private String component;

    /**
     * 权限标识（如：system:user:list）
     */
    private String perms;

    /**
     * 菜单图标
     */
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
     * 菜单层级
     */
    private Integer level;

    /**
     * 菜单路径（格式如：/根ID/父ID/）
     */
    private String treePath;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除（0-否，1-是）
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子菜单列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

    /**
     * 是否有子节点（非数据库字段）
     */
    @TableField(exist = false)
    private Boolean hasChildren;
} 