package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.dto.SysRoleMenuDTO;
import com.example.auth.entity.SysRoleMenu;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单关联服务接口
 *
 * @author 作者
 * @date 创建时间
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取角色菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<String> getRoleMenuIds(String roleId);

    /**
     * 分配角色菜单权限
     *
     * @param dto 角色菜单DTO
     */
    void assignRoleMenus(SysRoleMenuDTO dto);

    /**
     * 获取多个角色的权限标识
     *
     * @param roleIds 角色ID列表
     * @return 权限标识列表
     */
    List<String> getPermissionsByRoleIds(List<String> roleIds);

    /**
     * 判断角色是否拥有指定权限
     *
     * @param roleId     角色ID
     * @param permission 权限标识
     * @return 是否拥有权限
     */
    boolean hasPermission(String roleId, String permission);

    /**
     * 根据角色ID删除角色菜单关联
     *
     * @param roleId 角色ID
     */
    void deleteRoleMenusByRoleId(String roleId);

    /**
     * 根据角色ID列表批量删除角色菜单关联
     *
     * @param roleIds 角色ID列表
     */
    void deleteRoleMenusByRoleIds(List<String> roleIds);

    /**
     * 根据菜单ID删除角色菜单关联
     *
     * @param menuId 菜单ID
     */
    void deleteRoleMenusByMenuId(String menuId);

    /**
     * 统计菜单被角色引用次数
     *
     * @param menuId 菜单ID
     * @return 引用次数
     */
    int countRoleMenuByMenuId(String menuId);

    /**
     * 获取用户的菜单权限标识集合
     *
     * @param userId 用户ID
     * @return 权限标识集合
     */
    Set<String> getMenuPermissionsByUserId(String userId);
} 