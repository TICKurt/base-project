package com.example.auth.web;

import com.example.auth.dto.SysRoleOrgDTO;
import com.example.auth.service.SysRoleService;
import com.example.core.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 角色组织关联控制器（数据权限）
 *
 * @author 作者
 * @date 创建时间
 */
@RestController
@RequestMapping("/role/dataScope")
public class SysRoleOrgController {

    @Autowired
    private SysRoleService roleService;

    /**
     * 获取角色的数据权限范围
     */
    @GetMapping("/{roleId}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<Map<String, Object>> getRoleDataScope(@PathVariable String roleId) {
        Map<String, Object> dataScope = roleService.getRoleDataScope(roleId);
        return Result.ok(dataScope);
    }

    /**
     * 设置角色的数据权限范围
     */
    @PutMapping
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Void> updateRoleDataScope(@Valid @RequestBody SysRoleOrgDTO dto) {
        roleService.updateRoleDataScope(dto);
        return Result.ok();
    }

    /**
     * 获取角色已分配的组织机构ID列表
     */
    @GetMapping("/orgs/{roleId}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<List<String>> getRoleOrgs(@PathVariable String roleId) {
        List<String> orgIds = roleService.getRoleOrgIds(roleId);
        return Result.ok(orgIds);
    }

    /**
     * 角色分配组织机构权限
     */
    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Void> assignRoleOrgs(@Valid @RequestBody SysRoleOrgDTO dto) {
        roleService.assignRoleOrgs(dto);
        return Result.ok();
    }

    /**
     * 获取用户的数据权限范围
     */
    @GetMapping("/user/{userId}")
    public Result<List<String>> getUserDataScope(@PathVariable String userId) {
        List<String> orgIds = roleService.getUserDataScopeOrgIds(userId);
        return Result.ok(orgIds);
    }
} 