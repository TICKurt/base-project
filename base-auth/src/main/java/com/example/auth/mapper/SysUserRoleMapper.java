package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.domain.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联数据访问接口
 *
 * @author example
 * @date 2023-04-01
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 批量新增用户角色关联
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @param createBy 创建人
     * @param tenantId 租户ID
     * @return 插入条数
     */
    int batchInsert(@Param("userId") String userId, @Param("roleIds") List<String> roleIds,
                    @Param("createBy") String createBy, @Param("tenantId") String tenantId);

    /**
     * 根据用户ID删除用户角色关联
     *
     * @param userId 用户ID
     * @return 删除条数
     */
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 根据角色ID删除用户角色关联
     *
     * @param roleId 角色ID
     * @return 删除条数
     */
    int deleteByRoleId(@Param("roleId") String roleId);

    /**
     * 检查用户是否有指定角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 存在条数
     */
    int checkUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    /**
     * 获取用户角色列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<String> selectRoleIdsByUserId(@Param("userId") String userId);

    /**
     * 获取角色的用户列表
     *
     * @param roleId 角色ID
     * @return 用户ID列表
     */
    List<String> selectUserIdsByRoleId(@Param("roleId") String roleId);

    /**
     * 统计角色关联的用户数量
     *
     * @param roleId 角色ID
     * @return 用户数量
     */
    Integer countUserByRoleId(@Param("roleId") String roleId);
} 