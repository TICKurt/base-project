package com.example.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.example.auth.domain.dto.RoleDTO;
import com.example.auth.domain.dto.RoleQueryDTO;
import com.example.auth.domain.entity.SysRole;
import com.example.auth.domain.vo.RoleVO;
import com.example.auth.dto.SysRoleDTO;
import com.example.auth.dto.SysRoleMenuDTO;
import com.example.auth.dto.SysRoleOrgDTO;
import com.example.auth.vo.SysRoleVO;

import java.util.List;
import java.util.Map;

/**
 * 系统角色服务接口
 *
 * @author example
 * @date 2023-04-01
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色列表
     *
     * @param pageNum  页码
     * @param pageSize  每页大小
     * @param query 查询条件
     * @return 角色分页数据
     */
    PageInfo<RoleVO> getRolePage(Integer pageNum, Integer pageSize, RoleQueryDTO query);

    /**
     * 根据角色ID获取角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    RoleVO getRoleById(String roleId);

    /**
     * 创建角色
     *
     * @param roleDTO  角色信息
     * @param createBy 创建人
     * @return 角色ID
     */
    String createRole(RoleDTO roleDTO, String createBy);

    /**
     * 更新角色
     *
     * @param roleDTO  角色信息
     * @param updateBy 更新人
     * @return 是否成功
     */
    boolean updateRole(RoleDTO roleDTO, String updateBy);

    /**
     * 删除角色
     *
     * @param roleId   角色ID
     * @param operator 操作人
     * @return 是否成功
     */
    boolean deleteRole(String roleId, String operator);

    /**
     * 批量删除角色
     *
     * @param roleIds  角色ID列表
     * @param operator 操作人
     * @return 是否成功
     */
    boolean batchDeleteRoles(List<String> roleIds, String operator);

    /**
     * 更新角色状态
     *
     * @param roleId   角色ID
     * @param status   状态
     * @param operator 操作人
     * @return 是否成功
     */
    boolean updateStatus(String roleId, Integer status, String operator);

    /**
     *
     * 根据角色编码查询角色
     *
     * @param code     角色编码
     * @param tenantId 租户ID
     * @return 角色信息
     */
    SysRole getByCode(String code, String tenantId);

    /**
     * 根据用户ID获取角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleVO> getRolesByUserId(String userId);

    /**
     * 根据用户ID获取角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> getRoleCodesByUserId(String userId);

    /**
     * 检查角色数据是否合法（角色名称、角色编码唯一）
     *
     * @param roleDTO 角色信息
     * @return 是否合法
     */
    boolean checkRoleValid(RoleDTO roleDTO);

    /**
     * 分配角色菜单权限
     *
     * @param roleId   角色ID
     * @param menuIds  菜单ID列表
     * @param operator 操作人
     * @return 是否成功
     */
    boolean assignMenus(String roleId, List<String> menuIds, String operator);

    /**
     * 分配角色数据权限
     *
     * @param roleId     角色ID
     * @param dataScope  数据权限范围
     * @param orgIds     组织机构ID列表（当dataScope=5时使用）
     * @param operator   操作人
     * @return 是否成功
     */
    boolean assignDataScope(String roleId, Integer dataScope, List<String> orgIds, String operator);
    
    /**
     * 获取角色的菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<String> getRoleMenuIds(String roleId);
    
    /**
     * 获取角色的组织ID列表
     *
     * @param roleId 角色ID
     * @return 组织ID列表
     */
    List<String> getRoleOrgIds(String roleId);
    
    /**
     * 获取角色关联的用户数量
     *
     * @param roleId 角色ID
     * @return 用户数量
     */
    Integer getUserCountByRoleId(String roleId);

    /**
     * 根据条件查询角色列表
     *
     * @param dto 查询条件
     * @return 角色列表
     */
    List<SysRoleVO> getRoleList(SysRoleDTO dto);

    /**
     * 新增角色
     *
     * @param dto 角色信息
     */
    void addRole(SysRoleDTO dto);

    /**
     * 修改角色
     *
     * @param dto 角色信息
     */
    void updateRole(SysRoleDTO dto);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    void deleteRole(String roleId);

    /**
     * 批量删除角色
     *
     * @param roleIds 角色ID列表
     */
    void deleteRoles(List<String> roleIds);

    /**
     * 更新角色状态
     *
     * @param roleId 角色ID
     * @param status 状态（0正常 1停用）
     */
    void updateRoleStatus(String roleId, Integer status);

    /**
     * 校验角色名称是否唯一
     *
     * @param dto 角色信息
     * @return 结果
     */
    boolean checkRoleNameUnique(SysRoleDTO dto);

    /**
     * 校验角色编码是否唯一
     *
     * @param dto 角色信息
     * @return 结果
     */
    boolean checkRoleCodeUnique(SysRoleDTO dto);

    /**
     * 查询角色是否被分配
     *
     * @param roleId 角色ID
     * @return 结果
     */
    boolean isRoleAssigned(String roleId);

    /**
     * 获取角色选择框列表
     *
     * @return 角色选择框列表
     */
    List<Map<String, Object>> getRoleOptions();

    /**
     * 分配角色菜单权限
     *
     * @param dto 角色菜单权限信息
     */
    void assignRoleMenus(SysRoleMenuDTO dto);

    /**
     * 获取角色数据权限范围
     *
     * @param roleId 角色ID
     * @return 数据权限信息
     */
    Map<String, Object> getRoleDataScope(String roleId);

    /**
     * 更新角色数据权限
     *
     * @param dto 角色数据权限信息
     */
    void updateRoleDataScope(SysRoleOrgDTO dto);

    /**
     * 分配角色组织机构数据权限
     *
     * @param dto 角色组织机构数据权限信息
     */
    void assignRoleOrgs(SysRoleOrgDTO dto);

    /**
     * 获取用户的数据权限范围组织机构ID列表
     *
     * @param userId 用户ID
     * @return 组织机构ID列表
     */
    List<String> getUserDataScopeOrgIds(String userId);

    /**
     * 获取多个角色的权限标识
     *
     * @param roleIds 角色ID列表
     * @return 权限标识列表
     */
    List<String> getPermissionsByRoleIds(List<String> roleIds);

    /**
     * 检查角色是否有指定权限
     *
     * @param roleId 角色ID
     * @param permission 权限标识
     * @return 是否有权限
     */
    boolean hasPermission(String roleId, String permission);
} 