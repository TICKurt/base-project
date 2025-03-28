package com.example.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单实体类
 * 对应数据库表：sys_menu
 * 
 * @author example
 */
@Data
@TableName("sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父菜单ID
     */
    private String parentId;

    /**
     * 菜单类型（1目录，2菜单，3按钮）
     */
    private Integer type;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 是否缓存（0不缓存，1缓存）
     */
    private Integer keepAlive;

    /**
     * 是否可见（0隐藏，1显示）
     */
    private Integer visible;

    /**
     * 是否为外链（0否，1是）
     */
    private Integer isFrame;

    /**
     * 状态(0禁用,1正常)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除(0否,1是)
     * 逻辑删除字段
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 扩展属性: 子菜单列表
     * 非数据库字段，用于构建树形结构
     */
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();
    
    /**
     * 扩展属性: 是否选中
     * 非数据库字段，用于前端表格选择
     */
    @TableField(exist = false)
    private boolean selected;
    
    /**
     * 扩展属性: 是否展开
     * 非数据库字段，用于前端表格树形结构展开/收起状态
     */
    @TableField(exist = false)
    private boolean expanded;
    
    /**
     * 扩展属性: 路由元数据
     * 非数据库字段，用于前端路由配置
     * 可以根据需求扩展，例如：title、icon、breadcrumb等
     */
    @TableField(exist = false)
    private Object meta;
} 