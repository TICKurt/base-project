package com.example.auth.web;

import com.example.auth.domain.dto.UserDTO;
import com.example.auth.domain.vo.UserVO;
import com.example.auth.service.SysOrganizationService;
import com.example.auth.service.SysUserService;
import com.example.core.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 组织用户关联控制器
 *
 * @author 作者
 * @date 创建时间
 */
@RestController
@RequestMapping("/organization/user")
public class SysOrgUserController {

    @Autowired
    private SysOrganizationService organizationService;

    @Autowired
    private SysUserService userService;

    /**
     * 获取组织下的用户列表
     */
    @GetMapping("/list/{orgId}")
    @PreAuthorize("hasAuthority('system:org:list')")
    public Result<List<UserVO>> getOrgUsers(@PathVariable String orgId, UserDTO dto) {
        dto.setOrgId(orgId);
        List<UserVO> users = userService.getUserListByOrg(dto);
        return Result.ok(users);
    }

    /**
     * 获取组织下用户数量
     */
    @GetMapping("/count/{orgId}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public Result<Integer> getOrgUserCount(@PathVariable String orgId) {
        Integer count = userService.countUserByOrgId(orgId);
        return Result.ok(count);
    }

    /**
     * 向组织添加用户
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<Void> addUsersToOrg(@Valid @RequestBody Map<String, Object> params) {
        String orgId = (String) params.get("orgId");
        @SuppressWarnings("unchecked")
        List<String> userIds = (List<String>) params.get("userIds");
        organizationService.addUsersToOrg(orgId, userIds);
        return Result.ok();
    }

    /**
     * 从组织移除用户
     */
    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<Void> removeUsersFromOrg(@Valid @RequestBody Map<String, Object> params) {
        String orgId = (String) params.get("orgId");
        @SuppressWarnings("unchecked")
        List<String> userIds = (List<String>) params.get("userIds");
        organizationService.removeUsersFromOrg(orgId, userIds);
        return Result.ok();
    }

    /**
     * 获取组织负责人信息
     */
    @GetMapping("/leader/{orgId}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public Result<UserVO> getOrgLeader(@PathVariable String orgId) {
        String leaderId = organizationService.getOrgLeaderId(orgId);
        if (leaderId == null) {
            return Result.ok(null);
        }
        UserVO leader = userService.getUserById(leaderId);
        return Result.ok(leader);
    }

    /**
     * 设置组织负责人
     */
    @PutMapping("/leader")
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<Void> setOrgLeader(@RequestParam String orgId, @RequestParam String userId) {
        organizationService.setOrgLeader(orgId, userId);
        return Result.ok();
    }

    /**
     * 获取未分配到该组织的用户列表
     */
    @GetMapping("/unassigned/{orgId}")
    @PreAuthorize("hasAuthority('system:org:list')")
    public Result<List<UserVO>> getUnassignedUsers(@PathVariable String orgId, UserDTO dto) {
        List<UserVO> users = userService.getUnassignedOrgUsers(orgId, dto);
        return Result.ok(users);
    }
} 