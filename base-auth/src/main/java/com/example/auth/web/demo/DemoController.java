package com.example.auth.web.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.annotation.AuthIgnore;
import com.example.auth.annotation.Logical;
import com.example.auth.annotation.RequiresLogin;
import com.example.auth.annotation.RequiresPermission;
import com.example.auth.annotation.RequiresRole;
import com.example.auth.service.AuthService;
import com.example.auth.response.Result;

import lombok.RequiredArgsConstructor;

/**
 * 示例控制器
 * 用于演示注解的使用方式
 * 
 * @author example
 */
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final AuthService authService;
    
    /**
     * 公共接口，无需认证
     * 
     * @return 响应结果
     */
    @GetMapping("/public")
    @AuthIgnore
    public Result<String> publicApi() {
        return Result.ok("这是一个公共接口，无需认证");
    }
    
    /**
     * 需要登录的接口
     * 
     * @return 响应结果
     */
    @GetMapping("/login")
    @RequiresLogin
    public Result<String> loginApi() {
        return Result.ok("您已登录，可以访问此接口");
    }
    
    /**
     * 需要权限的接口
     * 
     * @return 响应结果
     */
    @GetMapping("/permission")
    @RequiresPermission("system:user:view")
    public Result<String> permissionApi() {
        return Result.ok("您有system:user:view权限，可以访问此接口");
    }
    
    /**
     * 需要多个权限的接口（AND逻辑）
     * 
     * @return 响应结果
     */
    @GetMapping("/permissions/and")
    @RequiresPermission(value = {"system:user:view", "system:user:edit"}, logical = Logical.AND)
    public Result<String> permissionsAndApi() {
        return Result.ok("您同时拥有system:user:view和system:user:edit权限，可以访问此接口");
    }
    
    /**
     * 需要多个权限的接口（OR逻辑）
     * 
     * @return 响应结果
     */
    @GetMapping("/permissions/or")
    @RequiresPermission(value = {"system:user:view", "system:user:edit"}, logical = Logical.OR)
    public Result<String> permissionsOrApi() {
        return Result.ok("您拥有system:user:view或system:user:edit权限之一，可以访问此接口");
    }
    
    /**
     * 需要角色的接口
     * 
     * @return 响应结果
     */
    @GetMapping("/role")
    @RequiresRole("admin")
    public Result<String> roleApi() {
        return Result.ok("您有admin角色，可以访问此接口");
    }
    
    /**
     * 需要多个角色的接口（AND逻辑）
     * 
     * @return 响应结果
     */
    @GetMapping("/roles/and")
    @RequiresRole(value = {"admin", "system"}, logical = Logical.AND)
    public Result<String> rolesAndApi() {
        return Result.ok("您同时拥有admin和system角色，可以访问此接口");
    }
    
    /**
     * 需要多个角色的接口（OR逻辑）
     * 
     * @return 响应结果
     */
    @GetMapping("/roles/or")
    @RequiresRole(value = {"admin", "system"}, logical = Logical.OR)
    public Result<String> rolesOrApi() {
        return Result.ok("您拥有admin或system角色之一，可以访问此接口");
    }
    
    /**
     * 获取当前登录用户信息
     * 
     * @return 响应结果
     */
    @GetMapping("/user")
    @RequiresLogin
    public Result<Object> userApi() {
        return Result.ok(authService.getLoginUser());
    }
} 