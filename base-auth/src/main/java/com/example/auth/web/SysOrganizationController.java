package com.example.auth.web;

import com.example.auth.dto.SysOrganizationDTO;
import com.example.auth.entity.SysOrganization;
import com.example.auth.service.SysOrganizationService;
import com.example.auth.vo.SysOrganizationVO;
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
    public ResponseEntity<List<SysOrganizationVO>> list(SysOrganizationDTO dto) {
        List<SysOrganizationVO> list = organizationService.getOrgList(dto);
        return ResponseEntity.ok(list);
    }

    /**
     * 获取组织机构树形结构
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:org:list')")
    public ResponseEntity<List<SysOrganizationVO>> tree(SysOrganizationDTO dto) {
        List<SysOrganizationVO> tree = organizationService.getOrgTree(dto);
        return ResponseEntity.ok(tree);
    }

    /**
     * 获取组织机构详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public ResponseEntity<SysOrganizationVO> getInfo(@PathVariable String id) {
        SysOrganizationVO org = organizationService.getOrgById(id);
        return ResponseEntity.ok(org);
    }

    /**
     * 新增组织机构
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:org:add')")
    public ResponseEntity<Void> add(@Valid @RequestBody SysOrganizationDTO dto) {
        organizationService.addOrg(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改组织机构
     */
    @PutMapping
    @PreAuthorize("hasAuthority('system:org:edit')")
    public ResponseEntity<Void> update(@Valid @RequestBody SysOrganizationDTO dto) {
        organizationService.updateOrg(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除组织机构
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:org:remove')")
    public ResponseEntity<Void> remove(@PathVariable String id) {
        organizationService.removeOrg(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取组织机构下拉树列表
     */
    @GetMapping("/treeSelect")
    public ResponseEntity<List<Map<String, Object>>> treeSelect(SysOrganizationDTO dto) {
        List<Map<String, Object>> treeSelect = organizationService.getOrgTreeSelect(dto);
        return ResponseEntity.ok(treeSelect);
    }

    /**
     * 加载对应角色组织列表树
     */
    @GetMapping("/roleOrgTreeSelect/{roleId}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public ResponseEntity<Map<String, Object>> roleOrgTreeSelect(@PathVariable String roleId) {
        Map<String, Object> roleOrgTree = organizationService.getRoleOrgTreeSelect(roleId);
        return ResponseEntity.ok(roleOrgTree);
    }

    /**
     * 获取组织机构详情（包含扩展信息）
     */
    @GetMapping("/detail/{id}")
    @PreAuthorize("hasAuthority('system:org:query')")
    public ResponseEntity<Map<String, Object>> getOrgDetail(@PathVariable String id) {
        Map<String, Object> detail = organizationService.getOrgDetailById(id);
        return ResponseEntity.ok(detail);
    }

    /**
     * 获取组织机构路径名称
     */
    @GetMapping("/paths")
    public ResponseEntity<List<String>> getOrgPathNames(@RequestParam String orgId) {
        List<String> pathNames = organizationService.getOrgPathNames(orgId);
        return ResponseEntity.ok(pathNames);
    }

    /**
     * 检查组织编码是否唯一
     */
    @GetMapping("/checkCodeUnique")
    public ResponseEntity<Boolean> checkCodeUnique(@RequestParam(required = false) String id, @RequestParam String code) {
        boolean isUnique = organizationService.checkOrgCodeUnique(id, code);
        return ResponseEntity.ok(isUnique);
    }

    /**
     * 获取下级组织列表
     */
    @GetMapping("/children/{parentId}")
    @PreAuthorize("hasAuthority('system:org:list')")
    public ResponseEntity<List<SysOrganizationVO>> getChildrenOrgs(@PathVariable String parentId) {
        List<SysOrganizationVO> children = organizationService.getChildrenOrgs(parentId);
        return ResponseEntity.ok(children);
    }

    /**
     * 导出组织机构数据
     */
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('system:org:export')")
    public ResponseEntity<byte[]> export(SysOrganizationDTO dto) {
        byte[] data = organizationService.exportOrgData(dto);
        return ResponseEntity.ok(data);
    }
} 