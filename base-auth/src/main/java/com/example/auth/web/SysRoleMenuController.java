package com.example.auth.web;

import com.example.auth.dto.SysRoleMenuDTO;
import com.example.auth.service.SysRoleService;
import com.example.auth.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色菜单关联控制器
 *
 * @author 作者
 * @date 创建时间
 */
@RestController
@RequestMapping("/role/menu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleService roleService;

    /**
     * 获取角色的菜单ID列表
     */
    @GetMapping("/{roleId}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<List<String>> getRoleMenus(@PathVariable String roleId) {
        List<String> menuIds = roleService.getRoleMenuIds(roleId);
        return Result.ok(menuIds);
    }

    /**
     * 角色分配菜单权限
     */
    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Void> assignRoleMenus(@Valid @RequestBody SysRoleMenuDTO dto) {
        roleService.assignRoleMenus(dto);
        return Result.ok();
    }

    /**
     * 获取多个角色的权限标识
     */
    @GetMapping("/permissions")
    public Result<List<String>> getMultiRolePermissions(@RequestParam List<String> roleIds) {
        List<String> permissions = roleService.getPermissionsByRoleIds(roleIds);
        return Result.ok(permissions);
    }

    /**
     * 检查角色是否有指定权限
     */
    @GetMapping("/hasPermission")
    public Result<Boolean> hasPermission(@RequestParam String roleId, @RequestParam String permission) {
        boolean hasPermission = roleService.hasPermission(roleId, permission);
        return Result.ok(hasPermission);
    }
} 