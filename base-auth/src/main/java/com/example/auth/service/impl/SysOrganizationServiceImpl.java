package com.example.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.dto.OrganizationDTO;
import com.example.auth.dto.OrganizationQueryDTO;
import com.example.auth.dto.SysOrganizationDTO;
import com.example.auth.dto.SysOrgRelationDTO;
import com.example.auth.entity.SysOrganization;
import com.example.auth.entity.SysOrganizationExt;
import com.example.auth.entity.SysOrgRelation;
import com.example.auth.mapper.SysOrganizationExtMapper;
import com.example.auth.mapper.SysOrganizationMapper;
import com.example.auth.mapper.SysOrgRelationMapper;
import com.example.auth.service.AuthService;
import com.example.auth.service.SysOrganizationService;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.vo.OrganizationTreeVO;
import com.example.auth.vo.OrganizationVO;
import com.example.auth.vo.SysOrganizationVO;
import com.example.auth.vo.SysOrgRelationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 组织机构Service实现类
 *
 * @author 作者
 * @date 创建时间
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOrganizationServiceImpl extends ServiceImpl<SysOrganizationMapper, SysOrganization> implements SysOrganizationService {

    @Autowired
    private  SysOrganizationMapper organizationMapper;
    @Autowired
    private  SysOrganizationExtMapper organizationExtMapper;
    @Autowired
    private  SysOrgRelationMapper orgRelationMapper;
    @Autowired
    private  AuthService authService;

    @Override
    public IPage<OrganizationVO> pageOrganization(OrganizationQueryDTO queryDTO) {
        // 转换查询条件
        SysOrganization organization = new SysOrganization();
        if (queryDTO != null) {
            organization.setName(queryDTO.getName());
            organization.setCode(queryDTO.getCode());
            organization.setOrgType(queryDTO.getOrgType());
            organization.setStatus(queryDTO.getStatus());
            organization.setParentId(queryDTO.getParentId());
            organization.setLeaderName(queryDTO.getLeaderName());
            organization.setContactPhone(queryDTO.getContactPhone());
        }

        // 设置分页参数
        Page<OrganizationVO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        
        // 执行分页查询
        return organizationMapper.selectOrganizationPage(page, organization);
    }

    @Override
    public List<OrganizationVO> listOrganization(OrganizationQueryDTO queryDTO) {
        // 转换查询条件
        SysOrganization organization = new SysOrganization();
        if (queryDTO != null) {
            organization.setName(queryDTO.getName());
            organization.setCode(queryDTO.getCode());
            organization.setOrgType(queryDTO.getOrgType());
            organization.setStatus(queryDTO.getStatus());
            organization.setParentId(queryDTO.getParentId());
            organization.setLeaderName(queryDTO.getLeaderName());
            organization.setContactPhone(queryDTO.getContactPhone());
        }

        // 执行查询
        return organizationMapper.selectOrganizationList(organization);
    }

    @Override
    public OrganizationVO getOrganizationById(String id) {
        if (StrUtil.isEmpty(id)) {
            throw new RuntimeException("组织机构ID不能为空");
        }
        
        // 查询组织机构详情
        OrganizationVO vo = organizationMapper.selectOrganizationById(id);
        
        if (vo != null) {
            // 检查是否可以删除
            boolean canDelete = true;
            
            // 检查是否有子组织
            int childrenCount = organizationMapper.countChildrenByOrgId(id);
            if (childrenCount > 0) {
                canDelete = false;
            }
            
            // 检查是否有关联用户
            if (canDelete) {
                int userCount = organizationMapper.countUserByOrgId(id, false);
                if (userCount > 0) {
                    canDelete = false;
                }
            }
            
            vo.setCanDelete(canDelete);
        }
        
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrganizationVO createOrganization(OrganizationDTO dto) {
        // 验证数据
        validateOrganizationData(dto, false);
        
        // 获取当前登录用户ID
        LoginUserVO loginUser = authService.getLoginUser();
        String userId = loginUser.getUserId();
        String tenantId = loginUser.getTenantId();
        
        // 构建组织机构实体
        SysOrganization organization = new SysOrganization();
        BeanUtils.copyProperties(dto, organization);
        
        // 设置基本信息
        String id = UUID.randomUUID().toString().replace("-", "");
        organization.setId(id);
        organization.setCreateBy(userId);
        organization.setCreateTime(LocalDateTime.now());
        organization.setTenantId(tenantId);
        organization.setIsDeleted(0);
        
        // 处理父级关系
        handleParentRelation(organization);
        
        // 插入组织机构数据
        organizationMapper.insert(organization);
        
        // 构建扩展信息
        SysOrganizationExt ext = buildOrganizationExt(dto, id, userId, tenantId);
        
        // 插入扩展信息
        if (ext != null) {
            organizationExtMapper.insert(ext);
        }
        
        // 返回新增结果
        return getOrganizationById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrganizationVO updateOrganization(OrganizationDTO dto) {
        // 验证参数
        if (!StringUtils.hasText(dto.getId())) {
            throw new RuntimeException("组织机构ID不能为空");
        }
        
        // 验证数据
        validateOrganizationData(dto, true);
        
        // 查询原组织信息
        SysOrganization originalOrg = getById(dto.getId());
        if (originalOrg == null) {
            throw new RuntimeException("组织机构不存在");
        }
        
        // 获取当前登录用户ID
        LoginUserVO loginUser = authService.getLoginUser();
        String userId = loginUser.getUserId();
        
        // 构建组织机构实体
        SysOrganization organization = new SysOrganization();
        BeanUtils.copyProperties(dto, organization);
        
        // 设置更新信息
        organization.setUpdateBy(userId);
        organization.setUpdateTime(LocalDateTime.now());
        
        // 处理父级关系
        if (!Objects.equals(originalOrg.getParentId(), organization.getParentId())) {
            handleParentRelation(organization);
            
            // 更新其所有子节点的path
            updateChildrenPath(originalOrg.getId(), organization.getPath());
        }
        
        // 更新组织机构信息
        organizationMapper.updateById(organization);
        
        // 更新扩展信息
        SysOrganizationExt ext = buildOrganizationExt(dto, dto.getId(), userId, originalOrg.getTenantId());
        if (ext != null) {
            // 查询是否存在扩展信息
            SysOrganizationExt existExt = organizationExtMapper.selectById(dto.getId());
            if (existExt != null) {
                organizationExtMapper.updateById(ext);
            } else {
                organizationExtMapper.insert(ext);
            }
        }
        
        // 返回更新结果
        return getOrganizationById(dto.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrganization(String id) {
        // 验证参数
        if (!StringUtils.hasText(id)) {
            throw new RuntimeException("组织机构ID不能为空");
        }
        
        // 检查是否有子组织
        int childrenCount = organizationMapper.countChildrenByOrgId(id);
        if (childrenCount > 0) {
            throw new RuntimeException("该组织存在下级组织，不能删除");
        }
        
        // 检查是否有关联用户
        int userCount = organizationMapper.countUserByOrgId(id, false);
        if (userCount > 0) {
            throw new RuntimeException("该组织下存在用户，不能删除");
        }
        
        // 删除组织关系
        orgRelationMapper.deleteByOrgId(id);
        
        // 删除扩展信息
        organizationExtMapper.deleteById(id);
        
        // 逻辑删除组织
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteOrganization(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        for (String id : ids) {
            boolean success = deleteOrganization(id);
            if (!success) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public List<OrganizationTreeVO> getOrganizationTree(OrganizationQueryDTO queryDTO) {
        // 查询所有符合条件的组织
        List<OrganizationVO> allOrgs = listOrganization(queryDTO);
        
        // 转换为树形结构
        return buildOrganizationTree(allOrgs);
    }

    @Override
    public List<String> getChildrenIds(String orgId) {
        if (!StringUtils.hasText(orgId)) {
            return new ArrayList<>();
        }
        
        // 结果集合，包含自身
        List<String> ids = new ArrayList<>();
        ids.add(orgId);
        
        // 查询所有组织
        List<SysOrganization> allOrgs = list(new LambdaQueryWrapper<SysOrganization>()
                .eq(SysOrganization::getIsDeleted, 0));
        
        // 递归获取所有子节点ID
        getChildrenIdsRecursive(orgId, allOrgs, ids);
        
        return ids;
    }

    @Override
    public boolean checkCodeExists(String code, String id) {
        if (!StringUtils.hasText(code)) {
            return false;
        }
        
        LambdaQueryWrapper<SysOrganization> queryWrapper = new LambdaQueryWrapper<SysOrganization>()
                .eq(SysOrganization::getCode, code)
                .eq(SysOrganization::getIsDeleted, 0);
        
        // 更新时排除自身
        if (StringUtils.hasText(id)) {
            queryWrapper.ne(SysOrganization::getId, id);
        }
        
        return count(queryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(String id, Integer status) {
        if (!StringUtils.hasText(id)) {
            throw new RuntimeException("组织机构ID不能为空");
        }
        
        if (status == null || (status != 0 && status != 1)) {
            throw new RuntimeException("状态值不正确");
        }
        
        LoginUserVO loginUser = authService.getLoginUser();
        String userId = loginUser.getUserId();
        
        SysOrganization organization = new SysOrganization();
        organization.setId(id);
        organization.setStatus(status);
        organization.setUpdateBy(userId);
        organization.setUpdateTime(LocalDateTime.now());
        
        return updateById(organization);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setOrgRelation(String orgId, List<String> targetOrgIds, Integer relationType) {
        if (!StringUtils.hasText(orgId)) {
            throw new RuntimeException("组织机构ID不能为空");
        }
        
        if (relationType == null) {
            throw new RuntimeException("关系类型不能为空");
        }
        
        // 删除原有关系
        LambdaQueryWrapper<SysOrgRelation> queryWrapper = new LambdaQueryWrapper<SysOrgRelation>()
                .eq(SysOrgRelation::getOrgId, orgId)
                .eq(SysOrgRelation::getRelationType, relationType);
        orgRelationMapper.delete(queryWrapper);
        
        // 如果目标组织ID为空，则只删除不新增
        if (targetOrgIds == null || targetOrgIds.isEmpty()) {
            return true;
        }
        
        // 获取当前登录用户ID
        LoginUserVO loginUser = authService.getLoginUser();
        String userId = loginUser.getUserId();
        String tenantId = loginUser.getTenantId();
        
        // 批量新增关系
        List<SysOrgRelation> relations = new ArrayList<>();
        for (String targetOrgId : targetOrgIds) {
            if (orgId.equals(targetOrgId)) {
                continue; // 跳过自身
            }
            
            SysOrgRelation relation = new SysOrgRelation();
            relation.setId(UUID.randomUUID().toString().replace("-", ""));
            relation.setOrgId(orgId);
            relation.setTargetOrgId(targetOrgId);
            relation.setRelationType(relationType);
            relation.setCreateBy(userId);
            relation.setCreateTime(LocalDateTime.now());
            relation.setTenantId(tenantId);
            
            relations.add(relation);
        }
        
        if (!relations.isEmpty()) {
            orgRelationMapper.batchInsert(relations);
        }
        
        return true;
    }

    @Override
    public List<String> getRelatedOrgIds(String orgId, Integer relationType) {
        if (!StringUtils.hasText(orgId)) {
            return new ArrayList<>();
        }
        
        // 查询关系
        List<SysOrgRelation> relations = orgRelationMapper.selectByOrgIdAndType(orgId, relationType);
        
        // 提取目标组织ID
        return relations.stream()
                .map(SysOrgRelation::getTargetOrgId)
                .collect(Collectors.toList());
    }

    /**
     * 验证组织机构数据
     *
     * @param dto 组织机构DTO
     * @param isUpdate 是否是更新操作
     */
    private void validateOrganizationData(OrganizationDTO dto, boolean isUpdate) {
        // 检查名称
        if (!StringUtils.hasText(dto.getName())) {
            throw new RuntimeException("组织名称不能为空");
        }
        
        // 检查组织类型
        if (dto.getOrgType() == null) {
            throw new RuntimeException("组织类型不能为空");
        }
        
        // 检查组织编码是否已存在
        if (StringUtils.hasText(dto.getCode())) {
            boolean exists = checkCodeExists(dto.getCode(), isUpdate ? dto.getId() : null);
            if (exists) {
                throw new RuntimeException("组织编码已存在");
            }
        }
        
        // 检查父组织是否存在
        if (StringUtils.hasText(dto.getParentId())) {
            SysOrganization parentOrg = getById(dto.getParentId());
            if (parentOrg == null) {
                throw new RuntimeException("父级组织不存在");
            }
            
            // 更新时，检查父组织是否是自己的子组织
            if (isUpdate) {
                List<String> childrenIds = getChildrenIds(dto.getId());
                if (childrenIds.contains(dto.getParentId())) {
                    throw new RuntimeException("不能选择自己的子组织作为父级组织");
                }
            }
        }
    }

    /**
     * 处理父级关系
     *
     * @param organization 组织机构实体
     */
    private void handleParentRelation(SysOrganization organization) {
        if (StringUtils.hasText(organization.getParentId())) {
            // 查询父组织
            SysOrganization parentOrg = getById(organization.getParentId());
            if (parentOrg != null) {
                // 设置层级
                organization.setLevel(parentOrg.getLevel() + 1);
                
                // 设置路径
                String parentPath = parentOrg.getPath();
                if (!StringUtils.hasText(parentPath)) {
                    parentPath = "/";
                }
                organization.setPath(parentPath + parentOrg.getId() + "/");
            }
        } else {
            // 根组织
            organization.setParentId(null);
            organization.setLevel(1);
            organization.setPath("/");
        }
    }

    /**
     * 更新子节点路径
     *
     * @param parentId 父节点ID
     * @param newParentPath 新的父节点路径
     */
    private void updateChildrenPath(String parentId, String newParentPath) {
        // 查询所有子节点
        List<SysOrganization> children = organizationMapper.selectChildren(parentId);
        if (children != null && !children.isEmpty()) {
            for (SysOrganization child : children) {
                // 计算新路径
                String newPath = newParentPath + parentId + "/";
                
                // 更新路径
                SysOrganization updateOrg = new SysOrganization();
                updateOrg.setId(child.getId());
                updateOrg.setPath(newPath);
                updateOrg.setUpdateTime(LocalDateTime.now());
                organizationMapper.updateById(updateOrg);
                
                // 递归更新子节点
                updateChildrenPath(child.getId(), newPath);
            }
        }
    }

    /**
     * 构建组织机构扩展信息
     *
     * @param dto 组织机构DTO
     * @param id 组织机构ID
     * @param userId 当前用户ID
     * @param tenantId 租户ID
     * @return 扩展信息实体
     */
    private SysOrganizationExt buildOrganizationExt(OrganizationDTO dto, String id, String userId, String tenantId) {
        if (dto.getCreditCode() != null || dto.getLegalPerson() != null || dto.getBusinessLicense() != null
                || dto.getRegisteredCapital() != null || dto.getFoundedTime() != null || dto.getBusinessScope() != null
                || dto.getRegionId() != null || dto.getDetailedAddress() != null || dto.getEmail() != null
                || dto.getFax() != null || dto.getWebsite() != null || dto.getLogo() != null
                || dto.getParentRelationType() != null || dto.getOrgCategory() != null) {
            
            SysOrganizationExt ext = new SysOrganizationExt();
            ext.setId(id);
            ext.setCreditCode(dto.getCreditCode());
            ext.setLegalPerson(dto.getLegalPerson());
            ext.setBusinessLicense(dto.getBusinessLicense());
            ext.setRegisteredCapital(dto.getRegisteredCapital());
            ext.setFoundedTime(dto.getFoundedTime());
            ext.setBusinessScope(dto.getBusinessScope());
            ext.setRegionId(dto.getRegionId());
            ext.setDetailedAddress(dto.getDetailedAddress());
            ext.setEmail(dto.getEmail());
            ext.setFax(dto.getFax());
            ext.setWebsite(dto.getWebsite());
            ext.setLogo(dto.getLogo());
            ext.setParentRelationType(dto.getParentRelationType());
            ext.setOrgCategory(dto.getOrgCategory());
            ext.setCreateBy(userId);
            ext.setCreateTime(LocalDateTime.now());
            ext.setTenantId(tenantId);
            
            return ext;
        }
        
        return null;
    }

    /**
     * 构建组织机构树
     *
     * @param allOrgs 所有组织
     * @return 树形结构
     */
    private List<OrganizationTreeVO> buildOrganizationTree(List<OrganizationVO> allOrgs) {
        if (allOrgs == null || allOrgs.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 转换为树形VO
        List<OrganizationTreeVO> treeNodes = new ArrayList<>();
        Map<String, OrganizationTreeVO> nodeMap = new HashMap<>();
        
        // 先转换为树形节点并存入Map
        for (OrganizationVO org : allOrgs) {
            OrganizationTreeVO treeNode = new OrganizationTreeVO();
            treeNode.setId(org.getId());
            treeNode.setParentId(org.getParentId());
            treeNode.setLabel(org.getName());
            treeNode.setCode(org.getCode());
            treeNode.setOrgType(org.getOrgType());
            treeNode.setOrgTypeName(org.getOrgTypeName());
            treeNode.setLevel(org.getLevel());
            treeNode.setSort(org.getSort());
            treeNode.setLeaderName(org.getLeaderName());
            treeNode.setStatus(org.getStatus());
            treeNode.setDisabled(org.getStatus() != null && org.getStatus() == 0);
            treeNode.setHasChildren(org.getHasChildren() != null ? org.getHasChildren() : false);
            
            nodeMap.put(org.getId(), treeNode);
        }
        
        // 构建树形结构
        for (OrganizationVO org : allOrgs) {
            OrganizationTreeVO treeNode = nodeMap.get(org.getId());
            
            if (!StringUtils.hasText(org.getParentId())) {
                // 根节点
                treeNodes.add(treeNode);
            } else {
                // 添加到父节点
                OrganizationTreeVO parentNode = nodeMap.get(org.getParentId());
                if (parentNode != null) {
                    parentNode.getChildren().add(treeNode);
                    parentNode.setHasChildren(true);
                } else {
                    // 父节点不存在，作为根节点
                    treeNodes.add(treeNode);
                }
            }
        }
        
        // 按排序号和名称排序
        sortTreeNodes(treeNodes);
        
        return treeNodes;
    }

    /**
     * 递归排序树节点
     *
     * @param nodes 节点列表
     */
    private void sortTreeNodes(List<OrganizationTreeVO> nodes) {
        if (nodes != null && !nodes.isEmpty()) {
            // 排序当前级别节点
            nodes.sort(Comparator.comparing(OrganizationTreeVO::getSort)
                    .thenComparing(OrganizationTreeVO::getLabel));
            
            // 递归排序子节点
            for (OrganizationTreeVO node : nodes) {
                if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                    sortTreeNodes(node.getChildren());
                }
            }
        }
    }

    /**
     * 递归获取所有子节点ID
     *
     * @param parentId 父节点ID
     * @param allOrgs 所有组织
     * @param ids 结果ID列表
     */
    private void getChildrenIdsRecursive(String parentId, List<SysOrganization> allOrgs, List<String> ids) {
        for (SysOrganization org : allOrgs) {
            if (Objects.equals(parentId, org.getParentId())) {
                ids.add(org.getId());
                getChildrenIdsRecursive(org.getId(), allOrgs, ids);
            }
        }
    }

    @Override
    public List<SysOrganizationVO> getOrgList(SysOrganizationDTO dto) {
        // 构建查询条件
        LambdaQueryWrapper<SysOrganization> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOrganization::getIsDeleted, 0);
        
        // 添加查询条件
        if (dto != null) {
            // 按名称模糊查询
            if (StringUtils.hasText(dto.getNameLike())) {
                queryWrapper.like(SysOrganization::getName, dto.getNameLike());
            }
            
            // 按名称精确查询
            if (StringUtils.hasText(dto.getName())) {
                queryWrapper.eq(SysOrganization::getName, dto.getName());
            }
            
            // 按编码查询
            if (StringUtils.hasText(dto.getCode())) {
                queryWrapper.eq(SysOrganization::getCode, dto.getCode());
            }
            
            // 按状态查询
            if (dto.getStatusFilter() != null) {
                queryWrapper.eq(SysOrganization::getStatus, dto.getStatusFilter());
            }
            
            // 按组织类型查询
            if (dto.getOrgTypeFilter() != null) {
                queryWrapper.eq(SysOrganization::getOrgType, dto.getOrgTypeFilter());
            }
            
            // 按父级ID查询
            if (StringUtils.hasText(dto.getParentId())) {
                queryWrapper.eq(SysOrganization::getParentId, dto.getParentId());
            }
            
            // 只查询顶级组织
            if (dto.getOnlyRoot() != null && dto.getOnlyRoot()) {
                queryWrapper.isNull(SysOrganization::getParentId);
            }
        }
        
        // 按排序号和名称排序
        queryWrapper.orderByAsc(SysOrganization::getSort).orderByAsc(SysOrganization::getName);
        
        // 查询组织机构列表
        List<SysOrganization> orgList = list(queryWrapper);
        
        // 转换为VO对象
        return convertToOrgVOList(orgList, dto != null && dto.getWithCalcFields() != null && dto.getWithCalcFields());
    }

    @Override
    public List<SysOrganizationVO> getOrgTree(SysOrganizationDTO dto) {
        // 查询所有符合条件的组织
        List<SysOrganizationVO> allOrgs = getOrgList(dto);
        
        // 构建树形结构
        if (dto != null && dto.getIncludeChildren() != null && dto.getIncludeChildren()) {
            return buildSysOrgTree(allOrgs);
        } else {
            return allOrgs;
        }
    }

    @Override
    public SysOrganizationVO getOrgById(String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        
        // 查询组织基本信息
        SysOrganization org = getById(id);
        if (org == null) {
            return null;
        }
        
        // 查询组织扩展信息
        SysOrganizationExt orgExt = organizationExtMapper.selectById(id);
        
        // 查询父组织名称
        String parentName = null;
        if (StringUtils.hasText(org.getParentId())) {
            SysOrganization parentOrg = getById(org.getParentId());
            if (parentOrg != null) {
                parentName = parentOrg.getName();
            }
        }
        
        // 转换为VO对象
        SysOrganizationVO vo = new SysOrganizationVO();
        BeanUtils.copyProperties(org, vo);
        
        // 设置父组织名称
        vo.setParentName(parentName);
        
        // 设置状态名称
        if (org.getStatus() != null) {
            vo.setStatusName(org.getStatus() == 0 ? "禁用" : "正常");
        }
        
        // 设置组织类型名称
        if (org.getOrgType() != null) {
            switch (org.getOrgType()) {
                case 1:
                    vo.setOrgTypeName("公司");
                    break;
                case 2:
                    vo.setOrgTypeName("部门");
                    break;
                case 3:
                    vo.setOrgTypeName("团队");
                    break;
                case 4:
                    vo.setOrgTypeName("小组");
                    break;
                default:
                    vo.setOrgTypeName("未知");
            }
        }
        
        // 设置扩展信息
        if (orgExt != null) {
            BeanUtils.copyProperties(orgExt, vo);
            
            // 设置上级关系类型名称
            if (orgExt.getParentRelationType() != null) {
                switch (orgExt.getParentRelationType()) {
                    case 1:
                        vo.setParentRelationTypeName("直属");
                        break;
                    case 2:
                        vo.setParentRelationTypeName("控股");
                        break;
                    case 3:
                        vo.setParentRelationTypeName("参股");
                        break;
                    case 4:
                        vo.setParentRelationTypeName("合作");
                        break;
                    case 5:
                        vo.setParentRelationTypeName("其他");
                        break;
                    default:
                        vo.setParentRelationTypeName("未知");
                }
            }
        }
        
        // 计算用户数量
        Integer userCount = organizationMapper.countUserByOrgId(id, false);
        vo.setUserCount(userCount);
        
        // 计算子组织数量
        Integer childCount = organizationMapper.countChildrenByOrgId(id);
        vo.setChildCount(childCount);
        vo.setHasChildren(childCount > 0);
        
        return vo;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrg(SysOrganizationDTO dto) {
        // 转换为OrganizationDTO以复用现有方法
        OrganizationDTO orgDto = new OrganizationDTO();
        BeanUtils.copyProperties(dto, orgDto);
        
        // 调用现有的创建方法
        createOrganization(orgDto);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrg(SysOrganizationDTO dto) {
        // 转换为OrganizationDTO以复用现有方法
        OrganizationDTO orgDto = new OrganizationDTO();
        BeanUtils.copyProperties(dto, orgDto);
        
        // 调用现有的更新方法
        updateOrganization(orgDto);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeOrg(String id) {
        // 调用现有的删除方法
        deleteOrganization(id);
    }
    
    @Override
    public List<Map<String, Object>> getOrgTreeSelect(SysOrganizationDTO dto) {
        // 获取组织树
        List<SysOrganizationVO> orgTree = getOrgTree(dto);
        
        // 转换为前端需要的格式
        return convertToTreeSelect(orgTree);
    }
    
    @Override
    public Map<String, Object> getRoleOrgTreeSelect(String roleId) {
        Map<String, Object> result = new HashMap<>(2);
        
        // 查询组织树
        SysOrganizationDTO dto = new SysOrganizationDTO();
        dto.setIncludeChildren(true);
        dto.setWithCalcFields(true);
        List<SysOrganizationVO> orgTree = getOrgTree(dto);
        result.put("orgTree", convertToTreeSelect(orgTree));
        
        // 查询角色已选择的组织ID列表
        // TODO: 根据实际情况查询角色关联的组织ID
        List<String> checkedKeys = new ArrayList<>();
        result.put("checkedKeys", checkedKeys);
        
        return result;
    }
    
    @Override
    public Map<String, Object> getOrgDetailById(String id) {
        Map<String, Object> result = new HashMap<>();
        
        // 获取组织信息
        SysOrganizationVO org = getOrgById(id);
        if (org != null) {
            result.put("org", org);
            
            // 获取组织路径名称
            List<String> pathNames = getOrgPathNames(id);
            result.put("pathNames", pathNames);
        }
        
        return result;
    }
    
    @Override
    public List<String> getOrgPathNames(String orgId) {
        List<String> pathNames = new ArrayList<>();
        
        if (!StringUtils.hasText(orgId)) {
            return pathNames;
        }
        
        // 查询组织信息
        SysOrganization org = getById(orgId);
        if (org == null) {
            return pathNames;
        }
        
        // 获取路径ID
        String path = org.getPath();
        if (!StringUtils.hasText(path) || "/".equals(path)) {
            // 只有当前组织
            pathNames.add(org.getName());
            return pathNames;
        }
        
        // 解析路径
        String[] orgIds = path.split("/");
        for (String id : orgIds) {
            if (StringUtils.hasText(id)) {
                SysOrganization pathOrg = getById(id);
                if (pathOrg != null) {
                    pathNames.add(pathOrg.getName());
                }
            }
        }
        
        // 添加当前组织名称
        pathNames.add(org.getName());
        
        return pathNames;
    }
    
    @Override
    public boolean checkOrgCodeUnique(String id, String code) {
        return !checkCodeExists(code, id);
    }
    
    @Override
    public List<SysOrganizationVO> getChildrenOrgs(String parentId) {
        LambdaQueryWrapper<SysOrganization> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOrganization::getIsDeleted, 0);
        
        if (StringUtils.hasText(parentId)) {
            queryWrapper.eq(SysOrganization::getParentId, parentId);
        } else {
            queryWrapper.isNull(SysOrganization::getParentId);
        }
        
        queryWrapper.orderByAsc(SysOrganization::getSort).orderByAsc(SysOrganization::getName);
        
        List<SysOrganization> orgList = list(queryWrapper);
        return convertToOrgVOList(orgList, true);
    }
    
    @Override
    public byte[] exportOrgData(SysOrganizationDTO dto) {
        // 获取组织列表
        List<SysOrganizationVO> orgList = getOrgList(dto);
        
        // TODO: 实现导出功能，将orgList转换为Excel或CSV格式
        
        return new byte[0]; // 临时返回空数组
    }
    
    @Override
    public Map<String, Object> getOrgExtById(String id) {
        Map<String, Object> result = new HashMap<>();
        
        if (!StringUtils.hasText(id)) {
            return result;
        }
        
        // 查询组织扩展信息
        SysOrganizationExt orgExt = organizationExtMapper.selectById(id);
        if (orgExt != null) {
            BeanUtils.copyProperties(orgExt, result);
        }
        
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrgExt(SysOrganizationDTO dto) {
        if (!StringUtils.hasText(dto.getId())) {
            throw new RuntimeException("组织机构ID不能为空");
        }
        
        // 获取当前登录用户ID
        LoginUserVO loginUser = authService.getLoginUser();
        String userId = loginUser.getUserId();
        String tenantId = loginUser.getTenantId();
        
        // 构建扩展信息
        SysOrganizationExt ext = new SysOrganizationExt();
        BeanUtils.copyProperties(dto, ext);
        ext.setCreateBy(userId);
        ext.setCreateTime(LocalDateTime.now());
        ext.setTenantId(tenantId);
        
        // 保存扩展信息
        organizationExtMapper.insert(ext);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrgExt(SysOrganizationDTO dto) {
        if (!StringUtils.hasText(dto.getId())) {
            throw new RuntimeException("组织机构ID不能为空");
        }
        
        // 获取当前登录用户ID
        LoginUserVO loginUser = authService.getLoginUser();
        String userId = loginUser.getUserId();
        
        // 构建扩展信息
        SysOrganizationExt ext = new SysOrganizationExt();
        BeanUtils.copyProperties(dto, ext);
        ext.setUpdateBy(userId);
        ext.setUpdateTime(LocalDateTime.now());
        
        // 更新扩展信息
        organizationExtMapper.updateById(ext);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeOrgExt(String id) {
        if (!StringUtils.hasText(id)) {
            throw new RuntimeException("组织机构ID不能为空");
        }
        
        // 删除扩展信息
        organizationExtMapper.deleteById(id);
    }
    
    @Override
    public String uploadOrgLogo(String id, Object file) {
        // TODO: 实现Logo上传功能
        return "logo_url_placeholder";
    }
    
    @Override
    public String uploadOrgLicense(String id, Object file) {
        // TODO: 实现营业执照上传功能
        return "license_url_placeholder";
    }
    
    @Override
    public List<SysOrgRelationVO> getOrgRelationList(SysOrgRelationDTO dto) {
        if (dto == null) {
            return new ArrayList<>();
        }
        
        // 构建查询条件
        LambdaQueryWrapper<SysOrgRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(dto.getOrgIdFilter()), SysOrgRelation::getOrgId, dto.getOrgIdFilter())
               .eq(StringUtils.hasText(dto.getTargetOrgIdFilter()), SysOrgRelation::getTargetOrgId, dto.getTargetOrgIdFilter())
               .eq(dto.getRelationTypeFilter() != null, SysOrgRelation::getRelationType, dto.getRelationTypeFilter())
               .orderByDesc(SysOrgRelation::getCreateTime);
        
        // 查询关系列表
        List<SysOrgRelation> relations = orgRelationMapper.selectList(wrapper);
        
        // 转换为VO对象
        return convertToRelationVOList(relations);
    }

    @Override
    public SysOrgRelationVO getOrgRelationById(String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        
        // 查询关系信息
        SysOrgRelation relation = orgRelationMapper.selectById(id);
        if (relation == null) {
            return null;
        }
        
        // 转换为VO对象
        return convertToRelationVO(relation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrgRelation(SysOrgRelationDTO dto) {
        if (dto == null || !StringUtils.hasText(dto.getOrgId()) || !StringUtils.hasText(dto.getTargetOrgId())) {
            throw new RuntimeException("组织ID和目标组织ID不能为空");
        }
        
        // 检查组织是否存在
        SysOrganization org = getById(dto.getOrgId());
        SysOrganization targetOrg = getById(dto.getTargetOrgId());
        if (org == null || targetOrg == null) {
            throw new RuntimeException("组织不存在");
        }
        
        // 检查关系是否已存在
        LambdaQueryWrapper<SysOrgRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysOrgRelation::getOrgId, dto.getOrgId())
               .eq(SysOrgRelation::getTargetOrgId, dto.getTargetOrgId())
               .eq(SysOrgRelation::getRelationType, dto.getRelationType());
        if (orgRelationMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("组织关系已存在");
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        String userId = loginUser.getUserId();
        String tenantId = loginUser.getTenantId();
        
        // 构建关系实体
        SysOrgRelation relation = new SysOrgRelation();
        relation.setId(IdUtil.fastSimpleUUID());
        relation.setOrgId(dto.getOrgId());
        relation.setTargetOrgId(dto.getTargetOrgId());
        relation.setRelationType(dto.getRelationType());
        relation.setDescription(dto.getDescription());
        relation.setCreateBy(userId);
        relation.setCreateTime(LocalDateTime.now());
        relation.setTenantId(tenantId);
        
        // 保存关系
        orgRelationMapper.insert(relation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrgRelation(SysOrgRelationDTO dto) {
        if (dto == null || !StringUtils.hasText(dto.getId())) {
            throw new RuntimeException("关系ID不能为空");
        }
        
        // 查询原关系信息
        SysOrgRelation relation = orgRelationMapper.selectById(dto.getId());
        if (relation == null) {
            throw new RuntimeException("组织关系不存在");
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        String userId = loginUser.getUserId();
        
        // 更新关系信息
        relation.setRelationType(dto.getRelationType());
        relation.setDescription(dto.getDescription());
        relation.setUpdateBy(userId);
        relation.setUpdateTime(LocalDateTime.now());
        
        // 保存更新
        orgRelationMapper.updateById(relation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeOrgRelation(String id) {
        if (!StringUtils.hasText(id)) {
            throw new RuntimeException("关系ID不能为空");
        }
        
        // 删除关系
        orgRelationMapper.deleteById(id);
    }

    @Override
    public List<SysOrgRelationVO> getRelationsByOrgId(String orgId) {
        if (!StringUtils.hasText(orgId)) {
            return new ArrayList<>();
        }
        
        // 查询组织的所有关系
        LambdaQueryWrapper<SysOrgRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysOrgRelation::getOrgId, orgId)
               .orderByDesc(SysOrgRelation::getCreateTime);
        
        List<SysOrgRelation> relations = orgRelationMapper.selectList(wrapper);
        
        // 转换为VO对象
        return convertToRelationVOList(relations);
    }

    @Override
    public List<SysOrgRelationVO> getRelatedOrgs(String orgId, Integer relationType) {
        if (!StringUtils.hasText(orgId)) {
            return new ArrayList<>();
        }
        
        // 构建查询条件
        LambdaQueryWrapper<SysOrgRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysOrgRelation::getOrgId, orgId)
               .eq(relationType != null, SysOrgRelation::getRelationType, relationType)
               .orderByDesc(SysOrgRelation::getCreateTime);
        
        List<SysOrgRelation> relations = orgRelationMapper.selectList(wrapper);
        
        // 转换为VO对象
        return convertToRelationVOList(relations);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAddOrgRelations(List<SysOrgRelationDTO> relationList) {
        if (CollUtil.isEmpty(relationList)) {
            return;
        }
        
        for (SysOrgRelationDTO dto : relationList) {
            addOrgRelation(dto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchRemoveOrgRelations(List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        
        // 批量删除关系
        orgRelationMapper.deleteBatchIds(ids);
    }

    /**
     * 将关系实体转换为VO对象
     */
    private SysOrgRelationVO convertToRelationVO(SysOrgRelation relation) {
        if (relation == null) {
            return null;
        }
        
        SysOrgRelationVO vo = new SysOrgRelationVO();
        BeanUtils.copyProperties(relation, vo);
        
        // 设置组织信息
        SysOrganization org = getById(relation.getOrgId());
        if (org != null) {
            vo.setOrgName(org.getName());
            vo.setOrgCode(org.getCode());
        }
        
        // 设置目标组织信息
        SysOrganization targetOrg = getById(relation.getTargetOrgId());
        if (targetOrg != null) {
            vo.setTargetOrgName(targetOrg.getName());
            vo.setTargetOrgCode(targetOrg.getCode());
        }
        
        // 设置关系类型名称
        if (relation.getRelationType() != null) {
            switch (relation.getRelationType()) {
                case 1:
                    vo.setRelationTypeName("上下级");
                    break;
                case 2:
                    vo.setRelationTypeName("同级");
                    break;
                case 3:
                    vo.setRelationTypeName("业务协作");
                    break;
                case 4:
                    vo.setRelationTypeName("管理关系");
                    break;
                case 5:
                    vo.setRelationTypeName("其他");
                    break;
                default:
                    vo.setRelationTypeName("未知");
            }
        }
        
        return vo;
    }

    /**
     * 将关系实体列表转换为VO列表
     */
    private List<SysOrgRelationVO> convertToRelationVOList(List<SysOrgRelation> relations) {
        if (CollUtil.isEmpty(relations)) {
            return new ArrayList<>();
        }
        
        // 收集所有组织ID
        Set<String> orgIds = new HashSet<>();
        for (SysOrgRelation relation : relations) {
            orgIds.add(relation.getOrgId());
            orgIds.add(relation.getTargetOrgId());
        }
        
        // 批量查询组织信息
        Map<String, SysOrganization> orgMap = new HashMap<>();
        if (!orgIds.isEmpty()) {
            List<SysOrganization> orgs = listByIds(orgIds);
            for (SysOrganization org : orgs) {
                orgMap.put(org.getId(), org);
            }
        }
        
        // 转换为VO列表
        List<SysOrgRelationVO> voList = new ArrayList<>();
        for (SysOrgRelation relation : relations) {
            SysOrgRelationVO vo = new SysOrgRelationVO();
            BeanUtils.copyProperties(relation, vo);
            
            // 设置组织信息
            SysOrganization org = orgMap.get(relation.getOrgId());
            if (org != null) {
                vo.setOrgName(org.getName());
                vo.setOrgCode(org.getCode());
            }
            
            // 设置目标组织信息
            SysOrganization targetOrg = orgMap.get(relation.getTargetOrgId());
            if (targetOrg != null) {
                vo.setTargetOrgName(targetOrg.getName());
                vo.setTargetOrgCode(targetOrg.getCode());
            }
            
            // 设置关系类型名称
            if (relation.getRelationType() != null) {
                switch (relation.getRelationType()) {
                    case 1:
                        vo.setRelationTypeName("上下级");
                        break;
                    case 2:
                        vo.setRelationTypeName("同级");
                        break;
                    case 3:
                        vo.setRelationTypeName("业务协作");
                        break;
                    case 4:
                        vo.setRelationTypeName("管理关系");
                        break;
                    case 5:
                        vo.setRelationTypeName("其他");
                        break;
                    default:
                        vo.setRelationTypeName("未知");
                }
            }
            
            voList.add(vo);
        }
        
        return voList;
    }

    /**
     * 将组织实体列表转换为VO列表
     *
     * @param orgList 组织实体列表
     * @param withCalcFields 是否计算额外字段
     * @return VO列表
     */
    private List<SysOrganizationVO> convertToOrgVOList(List<SysOrganization> orgList, boolean withCalcFields) {
        if (orgList == null || orgList.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 转换为VO列表
        List<SysOrganizationVO> voList = new ArrayList<>();
        
        // 查询所有父组织名称
        Set<String> parentIds = orgList.stream()
                .map(SysOrganization::getParentId)
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());
        
        Map<String, String> parentNameMap = new HashMap<>();
        if (!parentIds.isEmpty()) {
            List<SysOrganization> parentOrgs = listByIds(parentIds);
            for (SysOrganization org : parentOrgs) {
                parentNameMap.put(org.getId(), org.getName());
            }
        }
        
        for (SysOrganization org : orgList) {
            SysOrganizationVO vo = new SysOrganizationVO();
            BeanUtils.copyProperties(org, vo);
            
            // 设置父组织名称
            if (StringUtils.hasText(org.getParentId())) {
                vo.setParentName(parentNameMap.get(org.getParentId()));
            }
            
            // 设置状态名称
            if (org.getStatus() != null) {
                vo.setStatusName(org.getStatus() == 0 ? "禁用" : "正常");
            }
            
            // 设置组织类型名称
            if (org.getOrgType() != null) {
                switch (org.getOrgType()) {
                    case 1:
                        vo.setOrgTypeName("公司");
                        break;
                    case 2:
                        vo.setOrgTypeName("部门");
                        break;
                    case 3:
                        vo.setOrgTypeName("团队");
                        break;
                    case 4:
                        vo.setOrgTypeName("小组");
                        break;
                    default:
                        vo.setOrgTypeName("未知");
                }
            }
            
            // 计算额外字段
            if (withCalcFields) {
                // 计算是否有子节点
                int childCount = organizationMapper.countChildrenByOrgId(org.getId());
                vo.setHasChildren(childCount > 0);
                vo.setChildCount(childCount);
                
                // 初始化子节点列表
                vo.setChildren(new ArrayList<>());
            }
            
            voList.add(vo);
        }
        
        return voList;
    }
    
    /**
     * 构建组织机构树
     *
     * @param allOrgs 所有组织VO
     * @return 树形结构
     */
    private List<SysOrganizationVO> buildSysOrgTree(List<SysOrganizationVO> allOrgs) {
        if (allOrgs == null || allOrgs.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 转换为树形结构
        List<SysOrganizationVO> rootNodes = new ArrayList<>();
        Map<String, SysOrganizationVO> nodeMap = new HashMap<>();
        
        // 将所有节点放入Map
        for (SysOrganizationVO org : allOrgs) {
            nodeMap.put(org.getId(), org);
        }
        
        // 构建树形结构
        for (SysOrganizationVO org : allOrgs) {
            if (!StringUtils.hasText(org.getParentId())) {
                // 根节点
                rootNodes.add(org);
            } else {
                // 添加到父节点
                SysOrganizationVO parentNode = nodeMap.get(org.getParentId());
                if (parentNode != null) {
                    if (parentNode.getChildren() == null) {
                        parentNode.setChildren(new ArrayList<>());
                    }
                    parentNode.getChildren().add(org);
                    parentNode.setHasChildren(true);
                } else {
                    // 父节点不存在，作为根节点
                    rootNodes.add(org);
                }
            }
        }
        
        // 排序
        sortSysOrgTreeNodes(rootNodes);
        
        return rootNodes;
    }
    
    /**
     * 递归排序树节点
     *
     * @param nodes 节点列表
     */
    private void sortSysOrgTreeNodes(List<SysOrganizationVO> nodes) {
        if (nodes != null && !nodes.isEmpty()) {
            // 排序当前级别节点
            nodes.sort(Comparator.comparing(SysOrganizationVO::getSort, Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(SysOrganizationVO::getName, Comparator.nullsLast(Comparator.naturalOrder())));
            
            // 递归排序子节点
            for (SysOrganizationVO node : nodes) {
                if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                    sortSysOrgTreeNodes(node.getChildren());
                }
            }
        }
    }
    
    /**
     * 将组织树转换为树选择结构
     *
     * @param orgTree 组织树
     * @return 树选择数据
     */
    private List<Map<String, Object>> convertToTreeSelect(List<SysOrganizationVO> orgTree) {
        List<Map<String, Object>> treeSelect = new ArrayList<>();
        
        for (SysOrganizationVO org : orgTree) {
            Map<String, Object> node = new HashMap<>();
            node.put("id", org.getId());
            node.put("label", org.getName());
            node.put("value", org.getId());
            
            if (org.getChildren() != null && !org.getChildren().isEmpty()) {
                node.put("children", convertToTreeSelect(org.getChildren()));
            }
            
            treeSelect.add(node);
        }
        
        return treeSelect;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUsersToOrg(String orgId, List<String> userIds) {
        if (!StringUtils.hasText(orgId) || CollUtil.isEmpty(userIds)) {
            throw new RuntimeException("组织ID和用户ID列表不能为空");
        }
        
        // 检查组织是否存在
        SysOrganization org = getById(orgId);
        if (org == null) {
            throw new RuntimeException("组织不存在");
        }
        
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        String operatorId = loginUser.getUserId();
        String tenantId = loginUser.getTenantId();
        
        // 批量添加用户到组织
        for (String userId : userIds) {
            SysOrgRelation relation = new SysOrgRelation();
            relation.setId(IdUtil.fastSimpleUUID());
            relation.setOrgId(orgId);
            relation.setTargetOrgId(userId);
            relation.setRelationType(1); // 1表示用户所属组织关系
            relation.setCreateBy(operatorId);
            relation.setCreateTime(LocalDateTime.now());
            relation.setTenantId(tenantId);
            orgRelationMapper.insert(relation);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUsersFromOrg(String orgId, List<String> userIds) {
        if (!StringUtils.hasText(orgId) || CollUtil.isEmpty(userIds)) {
            throw new RuntimeException("组织ID和用户ID列表不能为空");
        }
        
        // 删除用户与组织的关系
        LambdaQueryWrapper<SysOrgRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysOrgRelation::getOrgId, orgId)
               .in(SysOrgRelation::getTargetOrgId, userIds)
               .eq(SysOrgRelation::getRelationType, 1); // 1表示用户所属组织关系
        
        orgRelationMapper.delete(wrapper);
    }
    
    @Override
    public String getOrgLeaderId(String orgId) {
        if (!StringUtils.hasText(orgId)) {
            return null;
        }
        
        SysOrganization org = getById(orgId);
        return org != null ? org.getLeaderId() : null;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setOrgLeader(String orgId, String userId) {
        if (!StringUtils.hasText(orgId) || !StringUtils.hasText(userId)) {
            throw new RuntimeException("组织ID和用户ID不能为空");
        }
        
        // 获取当前登录用户ID
        LoginUserVO loginUser = authService.getLoginUser();
        String operatorId = loginUser.getUserId();
        
        // 更新组织负责人
        SysOrganization org = new SysOrganization();
        org.setId(orgId);
        org.setLeaderId(userId);
        org.setUpdateBy(operatorId);
        org.setUpdateTime(LocalDateTime.now());
        
        updateById(org);
    }
} 