package com.example.business.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.auth.annotation.AuthIgnore;
import com.example.auth.annotation.RequiresLogin;
import com.example.auth.annotation.RequiresPermission;
import com.example.auth.annotation.RequiresRole;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.service.TokenService;
import com.example.auth.response.Result;
import com.example.business.domain.dto.TestItemDTO;
import com.example.business.domain.vo.TestItemVO;
import com.example.business.service.TestItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 测试项目控制器
 * 
 * @author example
 */
@RestController
@RequestMapping("/test-items")
@RequiredArgsConstructor
public class TestItemController {

    private final TestItemService testItemService;
    private final TokenService tokenService;
    
    /**
     * 登录接口
     * 为了测试方便，在测试模块中添加简化的登录接口
     * 实际项目中应该使用标准的登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果，包含token
     */
    @PostMapping("/login")
    @AuthIgnore
    public Result<LoginUserVO> login(@RequestParam String username, @RequestParam String password) {
        // 简单示例，实际应该查询数据库验证用户名和密码
        if ("admin".equals(username) && "admin123".equals(password)) {
            // 创建登录用户信息
            LoginUserVO loginUser = new LoginUserVO();
            loginUser.setUserId("1");
            loginUser.setUsername("admin");
            loginUser.setNickname("管理员");
            
            // 设置角色
            List<String> roleNames = new ArrayList<>();
            roleNames.add("admin");
            loginUser.setRoleNames(roleNames);

            // 设置权限
            Set<String> permissions = new HashSet<>();
            permissions.add("test:item:add");
            permissions.add("test:item:edit");
            permissions.add("test:item:delete");
            permissions.add("test:item:view");
            permissions.add("system:dict:query");
            loginUser.setPermissions(permissions);
            
            // 生成token
            String token = tokenService.createToken(loginUser);
            loginUser.setToken(token);
            
            // 设置过期时间
            Date expireTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000); // 30分钟后
            loginUser.setExpireTime(expireTime);
            
            return Result.ok(loginUser);
        } else if ("user1".equals(username) && "pass123".equals(password)) {
            // 普通用户登录
            LoginUserVO loginUser = new LoginUserVO();
            loginUser.setUserId("2");
            loginUser.setUsername("user1");
            loginUser.setNickname("用户1");
            
            // 设置角色List<String>
            List<String> roleNames = new ArrayList<>();
            roleNames.add("user");
            loginUser.setRoleNames(roleNames);
            
            // 设置权限
            Set<String> permissions = new HashSet<>();
            permissions.add("test:item:view");
            loginUser.setPermissions(permissions);
            
            // 生成token
            String token = tokenService.createToken(loginUser);
            loginUser.setToken(token);
            
            // 设置过期时间
            Date expireTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000); // 30分钟后
            loginUser.setExpireTime(expireTime);
            
