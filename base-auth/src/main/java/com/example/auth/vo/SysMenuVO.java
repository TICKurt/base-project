package com.example.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单视图对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysMenuVO {

    /**
     * 菜单ID
     */
    private String id;

    /**
     * 父菜单ID
     */
    private String parentId;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
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
     * 菜单类型名称
     */
    private String menuTypeName;

    /**
     * 是否可见（0-隐藏，1-显示）
     */
    private Integer visible;

    /**
     * 可见状态名称
     */
    private String visibleName;

    /**
     * 状态（0-禁用，1-正常）
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 是否缓存（0-不缓存，1-缓存）
     */
    private Integer keepAlive;

    /**
     * 缓存状态名称
     */
    private String keepAliveName;

    /**
     * 是否总是显示（适用于一级菜单）
     */
    private Integer alwaysShow;

    /**
     * 是否总是显示名称
     */
    private String alwaysShowName;

    /**
     * 打开方式（_self-当前页面，_blank-新窗口）
     */
    private String target;

    /**
     * 菜单层级
     */
    private Integer level;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建人姓名
     */
    private String createByName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新人姓名
     */
    private String updateByName;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子菜单列表
     */
    private List<SysMenuVO> children = new ArrayList<>();

    /**
     * 是否有子节点
     */
    private Boolean hasChildren;

    /**
     * 按钮权限列表（当menuType=0或1时有效）
     */
    private List<SysMenuVO> permissions = new ArrayList<>();

    /**
     * 菜单路由元数据（前端路由需要）
     */
    private MetaVO meta;

    /**
     * 路由元数据内部类
     */
    @Data
    public static class MetaVO {
        /**
         * 标题
         */
        private String title;

        /**
         * 图标
         */
        private String icon;

        /**
         * 是否隐藏
         */
        private Boolean hidden;

        /**
         * 是否缓存
         */
        private Boolean keepAlive;

        /**
         * 权限标识
         */
        private String permission;
    }
} 