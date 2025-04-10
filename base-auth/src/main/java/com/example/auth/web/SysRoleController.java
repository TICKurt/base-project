package com.example.auth.web;

import com.github.pagehelper.PageInfo;
import com.example.auth.annotation.RequiresPermission;
import com.example.auth.domain.dto.RoleDTO;
import com.example.auth.domain.dto.RoleQueryDTO;
import com.example.auth.domain.vo.RoleVO;
import com.example.auth.service.AuthService;
import com.example.auth.service.SysRoleService;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色管理控制器
 *
 * @author example
 * @date 2023-04-01
 */
@Slf4j
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
@Validated
public class SysRoleController {

    private final SysRoleService roleService;
    private final AuthService authService;

    /**
     * 获取角色分页列表
     *
     * @param pageNum  页码
     * @param pageSize  每页数量
     * @param query 查询参数
     * @return 角色分页数据
     */
    @GetMapping("/page")
    @RequiresPermission("system:role:list")
    public Result<PageInfo<RoleVO>> getRolePage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            RoleQueryDTO query) {
        PageInfo<RoleVO> result = roleService.getRolePage(pageNum, pageSize, query);
        return Result.ok(result);
    }

    /**
     * 获取角色详情
     *
     * @param roleId 角色ID
     * @return 角色详情
     */
    @GetMapping("/{roleId}")
    @RequiresPermission("system:role:query")
    public Result<RoleVO> getRoleInfo(@PathVariable String roleId) {
        RoleVO role = roleService.getRoleById(roleId);
        if (role == null) {
            return Result.error("角色不存在");
        }
        return Result.ok(role);
    }

    /**
     * 创建角色
     *
     * @param roleDTO 角色信息
     * @return 角色ID
     */
    @PostMapping
    @RequiresPermission("system:role:add")
    public Result<String> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 校验角色数据是否合法
        if (!roleService.checkRoleValid(roleDTO)) {
            return Result.error("角色名称或编码已存在");
        }
        
        String roleId = roleService.createRole(roleDTO, loginUser.getUserId());
        return Result.ok(roleId, "角色创建成功");
    }

    /**
     * 更新角色
     *
     * @param roleDTO 角色信息
     * @return 操作结果
     */
    @PutMapping
    @RequiresPermission("system:role:edit")
    public Result<String> updateRole(@Valid @RequestBody RoleDTO roleDTO) {
        // 校验角色ID
        if (roleDTO.getId() == null || roleDTO.getId().isEmpty()) {
            return Result.error("角色ID不能为空");
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 校验角色数据是否合法
        if (!roleService.checkRoleValid(roleDTO)) {
            return Result.error("角色名称或编码已存在");
        }
        
        boolean result = roleService.updateRole(roleDTO, loginUser.getUserId());
        return result ? Result.ok("角色更新成功") : Result.error("角色更新失败");
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 操作结果
     */
    @DeleteMapping("/{roleId}")
    @RequiresPermission("system:role:remove")
    public Result<String> deleteRole(@PathVariable String roleId) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        try {
            boolean result = roleService.deleteRole(roleId, loginUser.getUserId());
            return result ? Result.ok("角色删除成功") : Result.error("角色删除失败");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 批量删除角色
     *
     * @param roleIds 角色ID列表
     * @return 操作结果
     */
    @DeleteMapping("/batch")
    @RequiresPermission("system:role:remove")
    public Result<String> batchDeleteRoles(@RequestBody List<String> roleIds) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        if (roleIds.isEmpty()) {
            return Result.error("没有可删除的角色");
        }
        
        boolean result = roleService.batchDeleteRoles(roleIds, loginUser.getUserId());
        return result ? Result.ok("角色批量删除成功") : Result.error("角色批量删除失败");
    }

    /**
     * 更新角色状态
     *
     * @param roleId 角色ID
     * @param status 状态（0-禁用，1-正常）
     * @return 操作结果
     */
    @PutMapping("/{roleId}/status")
    @RequiresPermission("system:role:edit")
    public Result<String> updateStatus(
            @PathVariable String roleId,
            @RequestParam Integer status) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 校验状态值
        if (status < 0 || status > 1) {
            return Result.error("状态值不正确");
        }
        
        try {
            boolean result = roleService.updateStatus(roleId, status, loginUser.getUserId());
            return result ? Result.ok("状态更新成功") : Result.error("状态更新失败");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 分配角色菜单权限
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     * @return 操作结果
     */
    @PutMapping("/{roleId}/menus")
    @RequiresPermission("system:role:edit")
    public Result<String> assignMenus(
            @PathVariable String roleId,
            @RequestBody List<String> menuIds) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        boolean result = roleService.assignMenus(roleId, menuIds, loginUser.getUserId());
        return result ? Result.ok("菜单权限分配成功") : Result.error("菜单权限分配失败");
    }

    /**
     * 获取角色菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    @GetMapping("/{roleId}/menus")
    @RequiresPermission("system:role:query")
    public Result<List<String>> getRoleMenuIds(@PathVariable String roleId) {
        List<String> menuIds = roleService.getRoleMenuIds(roleId);
        return Result.ok(menuIds);
    }

    /**
     * 分配角色数据权限
     *
     * @param roleId    角色ID
     * @param dataScope 数据权限范围
     * @param orgIds    组织ID列表
     * @return 操作结果
     */
    @PutMapping("/{roleId}/dataScope")
    @RequiresPermission("system:role:edit")
    public Result<String> assignDataScope(
            @PathVariable String roleId,
            @RequestParam Integer dataScope,
            @RequestBody(required = false) List<String> orgIds) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 校验数据权限范围值
        if (dataScope < 1 || dataScope > 5) {
            return Result.error("数据权限范围值不正确");
        }
        
        // 如果是自定义数据权限，需要传组织ID列表
        if (dataScope == 5 && (orgIds == null || orgIds.isEmpty())) {
            return Result.error("自定义数据权限需要选择组织机构");
        }
        
        boolean result = roleService.assignDataScope(roleId, dataScope, orgIds, loginUser.getUserId());
        return result ? Result.ok("数据权限分配成功") : Result.error("数据权限分配失败");
    }

    /**
     * 获取角色的数据权限组织ID列表
     *
     * @param roleId 角色ID
     * @return 组织ID列表
     */
    @GetMapping("/{roleId}/orgs")
    @RequiresPermission("system:role:query")
    public Result<List<String>> getRoleOrgIds(@PathVariable String roleId) {
        List<String> orgIds = roleService.getRoleOrgIds(roleId);
        return Result.ok(orgIds);
    }
    
    /**
     * 获取所有角色列表
     *
     * @return 角色列表
     */
    @GetMapping("/list")
    @RequiresPermission("system:role:list")
    public Result<List<RoleVO>> getAllRoles() {
        List<RoleVO> roles = roleService.list().stream()
                .map(role -> {
                    RoleVO roleVO = new RoleVO();
                    roleVO.setId(role.getId());
                    roleVO.setName(role.getName());
                    roleVO.setCode(role.getCode());
                    roleVO.setStatus(role.getStatus());
                    roleVO.setRoleType(role.getRoleType());
                    roleVO.setDataScope(role.getDataScope());
                    roleVO.setSort(role.getSort());
                    return roleVO;
                })
                .collect(Collectors.toList());
        return Result.ok(roles);
    }
    
    /**
     * 获取角色关联的用户数量
     *
     * @param roleId 角色ID
     * @return 用户数量
     */
    @GetMapping("/{roleId}/userCount")
    @RequiresPermission("system:role:query")
    public Result<Integer> getUserCountByRoleId(@PathVariable String roleId) {
        Integer userCount = roleService.getUserCountByRoleId(roleId);
        return Result.ok(userCount);
    }
} 