package com.example.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色视图对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysRoleVO {

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
     * 角色排序
     */
    private Integer sort;

    /**
     * 数据范围（1：全部数据权限；2：本部门及以下数据权限；3：本部门数据权限；4：仅本人数据权限；5：自定义数据权限）
     */
    private Integer dataScope;

    /**
     * 数据范围名称
     */
    private String dataScopeName;

    /**
     * 角色状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

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
     * 菜单ID列表
     */
    private List<String> menuIds;

    /**
     * 组织机构ID列表
     */
    private List<String> orgIds;

    /**
     * 菜单权限列表（权限标识）
     */
    private List<String> permissions;

    /**
     * 用户数量（拥有该角色的用户数量）
     */
    private Long userCount;

    /**
     * 是否为内置角色（0否 1是）
     */
    private Integer isBuiltin;
} 