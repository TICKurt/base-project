package com.example.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.dto.SysRoleMenuDTO;
import com.example.auth.entity.SysRoleMenu;
import com.example.auth.mapper.SysMenuMapper;
import com.example.auth.mapper.SysRoleMenuMapper;
import com.example.auth.service.SysRoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色菜单关联服务实现类
 *
 * @author 作者
 * @date 创建时间
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<String> getRoleMenuIds(String roleId) {
        return baseMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoleMenus(SysRoleMenuDTO dto) {
        String roleId = dto.getRoleId();
        List<String> menuIds = dto.getMenuIds();
        
        // 删除原有的角色菜单关联
        LambdaQueryWrapper<SysRoleMenu> wrapper = Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getRoleId, roleId);
        remove(wrapper);
        
        // 如果未选择任何菜单，则直接返回
        if (CollUtil.isEmpty(menuIds)) {
            return;
        }
        
        // 批量新增角色菜单关联
        List<SysRoleMenu> roleMenuList = new ArrayList<>(menuIds.size());
        for (String menuId : menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        }
        
        // 使用自定义批量插入方法
        baseMapper.batchInsertList(roleMenuList);
    }

    @Override
    public List<String> getPermissionsByRoleIds(List<String> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        
        // 查询角色关联的所有菜单权限标识
        return baseMapper.selectPermissionsByRoleIds(roleIds);
    }

    @Override
    public boolean hasPermission(String roleId, String permission) {
        if (StringUtils.isBlank(roleId) || StringUtils.isBlank(permission)) {
            return false;
        }
        
        // 查询角色关联的所有菜单权限标识
        List<String> roleIds = new ArrayList<>();
        roleIds.add(roleId);
        List<String> permissions = baseMapper.selectPermissionsByRoleIds(roleIds);
        
        // 判断是否包含指定权限
        return permissions.contains(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByRoleId(String roleId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getRoleId, roleId);
        remove(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByRoleIds(List<String> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return;
        }
        
        LambdaQueryWrapper<SysRoleMenu> wrapper = Wrappers.<SysRoleMenu>lambdaQuery()
                .in(SysRoleMenu::getRoleId, roleIds);
        remove(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByMenuId(String menuId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getMenuId, menuId);
        remove(wrapper);
    }

    @Override
    public int countRoleMenuByMenuId(String menuId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getMenuId, menuId);
        return Math.toIntExact(count(wrapper));
    }

    @Override
    public Set<String> getMenuPermissionsByUserId(String userId) {
        List<String> permissions = menuMapper.selectPermissionsByUserId(userId);
        return permissions.stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
    }
} 