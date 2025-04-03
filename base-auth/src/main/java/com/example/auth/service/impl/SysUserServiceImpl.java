package com.example.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.domain.dto.UserDTO;
import com.example.auth.domain.dto.UserQueryDTO;
import com.example.auth.domain.entity.SysUser;
import com.example.auth.domain.vo.UserVO;
import com.example.auth.mapper.SysUserMapper;
import com.example.auth.mapper.SysUserRoleMapper;
import com.example.auth.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户服务实现类
 *
 * @author example
 * @date 2023-04-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private  SysUserMapper userMapper;
    @Autowired
    private  SysUserRoleMapper userRoleMapper;

    @Override
    public IPage<UserVO> getUserPage(Integer page, Integer size, UserQueryDTO query) {
        Page<UserVO> pageParam = new Page<>(page, size);
        return userMapper.selectUserPage(pageParam, query);
    }

    @Override
    public UserVO getUserById(String userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createUser(UserDTO userDTO, String createBy) {
        // 校验用户数据合法性
        if (!checkUserValid(userDTO)) {
            throw new RuntimeException("用户数据校验失败，用户名、手机号或邮箱已存在");
        }

        // 创建用户实体
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);
        
        // 设置默认值
        if (user.getStatus() == null) {
            user.setStatus(1); // 默认正常状态
        }
        if (user.getGender() == null) {
            user.setGender(0); // 默认性别未知
        }
        if (user.getUserType() == null) {
            user.setUserType(2); // 默认普通用户
        }
        
        // 设置密码（加密）
        if (StrUtil.isBlank(userDTO.getPassword())) {
            // 默认密码
            user.setPassword("123456");
        }
        
        // 设置账号相关状态
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setPwdResetTime(new Date());

        // 设置创建人
        user.setCreateBy(createBy);
        user.setTenantId(userDTO.getTenantId());

        // 保存用户
        userMapper.insert(user);
        
        // 分配角色（如果有）
        if (CollUtil.isNotEmpty(userDTO.getRoleIds())) {
            assignRoles(user.getId(), userDTO.getRoleIds(), createBy);
        }
        
        // TODO: 记录用户创建日志
        
        // TODO: 处理用户关联的其他信息（如用户-组织关系）
        
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UserDTO userDTO, String updateBy) {
        if (StrUtil.isBlank(userDTO.getId())) {
            throw new RuntimeException("用户ID不能为空");
        }
        
        // 校验用户数据合法性
        if (!checkUserValid(userDTO)) {
            throw new RuntimeException("用户数据校验失败，用户名、手机号或邮箱已存在");
        }
        
        // 获取原用户信息
        SysUser oldUser = userMapper.selectById(userDTO.getId());
        if (oldUser == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 更新用户信息
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);
        
        // 密码不能通过此方法修改
        user.setPassword(null);
        
        user.setUpdateBy(updateBy);
        user.setUpdateTime(new Date());
        
        boolean result = userMapper.updateById(user) > 0;
        
        // 更新角色关联
        if (CollUtil.isNotEmpty(userDTO.getRoleIds())) {
            assignRoles(user.getId(), userDTO.getRoleIds(), updateBy);
        }
        
        // TODO: 记录用户更新日志
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(String userId, String operator) {
        if (StrUtil.isBlank(userId)) {
            throw new RuntimeException("用户ID不能为空");
        }
        
        // 检查是否为系统管理员
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }
        if (user.getUserType() != null && user.getUserType() == 0) {
            throw new RuntimeException("不能删除系统管理员账号");
        }
        
        // 删除用户角色关联
        userRoleMapper.deleteByUserId(userId);
        
        // TODO: 删除用户关联的其他信息（如用户-职位关系、用户-职级关系等）
        
        // 删除用户（逻辑删除）
        boolean result = userMapper.deleteById(userId) > 0;
        
        // TODO: 记录用户删除日志
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteUsers(List<String> userIds, String operator) {
        if (CollUtil.isEmpty(userIds)) {
            return false;
        }
        
        // 删除用户角色关联
        for (String userId : userIds) {
            userRoleMapper.deleteByUserId(userId);
            
            // TODO: 删除用户关联的其他信息（如用户-职位关系、用户-职级关系等）
        }
        
        // 批量删除用户（逻辑删除）
        boolean result = userMapper.deleteBatchIds(userIds) > 0;
        
        // TODO: 记录用户批量删除日志
        
        return result;
    }

    @Override
    public boolean resetPassword(String userId, String newPassword, String operator) {
        if (StrUtil.isBlank(userId) || StrUtil.isBlank(newPassword)) {
            throw new RuntimeException("用户ID和新密码不能为空");
        }
        
        SysUser user = new SysUser();
        user.setId(userId);
        user.setPassword(newPassword);
        user.setPwdResetTime(new Date());
        user.setUpdateBy(operator);
        user.setUpdateTime(new Date());
        
        boolean result = userMapper.updateById(user) > 0;
        
        // TODO: 记录密码重置日志
        
        // TODO: 实现密码历史记录，防止重复使用历史密码
        
        return result;
    }

    @Override
    public boolean updateStatus(String userId, Integer status, String operator) {
        if (StrUtil.isBlank(userId) || status == null) {
            throw new RuntimeException("用户ID和状态不能为空");
        }
        
        SysUser user = new SysUser();
        user.setId(userId);
        user.setStatus(status);
        user.setUpdateBy(operator);
        user.setUpdateTime(new Date());
        
        // 如果状态为锁定，更新账号锁定状态
        if (status == 2) {
            user.setAccountNonLocked(false);
        } else if (status == 1) {
            user.setAccountNonLocked(true);
        }
        
        boolean result = userMapper.updateById(user) > 0;
        
        // TODO: 记录用户状态变更日志
        
        return result;
    }

    @Override
    public SysUser getByUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return null;
        }
        return userMapper.selectByUsername(username);
    }

    @Override
    public SysUser getByMobile(String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return null;
        }
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public SysUser getByEmail(String email) {
        if (StrUtil.isBlank(email)) {
            return null;
        }
        return userMapper.selectByEmail(email);
    }

    @Override
    public List<UserVO> getUsersByOrgId(String orgId, boolean includeChildOrg) {
        if (StrUtil.isBlank(orgId)) {
            return new ArrayList<>();
        }
        return userMapper.selectUsersByOrgId(orgId, includeChildOrg);
    }

    @Override
    public List<UserVO> getUsersByRoleId(String roleId) {
        if (StrUtil.isBlank(roleId)) {
            return new ArrayList<>();
        }
        return userMapper.selectUsersByRoleId(roleId);
    }

    @Override
    public List<String> getUserRoleIds(String userId) {
        if (StrUtil.isBlank(userId)) {
            return new ArrayList<>();
        }
        return userMapper.selectUserRoleIds(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignRoles(String userId, List<String> roleIds, String operator) {
        if (StrUtil.isBlank(userId) || CollUtil.isEmpty(roleIds)) {
            return false;
        }
        
        // 先删除旧的角色关联
        userRoleMapper.deleteByUserId(userId);
        
        // 批量插入新的角色关联
        String tenantId = getById(userId).getTenantId();
        boolean result = userRoleMapper.batchInsert(userId, roleIds, operator, tenantId) > 0;
        
        // TODO: 记录角色分配日志
        
        return result;
    }

    @Override
    public boolean checkUserValid(UserDTO userDTO) {
        if (userDTO == null) {
            return false;
        }
        
        // 用户名唯一性校验
        if (StrUtil.isNotBlank(userDTO.getUsername())) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getUsername, userDTO.getUsername())
                   .ne(StrUtil.isNotBlank(userDTO.getId()), SysUser::getId, userDTO.getId());
            
            if (userMapper.selectCount(wrapper) > 0) {
                log.warn("用户名已存在: {}", userDTO.getUsername());
                return false;
            }
        }
        
        // 手机号唯一性校验（如果有）
        if (StrUtil.isNotBlank(userDTO.getMobile())) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getMobile, userDTO.getMobile())
                   .ne(StrUtil.isNotBlank(userDTO.getId()), SysUser::getId, userDTO.getId());
            
            if (userMapper.selectCount(wrapper) > 0) {
                log.warn("手机号已存在: {}", userDTO.getMobile());
                return false;
            }
        }
        
        // 邮箱唯一性校验（如果有）
        if (StrUtil.isNotBlank(userDTO.getEmail())) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getEmail, userDTO.getEmail())
                   .ne(StrUtil.isNotBlank(userDTO.getId()), SysUser::getId, userDTO.getId());
            
            if (userMapper.selectCount(wrapper) > 0) {
                log.warn("邮箱已存在: {}", userDTO.getEmail());
                return false;
            }
        }
        
        // TODO: 实现更复杂的用户数据校验（如密码强度检查）
        
        return true;
    }

    @Override
    public long countUsers(UserQueryDTO query) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        
        // 设置查询条件
        wrapper.eq(StrUtil.isNotBlank(query.getUsername()), SysUser::getUsername, query.getUsername())
               .eq(StrUtil.isNotBlank(query.getOrgId()), SysUser::getOrgId, query.getOrgId())
               .eq(ObjectUtil.isNotNull(query.getStatus()), SysUser::getStatus, query.getStatus())
               .eq(ObjectUtil.isNotNull(query.getUserType()), SysUser::getUserType, query.getUserType())
               .eq(StrUtil.isNotBlank(query.getTenantId()), SysUser::getTenantId, query.getTenantId())
               .like(StrUtil.isNotBlank(query.getRealName()), SysUser::getRealName, query.getRealName())
               .like(StrUtil.isNotBlank(query.getMobile()), SysUser::getMobile, query.getMobile())
               .like(StrUtil.isNotBlank(query.getEmail()), SysUser::getEmail, query.getEmail())
               .ge(ObjectUtil.isNotNull(query.getBeginTime()), SysUser::getCreateTime, query.getBeginTime())
               .le(ObjectUtil.isNotNull(query.getEndTime()), SysUser::getCreateTime, query.getEndTime())
               .notIn(CollUtil.isNotEmpty(query.getExcludeUserIds()), SysUser::getId, query.getExcludeUserIds());
        
        return userMapper.selectCount(wrapper);
    }
    
    // TODO: 实现用户导入功能
    
    // TODO: 实现用户导出功能
    
    // TODO: 实现用户查询缓存，提高查询性能
    
    // TODO: 实现按部门统计用户数量功能
    
    // TODO: 实现用户登录次数统计功能

    @Override
    public List<UserVO> getUserListByOrg(UserDTO dto) {
        if (dto == null || !StringUtils.hasText(dto.getOrgId())) {
            return new ArrayList<>();
        }
        
        // 构建查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getOrgId, dto.getOrgId())
               .like(StringUtils.hasText(dto.getUsername()), SysUser::getUsername, dto.getUsername())
               .like(StringUtils.hasText(dto.getNickname()), SysUser::getNickname, dto.getNickname())
               .like(StringUtils.hasText(dto.getMobile()), SysUser::getMobile, dto.getMobile())
               .eq(dto.getStatus() != null, SysUser::getStatus, dto.getStatus())
               .orderByDesc(SysUser::getCreateTime);
        
        // 执行查询
        List<SysUser> users = list(wrapper);
        
        // 转换为VO对象
        return users.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public Integer countUserByOrgId(String orgId) {
        if (!StringUtils.hasText(orgId)) {
            return 0;
        }
        
        // 构建查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getOrgId, orgId);
        
        // 执行查询
        return Math.toIntExact(count(wrapper));
    }

    @Override
    public List<UserVO> getUnassignedOrgUsers(String orgId, UserDTO dto) {
        if (!StringUtils.hasText(orgId)) {
            return new ArrayList<>();
        }
        
        // 构建查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(SysUser::getOrgId, orgId)  // 不属于该组织的用户
               .or()
               .isNull(SysUser::getOrgId)     // 或者未分配组织的用户
               .like(StringUtils.hasText(dto.getUsername()), SysUser::getUsername, dto.getUsername())
               .like(StringUtils.hasText(dto.getNickname()), SysUser::getNickname, dto.getNickname())
               .like(StringUtils.hasText(dto.getMobile()), SysUser::getMobile, dto.getMobile())
               .eq(dto.getStatus() != null, SysUser::getStatus, dto.getStatus())
               .orderByDesc(SysUser::getCreateTime);
        
        // 执行查询
        List<SysUser> users = list(wrapper);
        
        // 转换为VO对象
        return users.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 将用户实体转换为VO对象
     */
    private UserVO convertToVO(SysUser user) {
        if (user == null) {
            return null;
        }
        
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        
        // 设置用户状态名称
        if (user.getStatus() != null) {
            switch (user.getStatus()) {
                case 0:
                    vo.setStatusName("禁用");
                    break;
                case 1:
                    vo.setStatusName("正常");
                    break;
                case 2:
                    vo.setStatusName("锁定");
                    break;
                default:
                    vo.setStatusName("未知");
            }
        }
        
        // 设置用户类型名称
        if (user.getUserType() != null) {
            switch (user.getUserType()) {
                case 0:
                    vo.setUserTypeName("超级管理员");
                    break;
                case 1:
                    vo.setUserTypeName("管理员");
                    break;
                case 2:
                    vo.setUserTypeName("普通用户");
                    break;
                default:
                    vo.setUserTypeName("未知");
            }
        }
        
        // 设置性别名称
        if (user.getGender() != null) {
            switch (user.getGender()) {
                case 0:
                    vo.setGenderName("未知");
                    break;
                case 1:
                    vo.setGenderName("男");
                    break;
                case 2:
                    vo.setGenderName("女");
                    break;
                default:
                    vo.setGenderName("未知");
            }
        }
        
        return vo;
    }
} 