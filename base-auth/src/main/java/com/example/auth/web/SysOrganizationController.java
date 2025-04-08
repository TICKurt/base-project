package com.example.auth.web;

import com.example.auth.dto.SysOrganizationDTO;
import com.example.auth.entity.SysOrganization;
import com.example.auth.service.SysOrganizationService;
import com.example.auth.vo.SysOrganizationVO;
import com.example.core.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 组织机构管理控制器
 *
 * @author 作者
 * @date 创建时间
 */
@RestController
@RequestMapping("/organization")
public class SysOrganizationController {

    @Autowired
    private SysOrganizationService organizationService;

    /**
     * 获取组织机构列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:org:list')")
    public Result<List<SysOrganizationVO>> list(SysOrganizationDTO dto) {
        List<SysOrganizationVO> list = organizationService.getOrgList(dto);
        return Result.ok(list);
    }

    /**
     * 获取组织机构树形结构
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:org:list')")
    public Result<List<SysOrganizationVO>> tree(SysOrganizationDTO dto) {
        List<SysOrganizationVO> tree = organizationService.getOrgTree(dto);
        return Result.ok(tree);
    }

    /**
     * 获取组织机构详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public Result<SysOrganizationVO> getInfo(@PathVariable String id) {
        SysOrganizationVO org = organizationService.getOrgById(id);
        return Result.ok(org);
    }

    /**
     * 新增组织机构
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:org:add')")
    public Result<Void> add(@Valid @RequestBody SysOrganizationDTO dto) {
        organizationService.addOrg(dto);
        return Result.ok();
    }

    /**
     * 修改组织机构
     */
    @PutMapping
    @PreAuthorize("hasAuthority('system:org:edit')")
    public Result<Void> update(@Valid @RequestBody SysOrganizationDTO dto) {
        organizationService.updateOrg(dto);
        return Result.ok();
    }

    /**
     * 删除组织机构
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:org:remove')")
    public Result<Void> remove(@PathVariable String id) {
        organizationService.removeOrg(id);
        return Result.ok();
    }

    /**
     * 获取组织机构下拉树列表
     */
    @GetMapping("/treeSelect")
    public Result<List<Map<String, Object>>> treeSelect(SysOrganizationDTO dto) {
        List<Map<String, Object>> treeSelect = organizationService.getOrgTreeSelect(dto);
        return Result.ok(treeSelect);
    }

    /**
     * 加载对应角色组织列表树
     */
    @GetMapping("/roleOrgTreeSelect/{roleId}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<Map<String, Object>> roleOrgTreeSelect(@PathVariable String roleId) {
        Map<String, Object> roleOrgTree = organizationService.getRoleOrgTreeSelect(roleId);
        return Result.ok(roleOrgTree);
    }

    /**
     * 获取组织机构详情（包含扩展信息）
     */
    @GetMapping("/detail/{id}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public Result<Map<String, Object>> getOrgDetail(@PathVariable String id) {
        Map<String, Object> detail = organizationService.getOrgDetailById(id);
        return Result.ok(detail);
    }

    /**
     * 获取组织机构路径名称
     */
    @GetMapping("/paths")
    public Result<List<String>> getOrgPathNames(@RequestParam String orgId) {
        List<String> pathNames = organizationService.getOrgPathNames(orgId);
        return Result.ok(pathNames);
    }

    /**
     * 检查组织编码是否唯一
     */
    @GetMapping("/checkCodeUnique")
    public Result<Boolean> checkCodeUnique(@RequestParam(required = false) String id, @RequestParam String code) {
        boolean isUnique = organizationService.checkOrgCodeUnique(id, code);
        return Result.ok(isUnique);
    }

    /**
     * 获取下级组织列表
     */
    @GetMapping("/children/{parentId}")
    @PreAuthorize("hasAuthority('system:org:list')")
    public Result<List<SysOrganizationVO>> getChildrenOrgs(@PathVariable String parentId) {
        List<SysOrganizationVO> children = organizationService.getChildrenOrgs(parentId);
        return Result.ok(children);
    }

    /**
     * 导出组织机构数据
     */
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('system:org:export')")
    public Result<byte[]> export(SysOrganizationDTO dto) {
        byte[] data = organizationService.exportOrgData(dto);
        return Result.ok(data);
    }
} 