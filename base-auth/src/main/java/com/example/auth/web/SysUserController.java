package com.example.auth.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.auth.annotation.RequiresPermission;
import com.example.auth.domain.dto.UserDTO;
import com.example.auth.domain.dto.UserQueryDTO;
import com.example.auth.domain.vo.UserVO;
import com.example.auth.service.AuthService;
import com.example.auth.service.SysUserService;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统用户管理控制器
 *
 * @author example
 * @date 2023-04-01
 */
@Slf4j
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;
    private final AuthService authService;

    /**
     * 获取用户分页列表
     *
     * @param page  页码
     * @param size  每页数量
     * @param query 查询参数
     * @return 用户分页数据
     */
    @GetMapping("/page")
    @RequiresPermission("system:user:list")
    public Result<IPage<UserVO>> getUserPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            UserQueryDTO query) {
        IPage<UserVO> result = userService.getUserPage(page, size, query);
        return Result.ok(result);
    }

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return 用户详情
     */
    @GetMapping("/{userId}")
    @RequiresPermission("system:user:query")
    public Result<UserVO> getUserInfo(@PathVariable String userId) {
        UserVO user = userService.getUserById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.ok(user);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前用户信息
     */
    @GetMapping("/info")
    public Result<LoginUserVO> getCurrentUserInfo() {
        LoginUserVO loginUser = authService.getLoginUser();
        if (loginUser == null) {
            return Result.error("用户未登录");
        }
        return Result.ok(loginUser);
    }

    /**
     * 创建用户
     *
     * @param userDTO 用户信息
     * @return 用户ID
     */
    @PostMapping
    @RequiresPermission("system:user:add")
    public Result<String> createUser(@Valid @RequestBody UserDTO userDTO) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 校验用户数据是否合法
        if (!userService.checkUserValid(userDTO)) {
            return Result.error("用户名、手机号或邮箱已存在");
        }
        
        String userId = userService.createUser(userDTO, loginUser.getUserId());
        return Result.ok(userId, "用户创建成功");
    }

    /**
     * 更新用户
     *
     * @param userDTO 用户信息
     * @return 操作结果
     */
    @PutMapping
    @RequiresPermission("system:user:edit")
    public Result<String> updateUser(@Valid @RequestBody UserDTO userDTO) {
        // 校验用户ID
        if (userDTO.getId() == null || userDTO.getId().isEmpty()) {
            return Result.error("用户ID不能为空");
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 校验用户数据是否合法
        if (!userService.checkUserValid(userDTO)) {
            return Result.error("用户名、手机号或邮箱已存在");
        }
        
        boolean result = userService.updateUser(userDTO, loginUser.getUserId());
        return result ? Result.ok("用户更新成功") : Result.error("用户更新失败");
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{userId}")
    @RequiresPermission("system:user:remove")
    public Result<String> deleteUser(@PathVariable String userId) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 不能删除自己
        if (userId.equals(loginUser.getUserId())) {
            return Result.error("不能删除当前登录用户");
        }
        
        boolean result = userService.deleteUser(userId, loginUser.getUserId());
        return result ? Result.ok("用户删除成功") : Result.error("用户删除失败");
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户ID列表
     * @return 操作结果
     */
    @DeleteMapping("/batch")
    @RequiresPermission("system:user:remove")
    public Result<String> batchDeleteUsers(@RequestBody List<String> userIds) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 过滤掉当前登录用户
        userIds.remove(loginUser.getUserId());
        
        if (userIds.isEmpty()) {
            return Result.error("没有可删除的用户");
        }
        
        boolean result = userService.batchDeleteUsers(userIds, loginUser.getUserId());
        return result ? Result.ok("用户批量删除成功") : Result.error("用户批量删除失败");
    }

    /**
     * 重置用户密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @return 操作结果
     */
    @PutMapping("/{userId}/password")
    @RequiresPermission("system:user:resetPwd")
    public Result<String> resetPassword(
            @PathVariable String userId,
            @RequestParam String newPassword) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        boolean result = userService.resetPassword(userId, newPassword, loginUser.getUserId());
        return result ? Result.ok("密码重置成功") : Result.error("密码重置失败");
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态（0-禁用，1-正常，2-锁定）
     * @return 操作结果
     */
    @PutMapping("/{userId}/status")
    @RequiresPermission("system:user:edit")
    public Result<String> updateStatus(
            @PathVariable String userId,
            @RequestParam Integer status) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 不能修改自己的状态
        if (userId.equals(loginUser.getUserId())) {
            return Result.error("不能修改当前登录用户的状态");
        }
        
        // 校验状态值
        if (status < 0 || status > 2) {
            return Result.error("状态值不正确");
        }
        
        boolean result = userService.updateStatus(userId, status, loginUser.getUserId());
        return result ? Result.ok("状态更新成功") : Result.error("状态更新失败");
    }

    /**
     * 获取用户角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    @GetMapping("/{userId}/roles")
    @RequiresPermission("system:user:query")
    public Result<List<String>> getUserRoles(@PathVariable String userId) {
        List<String> roleIds = userService.getUserRoleIds(userId);
        return Result.ok(roleIds);
    }

    /**
     * 分配用户角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     * @return 操作结果
     */
    @PutMapping("/{userId}/roles")
    @RequiresPermission("system:user:edit")
    public Result<String> assignRoles(
            @PathVariable String userId,
            @RequestBody List<String> roleIds) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        boolean result = userService.assignRoles(userId, roleIds, loginUser.getUserId());
        return result ? Result.ok("角色分配成功") : Result.error("角色分配失败");
    }

    /**
     * 根据组织ID获取用户列表
     *
     * @param orgId          组织ID
     * @param includeChildOrg 是否包含子组织
     * @return 用户列表
     */
    @GetMapping("/org/{orgId}")
    @RequiresPermission("system:user:list")
    public Result<List<UserVO>> getUsersByOrgId(
            @PathVariable String orgId,
            @RequestParam(defaultValue = "false") boolean includeChildOrg) {
        List<UserVO> users = userService.getUsersByOrgId(orgId, includeChildOrg);
        return Result.ok(users);
    }

    /**
     * 根据角色ID获取用户列表
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    @GetMapping("/role/{roleId}")
    @RequiresPermission("system:user:list")
    public Result<List<UserVO>> getUsersByRoleId(@PathVariable String roleId) {
        List<UserVO> users = userService.getUsersByRoleId(roleId);
        return Result.ok(users);
    }
} 