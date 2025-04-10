package com.example.auth.web.demo;

import com.example.auth.annotation.AuthIgnore;
import com.example.auth.annotation.Logical;
import com.example.auth.annotation.RequiresLogin;
import com.example.auth.annotation.RequiresPermission;
import com.example.auth.annotation.RequiresRole;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.service.AuthService;
import com.example.auth.response.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户权限演示控制器
 * 用于演示权限注解的使用方式
 *
 * @author example
 * @date 2023-04-01
 */
@RestController
@RequestMapping("/demo/user")
@RequiredArgsConstructor
public class UserDemoController {

    private final AuthService authService;

    /**
     * 匿名访问接口
     * 不需要登录就可以访问
     *
     * @return 结果
     */
    @GetMapping("/anonymous")
    @AuthIgnore
    public Result<String> anonymous() {
        return Result.ok("匿名访问成功");
    }

    /**
     * 需要登录才能访问的接口
     *
     * @return 结果
     */
    @GetMapping("/login")
    @RequiresLogin
    public Result<LoginUserVO> needLogin() {
        return Result.ok("登录访问成功",authService.getLoginUser());
    }

    /**
     * 需要user:view权限才能访问的接口
     *
     * @return 结果
     */
    @GetMapping("/permission/view")
    @RequiresPermission("user:view")
    public Result<String> needViewPermission() {
        return Result.ok("user:view权限访问成功");
    }

    /**
     * 需要同时具有user:add和user:edit权限才能访问的接口
     * 逻辑AND
     *
     * @return 结果
     */
    @GetMapping("/permission/add-edit")
    @RequiresPermission(value = {"user:add", "user:edit"}, logical = Logical.AND)
    public Result<String> needAddAndEditPermission() {
        return Result.ok("user:add AND user:edit权限访问成功");
    }

    /**
     * 需要user:add或user:edit权限之一就能访问的接口
     * 逻辑OR
     *
     * @return 结果
     */
    @GetMapping("/permission/add-or-edit")
    @RequiresPermission(value = {"user:add", "user:edit"}, logical = Logical.OR)
    public Result<String> needAddOrEditPermission() {
        return Result.ok("user:add OR user:edit权限访问成功");
    }

    /**
     * 需要admin角色才能访问的接口
     *
     * @return 结果
     */
    @GetMapping("/role/admin")
    @RequiresRole("admin")
    public Result<String> needAdminRole() {
        return Result.ok("admin角色访问成功");
    }

    /**
     * 需要同时具有admin和operator角色才能访问的接口
     * 逻辑AND
     *
     * @return 结果
     */
    @GetMapping("/role/admin-operator")
    @RequiresRole(value = {"admin", "operator"}, logical = Logical.AND)
    public Result<String> needAdminAndOperatorRole() {
        return Result.ok("admin AND operator角色访问成功");
    }

    /**
     * 需要admin或operator角色之一就能访问的接口
     * 逻辑OR
     *
     * @return 结果
     */
    @GetMapping("/role/admin-or-operator")
    @RequiresRole(value = {"admin", "operator"}, logical = Logical.OR)
    public Result<String> needAdminOrOperatorRole() {
        return Result.ok("admin OR operator角色访问成功");
    }
} 