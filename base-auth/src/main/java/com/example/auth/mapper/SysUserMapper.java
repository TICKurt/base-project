package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.auth.domain.dto.UserQueryDTO;
import com.example.auth.domain.entity.SysUser;
import com.example.auth.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户数据访问接口
 *
 * @author example
 * @date 2023-04-01
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询用户列表
     *
     * @param page 分页参数
     * @param query 查询条件
     * @return 用户分页数据
     */
    IPage<UserVO> selectUserPage(Page<UserVO> page, @Param("query") UserQueryDTO query);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO selectUserById(@Param("userId") String userId);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser selectByUsername(@Param("username") String username);

    /**
     * 根据手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    SysUser selectByMobile(@Param("mobile") String mobile);

    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 用户信息
     */
    SysUser selectByEmail(@Param("email") String email);

    /**
     * 根据组织ID查询用户列表
     *
     * @param orgId 组织ID
     * @param includeChildOrg 是否包含子组织
     * @return 用户列表
     */
    List<UserVO> selectUsersByOrgId(@Param("orgId") String orgId, @Param("includeChildOrg") Boolean includeChildOrg);

    /**
     * 根据角色ID查询用户列表
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    List<UserVO> selectUsersByRoleId(@Param("roleId") String roleId);
    
    /**
     * 获取指定用户的角色ID列表
     * 
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<String> selectUserRoleIds(@Param("userId") String userId);
} 