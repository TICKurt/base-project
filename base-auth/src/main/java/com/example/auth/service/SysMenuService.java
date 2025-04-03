package com.example.auth.service;

import com.example.auth.dto.SysMenuDTO;
import com.example.auth.entity.SysMenu;
import com.example.auth.vo.SysMenuVO;
import com.example.auth.vo.RouterVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单服务接口
 *
 * @author 作者
 * @date 创建时间
 */
public interface SysMenuService {

    /**
     * 查询菜单列表
     *
     * @param dto 查询参数
     * @return 菜单列表
     */
    List<SysMenuVO> getMenuList(SysMenuDTO dto);

    /**
     * 查询菜单树形结构
     *
     * @param dto 查询参数
     * @return 菜单树
     */
    List<SysMenuVO> getMenuTree(SysMenuDTO dto);

    /**
     * 根据用户ID查询菜单树形结构
     *
     * @param userId 用户ID
     * @return 菜单树
     */
    List<SysMenuVO> getMenuTreeByUserId(String userId);

    /**
     * 根据ID查询菜单详情
     *
     * @param menuId 菜单ID
     * @return 菜单详情
     */
    SysMenuVO getMenuById(String menuId);

    /**
     * 新增菜单
     *
     * @param dto 菜单信息
     * @return 结果
     */
    void addMenu(SysMenuDTO dto);

    /**
     * 修改菜单
     *
     * @param dto 菜单信息
     * @return 结果
     */
    void updateMenu(SysMenuDTO dto);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    void deleteMenu(String menuId);

    /**
     * 查询菜单下拉树结构
     *
     * @param dto 查询参数
     * @return 下拉树结构
     */
    List<Map<String, Object>> getMenuTreeSelect(SysMenuDTO dto);

    /**
     * 根据角色ID查询对应的菜单树结构
     *
     * @param roleId 角色ID
     * @return 菜单树和已选中的菜单ID
     */
    Map<String, Object> getRoleMenuTreeSelect(String roleId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param dto 菜单信息
     * @return 结果
     */
    boolean checkMenuNameUnique(SysMenuDTO dto);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean hasChildByMenuId(String menuId);

    /**
     * 根据用户ID查询权限集合
     *
     * @param userId 用户ID
     * @return 权限集合
     */
    Set<String> getPermissionsByUserId(String userId);

    /**
     * 根据用户ID查询路由信息
     *
     * @param userId 用户ID
     * @return 路由信息
     */
    List<RouterVO> getRoutersByUserId(String userId);

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int countMenuUsage(String menuId);

    /**
     * 查询所有按钮权限列表
     *
     * @return 按钮权限列表
     */
    List<SysMenuVO> getButtonPermissions();

    /**
     * 查询父菜单列表
     *
     * @return 父菜单列表（不包含按钮）
     */
    List<SysMenuVO> getParentMenus();

    /**
     * 根据父级ID查询子菜单列表
     *
     * @param parentId 父菜单ID
     * @return 子菜单列表
     */
    List<SysMenuVO> getChildrenMenus(String parentId);
} 