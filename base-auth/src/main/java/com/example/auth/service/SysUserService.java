package com.example.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.domain.dto.UserDTO;
import com.example.auth.domain.dto.UserQueryDTO;
import com.example.auth.domain.entity.SysUser;
import com.example.auth.domain.vo.UserVO;

import java.util.List;

/**
 * 系统用户服务接口
 *
 * @author example
 * @date 2023-04-01
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户列表
     *
     * @param page 页码
     * @param size 每页大小
     * @param query 查询条件
     * @return 用户分页数据
     */
    IPage<UserVO> getUserPage(Integer page, Integer size, UserQueryDTO query);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO getUserById(String userId);

    /**
     * 创建用户
     *
     * @param userDTO 用户信息
     * @param createBy 创建人
     * @return 用户ID
     */
    String createUser(UserDTO userDTO, String createBy);

    /**
     * 更新用户
     *
     * @param userDTO 用户信息
     * @param updateBy 更新人
     * @return 是否成功
     */
    boolean updateUser(UserDTO userDTO, String updateBy);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @param operator 操作人
     * @return 是否成功
     */
    boolean deleteUser(String userId, String operator);

    /**
     * 批量删除用户
     *
     * @param userIds 用户ID列表
     * @param operator 操作人
     * @return 是否成功
     */
    boolean batchDeleteUsers(List<String> userIds, String operator);

    /**
     * 重置用户密码
     *
     * @param userId 用户ID
     * @param newPassword 新密码
     * @param operator 操作人
     * @return 是否成功
     */
    boolean resetPassword(String userId, String newPassword, String operator);

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @param operator 操作人
     * @return 是否成功
     */
    boolean updateStatus(String userId, Integer status, String operator);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser getByUsername(String username);

    /**
     * 根据手机号查询用户
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    SysUser getByMobile(String mobile);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    SysUser getByEmail(String email);

    /**
     * 根据组织ID查询用户列表
     *
     * @param orgId 组织ID
     * @param includeChildOrg 是否包含子组织
     * @return 用户列表
     */
    List<UserVO> getUsersByOrgId(String orgId, boolean includeChildOrg);

    /**
     * 根据角色ID查询用户列表
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    List<UserVO> getUsersByRoleId(String roleId);

    /**
     * 获取用户角色列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<String> getUserRoleIds(String userId);

    /**
     * 分配用户角色
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @param operator 操作人
     * @return 是否成功
     */
    boolean assignRoles(String userId, List<String> roleIds, String operator);

    /**
     * 检查用户数据是否合法（用户名、手机号、邮箱唯一）
     *
     * @param userDTO 用户信息
     * @return 是否合法
     */
    boolean checkUserValid(UserDTO userDTO);

    /**
     * 查询用户数量
     *
     * @param query 查询条件
     * @return 用户数量
     */
    long countUsers(UserQueryDTO query);

    /**
     * 根据组织ID和查询条件获取用户列表
     *
     * @param dto 查询条件（包含组织ID）
     * @return 用户列表
     */
    List<UserVO> getUserListByOrg(UserDTO dto);

    /**
     * 统计组织下的用户数量
     *
     * @param orgId 组织ID
     * @return 用户数量
     */
    Integer countUserByOrgId(String orgId);

    /**
     * 获取未分配到指定组织的用户列表
     *
     * @param orgId 组织ID
     * @param dto 查询条件
     * @return 用户列表
     */
    List<UserVO> getUnassignedOrgUsers(String orgId, UserDTO dto);
} 