package com.example.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色菜单关联数据传输对象
 *
 * @author 作者
 * @date 创建时间
 */
@Data
public class SysRoleMenuDTO {

    /**
     * 角色ID
     */
    @NotBlank(message = "角色ID不能为空")
    private String roleId;

    /**
     * 菜单ID列表
     */
    private List<String> menuIds;
} 