            return Result.ok(loginUser);
        } else if ("user2".equals(username) && "pass456".equals(password)) {
            // 编辑用户登录
            LoginUserVO loginUser = new LoginUserVO();
            loginUser.setUserId("3");
            loginUser.setUsername("user2");
            loginUser.setNickname("用户2");
            
            // 设置角色
            List<String> roleNames = new ArrayList<>();
            roleNames.add("editor");
            loginUser.setRoleNames(roleNames);

            // 设置权限
            Set<String> permissions = new HashSet<>();
            permissions.add("test:item:view");
            permissions.add("test:item:add");
            permissions.add("test:item:edit");
            loginUser.setPermissions(permissions);
            
            // 生成token
            String token = tokenService.createToken(loginUser);
            loginUser.setToken(token);
            
            // 设置过期时间
            Date expireTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000); // 30分钟后
            loginUser.setExpireTime(expireTime);
            
            return Result.ok(loginUser);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    /**
     * 分页查询项目
     *
     * @param current 当前页
     * @param size 每页大小
     * @param name 项目名称（可选）
     * @return 分页结果
     */
    @GetMapping("/page")
    @RequiresLogin
    public Result<Page<TestItemVO>> page(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String name) {
        Page<TestItemVO> page = new Page<>(current, size);
        Page<TestItemVO> result = testItemService.page(page, name);
        return Result.ok(result);
    }

    /**
     * 根据ID获取项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    @GetMapping("/{id}")
    @RequiresLogin
    public Result<TestItemVO> getById(@PathVariable Integer id) {
        TestItemVO testItem = testItemService.getById(id);
        if (testItem == null) {
            return Result.error("项目不存在");
        }
        return Result.ok(testItem);
    }

    /**
     * 新增项目
     *
     * @param dto 项目信息
     * @return 新增后的项目
     */
    @PostMapping
    @RequiresLogin
    @RequiresPermission("test:item:add")
    public Result<TestItemVO> add(@Validated @RequestBody TestItemDTO dto) {
        TestItemVO testItem = testItemService.add(dto);
        return Result.ok(testItem);
    }

    /**
     * 修改项目
     *
     * @param dto 项目信息
     * @return 修改后的项目
     */
    @PutMapping
    @RequiresLogin
    @RequiresPermission("test:item:edit")
    public Result<TestItemVO> update(@Validated @RequestBody TestItemDTO dto) {
        if (dto.getId() == null) {
            return Result.error("项目ID不能为空");
        }
        try {
            TestItemVO testItem = testItemService.update(dto);
            return Result.ok(testItem);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除项目
     *
     * @param id 项目ID
     * @return 结果
     */
    @DeleteMapping("/{id}")
    @RequiresLogin
    @RequiresPermission("test:item:delete")
    public Result<Boolean> delete(@PathVariable Integer id) {
        try {
            boolean result = testItemService.delete(id);
            return Result.ok(result);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 管理员批量删除项目
     * 需要管理员角色
     *
     * @param ids 项目ID数组
     * @return 结果
     */
    @DeleteMapping("/batch")
    @RequiresLogin
    @RequiresRole("admin")
    public Result<Boolean> batchDelete(@RequestBody Integer[] ids) {
        if (ids == null || ids.length == 0) {
            return Result.error("项目ID不能为空");
        }
        
        boolean result = true;
        for (Integer id : ids) {
            try {
                result = result && testItemService.delete(id);
            } catch (RuntimeException e) {
                // 继续处理其他项目
            }
        }
        
        return Result.ok(result);
    }
    
    /**
     * 获取系统管理信息
     * 需要管理员角色
     *
     * @return 系统管理信息
     */
    @GetMapping("/admin-info")
    @RequiresLogin
    @RequiresRole("admin")
    public Result<Map<String, Object>> getAdminInfo() {
        Map<String, Object> adminInfo = new HashMap<>();
        adminInfo.put("totalItems", 100);  // 示例数据，实际应从服务层获取
        adminInfo.put("activeUsers", 25);
        adminInfo.put("systemStatus", "正常运行");
        adminInfo.put("lastBackupTime", new Date());
        adminInfo.put("cpuUsage", "32%");
        adminInfo.put("memoryUsage", "1.8GB/4GB");
        adminInfo.put("diskUsage", "120GB/500GB");
        
        return Result.ok(adminInfo);
    }
    
    /**
     * 仅编辑者可见的接口
     * 需要editor角色
     *
     * @return 编辑者专用信息
     */
    @GetMapping("/editor-actions")
    @RequiresLogin
    @RequiresRole("editor")
    public Result<Map<String, Object>> getEditorActions() {
        Map<String, Object> editorActions = new HashMap<>();
        editorActions.put("drafts", 5);
        editorActions.put("pendingReviews", 3);
        editorActions.put("availableTemplates", 10);
        editorActions.put("lastEditTime", new Date());
        
        return Result.ok(editorActions);
    }


} 