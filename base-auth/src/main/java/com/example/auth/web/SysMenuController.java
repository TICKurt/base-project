package com.example.auth.web;

import com.example.auth.dto.SysMenuDTO;
import com.example.auth.service.SysMenuService;
import com.example.auth.vo.RouterVO;
import com.example.auth.vo.SysMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单权限控制器
 *
 * @author 作者
 * @date 创建时间
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:menu:list')")
    public ResponseEntity<List<SysMenuVO>> list(SysMenuDTO dto) {
        List<SysMenuVO> menus = menuService.getMenuList(dto);
        return ResponseEntity.ok(menus);
    }

    /**
     * 获取菜单树形结构
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:menu:list')")
    public ResponseEntity<List<SysMenuVO>> tree(SysMenuDTO dto) {
        List<SysMenuVO> tree = menuService.getMenuTree(dto);
        return ResponseEntity.ok(tree);
    }

    /**
     * 根据菜单ID获取详情
     */
    @GetMapping("/{menuId}")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public ResponseEntity<SysMenuVO> getInfo(@PathVariable String menuId) {
        SysMenuVO menu = menuService.getMenuById(menuId);
        return ResponseEntity.ok(menu);
    }

    /**
     * 新增菜单
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:menu:add')")
    public ResponseEntity<Void> add(@Valid @RequestBody SysMenuDTO dto) {
        menuService.addMenu(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改菜单
     */
    @PutMapping
    @PreAuthorize("hasAuthority('system:menu:edit')")
    public ResponseEntity<Void> update(@Valid @RequestBody SysMenuDTO dto) {
        menuService.updateMenu(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    @PreAuthorize("hasAuthority('system:menu:remove')")
    public ResponseEntity<Void> remove(@PathVariable String menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeSelect")
    public ResponseEntity<List<Map<String, Object>>> treeSelect(SysMenuDTO dto) {
        List<Map<String, Object>> treeSelect = menuService.getMenuTreeSelect(dto);
        return ResponseEntity.ok(treeSelect);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping("/roleMenuTreeSelect/{roleId}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public ResponseEntity<Map<String, Object>> roleMenuTreeSelect(@PathVariable String roleId) {
        Map<String, Object> roleMenuTree = menuService.getRoleMenuTreeSelect(roleId);
        return ResponseEntity.ok(roleMenuTree);
    }

    /**
     * 获取当前用户的菜单树
     */
    @GetMapping("/userMenuTree")
    public ResponseEntity<List<SysMenuVO>> getUserMenuTree() {
        // 这里需要从认证信息中获取当前登录用户的ID
        // 这里简化处理，实际应从SecurityContext中获取
        String userId = "currentUserId";
        List<SysMenuVO> menuTree = menuService.getMenuTreeByUserId(userId);
        return ResponseEntity.ok(menuTree);
    }

    /**
     * 获取当前用户的路由信息
     */
    @GetMapping("/getRouters")
    public ResponseEntity<List<RouterVO>> getRouters() {
        // 这里需要从认证信息中获取当前登录用户的ID
        // 这里简化处理，实际应从SecurityContext中获取
        String userId = "currentUserId";
        List<RouterVO> routers = menuService.getRoutersByUserId(userId);
        return ResponseEntity.ok(routers);
    }

    /**
     * 获取当前用户的权限标识集合
     */
    @GetMapping("/getPermissions")
    public ResponseEntity<Set<String>> getPermissions() {
        // 这里需要从认证信息中获取当前登录用户的ID
        // 这里简化处理，实际应从SecurityContext中获取
        String userId = "currentUserId";
        Set<String> permissions = menuService.getPermissionsByUserId(userId);
        return ResponseEntity.ok(permissions);
    }

    /**
     * 检查菜单名称是否唯一
     */
    @GetMapping("/checkMenuNameUnique")
    public ResponseEntity<Boolean> checkMenuNameUnique(SysMenuDTO dto) {
        boolean isUnique = menuService.checkMenuNameUnique(dto);
        return ResponseEntity.ok(isUnique);
    }

    /**
     * 获取所有按钮权限
     */
    @GetMapping("/buttons")
    @PreAuthorize("hasAuthority('system:menu:list')")
    public ResponseEntity<List<SysMenuVO>> getButtonPermissions() {
        List<SysMenuVO> buttons = menuService.getButtonPermissions();
        return ResponseEntity.ok(buttons);
    }

    /**
     * 获取所有父级菜单（非按钮）
     */
    @GetMapping("/parents")
    @PreAuthorize("hasAuthority('system:menu:list')")
    public ResponseEntity<List<SysMenuVO>> getParentMenus() {
        List<SysMenuVO> parents = menuService.getParentMenus();
        return ResponseEntity.ok(parents);
    }

    /**
     * 获取子菜单列表
     */
    @GetMapping("/children/{parentId}")
    @PreAuthorize("hasAuthority('system:menu:list')")
    public ResponseEntity<List<SysMenuVO>> getChildrenMenus(@PathVariable String parentId) {
        List<SysMenuVO> children = menuService.getChildrenMenus(parentId);
        return ResponseEntity.ok(children);
    }
} 