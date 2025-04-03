package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单Mapper接口
 *
 * @author 作者
 * @date 创建时间
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByUserId(String userId);

    /**
     * 根据用户ID查询权限标识
     *
     * @param userId 用户ID
     * @return 权限标识列表
     */
    List<String> selectPermissionsByUserId(String userId);

    /**
     * 根据角色ID查询菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<String> selectMenuIdsByRoleId(String roleId);

    /**
     * 查询菜单是否有子菜单
     *
     * @param menuId 菜单ID
     * @return 子菜单数量
     */
    int hasChildByMenuId(String menuId);

    /**
     * 查询所有菜单信息
     *
     * @param name   菜单名称（可选）
     * @param status 菜单状态（可选）
     * @param type   菜单类型（可选）
     * @return 菜单列表
     */
    List<SysMenu> selectAllMenus(@Param("name") String name, @Param("status") Integer status, @Param("type") Integer type);

    /**
     * 根据父菜单ID查询子菜单
     *
     * @param parentId 父菜单ID
     * @return 子菜单列表
     */
    List<SysMenu> selectChildrenByParentId(String parentId);

    /**
     * 根据菜单类型查询菜单
     *
     * @param type 菜单类型
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByType(Integer type);

    /**
     * 根据多个角色ID查询菜单权限标识
     *
     * @param roleIds 角色ID列表
     * @return 菜单权限标识列表
     */
    List<String> selectPermissionsByRoleIds(@Param("roleIds") List<String> roleIds);
} 