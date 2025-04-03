package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统角色 Mapper 接口
 *
 * @author example
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据角色编码查询角色
     *
     * @param code 角色编码
     * @param tenantId 租户ID
     * @return 角色信息
     */
    SysRole selectByCode(@Param("code") String code, @Param("tenantId") String tenantId);

    /**
     * 根据角色名称查询角色
     *
     * @param name 角色名称
     * @param tenantId 租户ID
     * @return 角色信息
     */
    SysRole selectByName(@Param("name") String name, @Param("tenantId") String tenantId);

    /**
     * 查询用户的角色列表
     *
     * @param userId 用户ID
     * @param tenantId 租户ID
     * @return 角色列表
     */
    List<SysRole> selectByUserId(@Param("userId") String userId, @Param("tenantId") String tenantId);

    /**
     * 批量查询角色信息
     *
     * @param roleIds 角色ID列表
     * @param tenantId 租户ID
     * @return 角色列表
     */
    List<SysRole> selectBatchIds(@Param("roleIds") List<String> roleIds, @Param("tenantId") String tenantId);
} 