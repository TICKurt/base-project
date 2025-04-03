package com.example.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色-组织机构关联数据传输对象（数据权限）
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysRoleOrgDTO {

    /**
     * 角色ID
     */
    @NotBlank(message = "角色ID不能为空")
    private String roleId;

    /**
     * 数据权限范围（1-全部数据，2-本部门及以下，3-本部门，4-仅本人，5-自定义）
     */
    @NotNull(message = "数据权限范围不能为空")
    private Integer dataScope;

    /**
     * 组织机构ID列表（当数据权限范围为自定义时必填）
     */
    private List<String> orgIds;
} 