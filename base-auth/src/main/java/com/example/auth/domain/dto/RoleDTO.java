package com.example.auth.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色数据传输对象
 * 用于接收前端传入的角色信息
 *
 * @author example
 * @date 2023-04-01
 */
@Data
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID（更新时使用）
     */
    private String id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 50, message = "角色名称长度必须在2-50个字符之间")
    private String name;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Length(min = 2, max = 50, message = "角色编码长度必须在2-50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_:]+$", message = "角色编码只能包含字母、数字、下划线和冒号")
    private String code;

    /**
     * 角色类型（1-系统角色，2-业务角色）
     */
    @Min(value = 1, message = "角色类型值不正确")
    @Max(value = 2, message = "角色类型值不正确")
    private Integer roleType;

    /**
     * 数据权限范围（1-全部数据，2-本部门及以下，3-本部门，4-仅本人，5-自定义）
     */
    @Min(value = 1, message = "数据权限范围值不正确")
    @Max(value = 5, message = "数据权限范围值不正确")
    private Integer dataScope;

    /**
     * 状态（0-禁用，1-正常）
     */
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 关联的菜单ID列表
     */
    private List<String> menuIds;

    /**
     * 关联的组织ID列表（用于数据权限控制，当dataScope=5时使用）
     */
    private List<String> orgIds;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 备注
     */
    private String remark;
} 