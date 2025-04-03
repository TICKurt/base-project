package com.example.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 角色数据传输对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysRoleDTO {

    /**
     * 角色ID
     */
    private String id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    private String name;

    /**
     * 角色编码
     */
    @Size(max = 50, message = "角色编码长度不能超过50个字符")
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
     * 角色状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过255个字符")
    private String remark;

    /**
     * 菜单ID列表
     */
    private List<String> menuIds;

    /**
     * 组织机构ID列表
     */
    private List<String> orgIds;

    // 查询参数

    /**
     * 角色名称（模糊查询）
     */
    private String nameLike;

    /**
     * 角色编码（模糊查询）
     */
    private String codeLike;

    /**
     * 角色状态（精确查询）
     */
    private Integer statusFilter;

    /**
     * 数据范围（精确查询）
     */
    private Integer dataScopeFilter;

    /**
     * 创建时间范围开始
     */
    private String beginCreateTime;

    /**
     * 创建时间范围结束
     */
    private String endCreateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;
} 