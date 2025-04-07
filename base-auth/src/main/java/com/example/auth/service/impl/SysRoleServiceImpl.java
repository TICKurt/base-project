package com.example.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.auth.domain.dto.RoleDTO;
import com.example.auth.domain.dto.RoleQueryDTO;
import com.example.auth.domain.entity.SysRole;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.domain.vo.RoleVO;
import com.example.auth.dto.SysRoleDTO;
import com.example.auth.dto.SysRoleMenuDTO;
import com.example.auth.dto.SysRoleOrgDTO;
import com.example.auth.mapper.SysRoleMapper;
import com.example.auth.mapper.SysRoleMenuMapper;
import com.example.auth.mapper.SysRoleOrgMapper;
import com.example.auth.mapper.SysUserRoleMapper;
import com.example.auth.service.AuthService;
import com.example.auth.service.SysRoleService;
import com.example.auth.vo.SysRoleVO;
import com.example.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统角色服务实现类
 *
 * @author example
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper roleMapper;
    private final SysRoleMenuMapper roleMenuMapper;
    private final SysRoleOrgMapper roleOrgMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final AuthService authService;

    /**
     * 获取当前租户ID
     */
    private String getCurrentTenantId() {
        LoginUserVO loginUser = authService.getLoginUser();
        return loginUser != null ? loginUser.getTenantId() : null;
    }

    /**
     * 获取当前用户ID
     */
    private String getCurrentUserId() {
        LoginUserVO loginUser = authService.getLoginUser();
        return loginUser != null ? loginUser.getUserId() : null;
    }

    /**
     * 获取当前用户所属组织ID
     */
    private String getCurrentUserOrgId() {
        LoginUserVO loginUser = authService.getLoginUser();
        return loginUser != null ? loginUser.getOrgId() : null;
    }

    @Override
    public PageInfo<RoleVO> getRolePage(Integer pageNum, Integer pageSize, RoleQueryDTO query) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 构建查询条件
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getName()), SysRole::getName, query.getName())
                .like(StringUtils.isNotBlank(query.getCode()), SysRole::getCode, query.getCode())
                .eq(query.getStatus() != null, SysRole::getStatus, query.getStatus())
                .eq(query.getRoleType() != null, SysRole::getRoleType, query.getRoleType())
                .eq(SysRole::getTenantId, query.getTenantId())
                .orderByAsc(SysRole::getSort);

        // 执行查询
        List<SysRole> roles = roleMapper.selectList(wrapper);
        
        // 转换为VO并返回分页信息
        List<RoleVO> roleVOs = roles.stream()
                .map(role -> BeanUtil.copyProperties(role, RoleVO.class))
                .collect(Collectors.toList());
        return new PageInfo<>(roleVOs);
    }

    @Override
    public RoleVO getRoleById(String roleId) {
        SysRole role = getById(roleId);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }
        return BeanUtil.copyProperties(role, RoleVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createRole(RoleDTO roleDTO, String createBy) {
        // 校验角色数据
        if (!checkRoleValid(roleDTO)) {
            throw new BusinessException("角色数据不合法");
        }

        // 构建角色实体
        SysRole role = BeanUtil.copyProperties(roleDTO, SysRole.class);
        role.setCreateBy(createBy);
        role.setCreateTime(new Date());

        // 保存角色
        save(role);
        
        // 处理菜单权限
        if (CollectionUtils.isNotEmpty(roleDTO.getMenuIds())) {
            assignMenus(role.getId(), roleDTO.getMenuIds(), createBy);
        }
        
        // 处理数据权限
        if (roleDTO.getDataScope() != null) {
            assignDataScope(role.getId(), roleDTO.getDataScope(), roleDTO.getOrgIds(), createBy);
        }
        
        return role.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleDTO roleDTO, String updateBy) {
        // 校验角色是否存在
        SysRole existingRole = getById(roleDTO.getId());
        if (existingRole == null) {
            throw new BusinessException("角色不存在");
        }

        // 校验角色数据
        if (!checkRoleValid(roleDTO)) {
            throw new BusinessException("角色数据不合法");
        }
        
        // 更新角色信息
        SysRole role = BeanUtil.copyProperties(roleDTO, SysRole.class);
        role.setUpdateBy(updateBy);
        role.setUpdateTime(new Date());
        updateById(role);
        
        // 更新菜单权限
        if (CollectionUtils.isNotEmpty(roleDTO.getMenuIds())) {
            assignMenus(role.getId(), roleDTO.getMenuIds(), updateBy);
        }
        
        // 更新数据权限
        if (roleDTO.getDataScope() != null) {
            assignDataScope(role.getId(), roleDTO.getDataScope(), roleDTO.getOrgIds(), updateBy);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRole(String roleId, String operator) {
        // 校验角色是否存在
        SysRole role = getById(roleId);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 校验是否为内置角色
        if (role.getIsBuiltIn()) {
            throw new BusinessException("内置角色不能删除");
        }

        // 校验是否有用户使用该角色
        if (getUserCountByRoleId(roleId) > 0) {
            throw new BusinessException("该角色已分配给用户，不能删除");
        }

        // 删除角色
        removeById(roleId);
        
        // 删除角色菜单关联
        roleMenuMapper.deleteByRoleId(roleId);
        
        // 删除角色组织关联
        roleOrgMapper.deleteByRoleId(roleId);
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteRoles(List<String> roleIds, String operator) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }
        
        // 批量删除角色
        for (String roleId : roleIds) {
                deleteRole(roleId, operator);
        }
        
        return true;
    }

    @Override
    public boolean updateStatus(String roleId, Integer status, String operator) {
        // 校验角色是否存在
        SysRole role = getById(roleId);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 更新状态
        role.setStatus(status);
        role.setUpdateBy(operator);
        role.setUpdateTime(new Date());

        return updateById(role);
    }

    @Override
    public SysRole getByCode(String code, String tenantId) {
        return roleMapper.selectByCode(code, tenantId);
    }

    @Override
    public List<RoleVO> getRolesByUserId(String userId) {
        List<SysRole> roles = roleMapper.selectByUserId(userId, getCurrentTenantId());
        return roles.stream()
                .map(role -> BeanUtil.copyProperties(role, RoleVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleCodesByUserId(String userId) {
        List<SysRole> roles = roleMapper.selectByUserId(userId, getCurrentTenantId());
        return roles.stream().map(SysRole::getCode).collect(Collectors.toList());
    }

    @Override
    public boolean checkRoleValid(RoleDTO roleDTO) {
        // 校验角色名称是否重复
        LambdaQueryWrapper<SysRole> nameWrapper = new LambdaQueryWrapper<>();
        nameWrapper.eq(SysRole::getName, roleDTO.getName())
                .eq(SysRole::getTenantId, getCurrentTenantId())
                .ne(StringUtils.isNotBlank(roleDTO.getId()), SysRole::getId, roleDTO.getId());
        if (count(nameWrapper) > 0) {
            throw new BusinessException("角色名称已存在");
        }

        // 校验角色编码是否重复
        LambdaQueryWrapper<SysRole> codeWrapper = new LambdaQueryWrapper<>();
        codeWrapper.eq(SysRole::getCode, roleDTO.getCode())
                .eq(SysRole::getTenantId, getCurrentTenantId())
                .ne(StringUtils.isNotBlank(roleDTO.getId()), SysRole::getId, roleDTO.getId());
        if (count(codeWrapper) > 0) {
            throw new BusinessException("角色编码已存在");
        }
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignMenus(String roleId, List<String> menuIds, String operator) {
        // 删除原有的角色菜单关联
        roleMenuMapper.deleteByRoleId(roleId);
        
        // 批量插入新的角色菜单关联
        if (CollectionUtils.isNotEmpty(menuIds)) {
            roleMenuMapper.batchInsert(roleId, menuIds, operator, getCurrentTenantId());
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignDataScope(String roleId, Integer dataScope, List<String> orgIds, String operator) {
        // 更新角色数据权限范围
        SysRole role = getById(roleId);
        role.setDataScope(dataScope);
        role.setUpdateBy(operator);
        role.setUpdateTime(new Date());
        updateById(role);

        // 删除原有的角色组织关联
            roleOrgMapper.deleteByRoleId(roleId);
            
        // 如果是自定义数据权限，则保存角色组织关联
        if (dataScope == 5 && CollectionUtils.isNotEmpty(orgIds)) {
            roleOrgMapper.batchInsert(roleId, orgIds, operator, getCurrentTenantId());
        }

        return true;
    }

    @Override
    public List<String> getRoleMenuIds(String roleId) {
        return roleMenuMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public List<String> getRoleOrgIds(String roleId) {
        return roleOrgMapper.selectOrgIdsByRoleId(roleId);
    }

    @Override
    public Integer getUserCountByRoleId(String roleId) {
        return userRoleMapper.countUserByRoleId(roleId);
    }

    @Override
    public List<SysRoleVO> getRoleList(SysRoleDTO dto) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(dto.getName()), SysRole::getName, dto.getName())
                .like(StringUtils.isNotBlank(dto.getCode()), SysRole::getCode, dto.getCode())
                .eq(dto.getStatus() != null, SysRole::getStatus, dto.getStatus())
                .eq(SysRole::getTenantId, getCurrentTenantId())
                .orderByAsc(SysRole::getSort);

        List<SysRole> roles = list(wrapper);
        return roles.stream()
                .map(role -> BeanUtil.copyProperties(role, SysRoleVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addRole(SysRoleDTO dto) {
        createRole(BeanUtil.copyProperties(dto, RoleDTO.class), dto.getCreateBy());
    }

    @Override
    public void updateRole(SysRoleDTO dto) {
        updateRole(BeanUtil.copyProperties(dto, RoleDTO.class), dto.getUpdateBy());
    }

    @Override
    public void deleteRole(String roleId) {
        deleteRole(roleId, getCurrentUserId());
    }

    @Override
    public void deleteRoles(List<String> roleIds) {
        batchDeleteRoles(roleIds, getCurrentUserId());
    }

    @Override
    public void updateRoleStatus(String roleId, Integer status) {
        updateStatus(roleId, status, getCurrentUserId());
    }

    @Override
    public boolean checkRoleNameUnique(SysRoleDTO dto) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getName, dto.getName())
                .eq(SysRole::getTenantId, getCurrentTenantId())
                .ne(StringUtils.isNotBlank(dto.getId()), SysRole::getId, dto.getId());
        return count(wrapper) == 0;
    }

    @Override
    public boolean checkRoleCodeUnique(SysRoleDTO dto) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getCode, dto.getCode())
                .eq(SysRole::getTenantId, getCurrentTenantId())
                .ne(StringUtils.isNotBlank(dto.getId()), SysRole::getId, dto.getId());
        return count(wrapper) == 0;
    }

    @Override
    public boolean isRoleAssigned(String roleId) {
        return getUserCountByRoleId(roleId) > 0;
    }

    @Override
    public List<Map<String, Object>> getRoleOptions() {
        List<SysRole> roles = list(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getStatus, 1)
                .eq(SysRole::getTenantId, getCurrentTenantId())
                .orderByAsc(SysRole::getSort));

        return roles.stream().map(role -> {
            Map<String, Object> option = new HashMap<>(2);
            option.put("value", role.getId());
            option.put("label", role.getName());
            return option;
        }).collect(Collectors.toList());
    }

    @Override
    public void assignRoleMenus(SysRoleMenuDTO dto) {
        assignMenus(dto.getRoleId(), dto.getMenuIds(), getCurrentUserId());
    }

    @Override
    public Map<String, Object> getRoleDataScope(String roleId) {
        SysRole role = getById(roleId);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        Map<String, Object> result = new HashMap<>(2);
        result.put("dataScope", role.getDataScope());
        if (role.getDataScope() == 5) {
            result.put("orgIds", getRoleOrgIds(roleId));
        }
        return result;
    }

    @Override
    public void updateRoleDataScope(SysRoleOrgDTO dto) {
        assignDataScope(dto.getRoleId(), dto.getDataScope(), dto.getOrgIds(), getCurrentUserId());
    }

    @Override
    public void assignRoleOrgs(SysRoleOrgDTO dto) {
        assignDataScope(dto.getRoleId(), 5, dto.getOrgIds(), getCurrentUserId());
    }

    @Override
    public List<String> getUserDataScopeOrgIds(String userId) {
        // 获取用户角色
        List<SysRole> roles = roleMapper.selectByUserId(userId, getCurrentTenantId());
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }

        // 获取数据权限最大的角色
        SysRole maxDataScopeRole = roles.stream()
                .min(Comparator.comparing(SysRole::getDataScope))
                .orElse(null);

        if (maxDataScopeRole == null) {
            return Collections.emptyList();
        }

        // 根据数据权限范围返回组织机构ID列表
        switch (maxDataScopeRole.getDataScope()) {
            case 1: // 全部数据
                return null;
            case 2: // 本部门及以下
                return roleOrgMapper.selectChildOrgIds(getCurrentUserOrgId());
            case 3: // 本部门
                return Collections.singletonList(getCurrentUserOrgId());
            case 4: // 仅本人
                return Collections.singletonList(getCurrentUserOrgId());
            case 5: // 自定义
                return getRoleOrgIds(maxDataScopeRole.getId());
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public List<String> getPermissionsByRoleIds(List<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return roleMenuMapper.selectPermissionsByRoleIds(roleIds);
    }

    @Override
    public boolean hasPermission(String roleId, String permission) {
        return roleMenuMapper.countPermissionByRoleId(roleId, permission) > 0L;
    }

} 