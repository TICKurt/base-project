package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.entity.SysMenu;
import com.example.auth.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色菜单关联Mapper接口
 *
 * @author 作者
 * @date 创建时间
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据角色ID查询菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<String> selectMenuIdsByRoleId(String roleId);

    /**
     * 统计菜单被角色引用的次数
     *
     * @param menuId 菜单ID
     * @return 引用次数
     */
    int countRoleMenuByMenuId(String menuId);

    /**
     * 根据角色ID列表查询菜单权限标识
     *
     * @param roleIds 角色ID列表
     * @return 权限标识列表
     */
    List<String> selectPermissionsByRoleIds(@Param("roleIds") List<String> roleIds);

    /**
     * 批量插入角色菜单关联
     *
     * @param list 角色菜单关联列表
     * @return 影响行数
     */
    int batchInsertList(@Param("list") List<SysRoleMenu> list);
    
    /**
     * 批量插入角色菜单关联
     *
     * @param roleId   角色ID
     * @param menuIds  菜单ID列表
     * @param createBy 创建人
     * @param tenantId 租户ID
     * @return 影响行数
     */
    int batchInsert(@Param("roleId") String roleId, @Param("menuIds") List<String> menuIds, 
                    @Param("createBy") String createBy, @Param("tenantId") String tenantId);

    /**
     * 根据角色ID删除角色菜单关联
     *
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByRoleId(String roleId);

    /**
     * 根据菜单ID删除角色菜单关联
     *
     * @param menuId 菜单ID
     * @return 影响行数
     */
    int deleteByMenuId(String menuId);

    /**
     * 根据角色ID和菜单ID删除角色菜单关联
     *
     * @param roleId 角色ID
     * @param menuId 菜单ID
     * @return 影响行数
     */
    int deleteByRoleIdAndMenuId(@Param("roleId") String roleId, @Param("menuId") String menuId);

    /**
     * 根据角色ID列表批量删除角色菜单关联
     *
     * @param roleIds 角色ID列表
     * @return 影响行数
     */
    int deleteByRoleIds(@Param("roleIds") List<String> roleIds);

    /**
     * 获取角色已分配的菜单详情
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByRoleId(String roleId);

    /**
     * 统计角色的权限数量
     *
     * @param roleId 角色ID
     * @param tenantId 租户ID
     * @return 权限数量
     */
    int countPermissionByRoleId(@Param("roleId") String roleId, @Param("tenantId") String tenantId);
} 