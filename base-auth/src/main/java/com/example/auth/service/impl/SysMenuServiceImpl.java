package com.example.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.dto.SysMenuDTO;
import com.example.auth.entity.SysMenu;
import com.example.auth.mapper.SysMenuMapper;
import com.example.auth.mapper.SysRoleMenuMapper;
import com.example.auth.service.SysMenuService;
import com.example.auth.vo.RouterVO;
import com.example.auth.vo.SysMenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 *
 * @author 作者
 * @date 创建时间
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public List<SysMenuVO> getMenuList(SysMenuDTO dto) {
        LambdaQueryWrapper<SysMenu> wrapper = buildQueryWrapper(dto);
        List<SysMenu> menuList = list(wrapper);
        
        return convertToVOList(menuList);
    }

    @Override
    public List<SysMenuVO> getMenuTree(SysMenuDTO dto) {
        List<SysMenuVO> menuList = getMenuList(dto);
        // 根据业务需求，可能需要过滤掉按钮类型的菜单
        if (!Boolean.TRUE.equals(dto.getIncludeButtons())) {
            menuList = menuList.stream()
                    .filter(menu -> menu.getMenuType() != 2)
                    .collect(Collectors.toList());
        }
        return buildMenuTree(menuList);
    }

    @Override
    public List<SysMenuVO> getMenuTreeByUserId(String userId) {
        List<SysMenu> menuList = baseMapper.selectMenusByUserId(userId);
        List<SysMenuVO> voList = convertToVOList(menuList);
        // 过滤掉按钮类型
        voList = voList.stream()
                .filter(menu -> menu.getMenuType() != 2)
                .collect(Collectors.toList());
        return buildMenuTree(voList);
    }

    @Override
    public SysMenuVO getMenuById(String menuId) {
        SysMenu menu = getById(menuId);
        if (menu == null) {
            return null;
        }
        
        SysMenuVO vo = convertToVO(menu);
        // 查询父菜单名称
        if (StringUtils.isNotBlank(menu.getParentId()) && !"0".equals(menu.getParentId())) {
            SysMenu parentMenu = getById(menu.getParentId());
            if (parentMenu != null) {
                vo.setParentName(parentMenu.getName());
            }
        }
        
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(SysMenuDTO dto) {
        SysMenu menu = new SysMenu();
        BeanUtil.copyProperties(dto, menu);
        
        // 设置主键
        menu.setId(IdUtil.fastSimpleUUID());
        
        // 设置菜单层级和路径
        setMenuLevelAndPath(menu);
        
        // 设置默认值
        if (menu.getStatus() == null) {
            menu.setStatus(1); // 默认启用
        }
        if (menu.getVisible() == null) {
            menu.setVisible(1); // 默认显示
        }
        
        menu.setCreateTime(LocalDateTime.now());
        
        save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenuDTO dto) {
        SysMenu menu = getById(dto.getId());
        if (menu == null) {
            throw new RuntimeException("菜单不存在");
        }
        
        // 判断是否修改了父级，如果修改了，需要更新层级和路径
        boolean isUpdateParent = !StringUtils.equals(dto.getParentId(), menu.getParentId());
        
        BeanUtil.copyProperties(dto, menu);
        
        if (isUpdateParent) {
            setMenuLevelAndPath(menu);
            
            // 更新子菜单的层级和路径
            updateChildrenLevelAndPath(menu);
        }
        
        menu.setUpdateTime(LocalDateTime.now());
        
        updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(String menuId) {
        // 判断是否有子菜单
        if (hasChildByMenuId(menuId)) {
            throw new RuntimeException("存在子菜单,不允许删除");
        }
        
        // 判断是否被角色使用
        int count = roleMenuMapper.countRoleMenuByMenuId(menuId);
        if (count > 0) {
            throw new RuntimeException("菜单已分配,不允许删除");
        }
        
        removeById(menuId);
    }

    @Override
    public List<Map<String, Object>> getMenuTreeSelect(SysMenuDTO dto) {
        List<SysMenuVO> menuList = getMenuList(dto);
        // 过滤掉按钮类型
        menuList = menuList.stream()
                .filter(menu -> menu.getMenuType() != 2)
                .collect(Collectors.toList());
        
        // 转换为下拉树结构
        return buildMenuTreeSelect(menuList);
    }

    @Override
    public Map<String, Object> getRoleMenuTreeSelect(String roleId) {
        Map<String, Object> result = new HashMap<>(2);
        
        // 查询所有菜单
        SysMenuDTO dto = new SysMenuDTO();
        dto.setStatus(1); // 只查询正常状态的菜单
        List<SysMenuVO> menuList = getMenuList(dto);
        // 过滤掉按钮类型
        menuList = menuList.stream()
                .filter(menu -> menu.getMenuType() != 2)
                .collect(Collectors.toList());
        
        // 获取角色已分配的菜单ID列表
        List<String> checkedKeys = baseMapper.selectMenuIdsByRoleId(roleId);
        
        result.put("menuTree", buildMenuTreeSelect(menuList));
        result.put("checkedKeys", checkedKeys);
        
        return result;
    }

    @Override
    public boolean checkMenuNameUnique(SysMenuDTO dto) {
        String menuId = StringUtils.isBlank(dto.getId()) ? "" : dto.getId();
        
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getName, dto.getName())
                .eq(SysMenu::getParentId, dto.getParentId());
        
        SysMenu menu = getOne(wrapper);
        
        return menu == null || menu.getId().equals(menuId);
    }

    @Override
    public boolean hasChildByMenuId(String menuId) {
        return baseMapper.hasChildByMenuId(menuId) > 0;
    }

    @Override
    public Set<String> getPermissionsByUserId(String userId) {
        List<String> permsList = baseMapper.selectPermissionsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        
        for (String perms : permsList) {
            if (StringUtils.isNotBlank(perms)) {
                permsSet.add(perms);
            }
        }
        
        return permsSet;
    }

    @Override
    public List<RouterVO> getRoutersByUserId(String userId) {
        List<SysMenu> menuList = baseMapper.selectMenusByUserId(userId);
        
        // 过滤掉按钮类型，并按照父子关系构建路由树
        return buildRouterTree(menuList);
    }

    @Override
    public int countMenuUsage(String menuId) {
        return roleMenuMapper.countRoleMenuByMenuId(menuId);
    }

    @Override
    public List<SysMenuVO> getButtonPermissions() {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getMenuType, 2) // 按钮类型
                .eq(SysMenu::getStatus, 1) // 正常状态
                .orderByAsc(SysMenu::getParentId)
                .orderByAsc(SysMenu::getSort);
        
        List<SysMenu> menuList = list(wrapper);
        return convertToVOList(menuList);
    }

    @Override
    public List<SysMenuVO> getParentMenus() {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.<SysMenu>lambdaQuery()
                .in(SysMenu::getMenuType, Arrays.asList(0, 1)) // 目录和菜单类型
                .eq(SysMenu::getStatus, 1) // 正常状态
                .orderByAsc(SysMenu::getParentId)
                .orderByAsc(SysMenu::getSort);
        
        List<SysMenu> menuList = list(wrapper);
        return convertToVOList(menuList);
    }

    @Override
    public List<SysMenuVO> getChildrenMenus(String parentId) {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getParentId, parentId)
                .eq(SysMenu::getStatus, 1) // 正常状态
                .orderByAsc(SysMenu::getSort);
        
        List<SysMenu> menuList = list(wrapper);
        return convertToVOList(menuList);
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<SysMenu> buildQueryWrapper(SysMenuDTO dto) {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
        
        // 根据名称模糊搜索
        wrapper.like(StringUtils.isNotBlank(dto.getNameLike()), SysMenu::getName, dto.getNameLike());
        
        // 根据状态过滤
        wrapper.eq(ObjectUtil.isNotNull(dto.getStatusFilter()), SysMenu::getStatus, dto.getStatusFilter());
        
        // 根据菜单类型过滤
        wrapper.eq(ObjectUtil.isNotNull(dto.getMenuTypeFilter()), SysMenu::getMenuType, dto.getMenuTypeFilter());
        
        // 是否只查询顶级菜单
        if (Boolean.TRUE.equals(dto.getOnlyRoot())) {
            wrapper.eq(SysMenu::getParentId, "0");
        }
        
        // 排序
        wrapper.orderByAsc(SysMenu::getParentId)
               .orderByAsc(SysMenu::getSort);
        
        return wrapper;
    }

    /**
     * 将实体对象转为VO对象
     */
    private SysMenuVO convertToVO(SysMenu menu) {
        SysMenuVO vo = new SysMenuVO();
        BeanUtil.copyProperties(menu, vo);
        
        // 设置菜单类型名称
        if (menu.getMenuType() != null) {
            switch (menu.getMenuType()) {
                case 0:
                    vo.setMenuTypeName("目录");
                    break;
                case 1:
                    vo.setMenuTypeName("菜单");
                    break;
                case 2:
                    vo.setMenuTypeName("按钮");
                    break;
                default:
                    vo.setMenuTypeName("未知");
            }
        }
        
        // 设置可见状态名称
        if (menu.getVisible() != null) {
            vo.setVisibleName(menu.getVisible() == 1 ? "显示" : "隐藏");
        }
        
        // 设置状态名称
        if (menu.getStatus() != null) {
            vo.setStatusName(menu.getStatus() == 1 ? "正常" : "停用");
        }
        
        // 设置是否缓存名称
        if (menu.getKeepAlive() != null) {
            vo.setKeepAliveName(menu.getKeepAlive() == 1 ? "是" : "否");
        }
        
        // 设置是否总是显示名称
        if (menu.getAlwaysShow() != null) {
            vo.setAlwaysShowName(menu.getAlwaysShow() == 1 ? "是" : "否");
        }
        
        // 构建Meta信息（用于前端路由）
        SysMenuVO.MetaVO meta = new SysMenuVO.MetaVO();
        meta.setTitle(menu.getName());
        meta.setIcon(menu.getIcon());
        meta.setHidden(menu.getVisible() == 0);
        meta.setKeepAlive(menu.getKeepAlive() == 1);
        meta.setPermission(menu.getPerms());
        vo.setMeta(meta);
        
        return vo;
    }

    /**
     * 将实体列表转为VO列表
     */
    private List<SysMenuVO> convertToVOList(List<SysMenu> menuList) {
        if (CollUtil.isEmpty(menuList)) {
            return new ArrayList<>();
        }
        
        return menuList.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 构建菜单树
     */
    private List<SysMenuVO> buildMenuTree(List<SysMenuVO> menuList) {
        if (CollUtil.isEmpty(menuList)) {
            return new ArrayList<>();
        }
        
        // 构建一个Map，key是菜单ID，value是菜单
        Map<String, SysMenuVO> menuMap = menuList.stream()
                .collect(Collectors.toMap(SysMenuVO::getId, m -> m, (e1, e2) -> e1));
        
        List<SysMenuVO> resultList = new ArrayList<>();
        
        for (SysMenuVO menu : menuList) {
            // 如果是顶级菜单，直接加入结果列表
            if ("0".equals(menu.getParentId()) || !menuMap.containsKey(menu.getParentId())) {
                resultList.add(menu);
                continue;
            }
            
            // 如果不是顶级菜单，找到父菜单，将当前菜单添加到父菜单的children列表中
            SysMenuVO parentMenu = menuMap.get(menu.getParentId());
            if (parentMenu.getChildren() == null) {
                parentMenu.setChildren(new ArrayList<>());
            }
            parentMenu.getChildren().add(menu);
        }
        
        // 计算是否有子节点
        for (SysMenuVO menu : menuList) {
            menu.setHasChildren(CollUtil.isNotEmpty(menu.getChildren()));
        }
        
        return resultList;
    }

    /**
     * 构建菜单下拉树选择器
     */
    private List<Map<String, Object>> buildMenuTreeSelect(List<SysMenuVO> menuList) {
        if (CollUtil.isEmpty(menuList)) {
            return new ArrayList<>();
        }
        
        // 转换为TreeNode列表
        List<TreeNode<String>> nodeList = menuList.stream().map(menu -> {
            TreeNode<String> node = new TreeNode<>();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSort() != null ? menu.getSort() : 0);
            
            Map<String, Object> extra = new HashMap<>(5);
            extra.put("path", menu.getPath());
            extra.put("component", menu.getComponent());
            extra.put("perms", menu.getPerms());
            extra.put("icon", menu.getIcon());
            extra.put("menuType", menu.getMenuType());
            extra.put("visible", menu.getVisible());
            node.setExtra(extra);
            
            return node;
        }).collect(Collectors.toList());
        
        // 构建树结构
        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");
        
        // 转换为前端需要的格式
        return treeList.stream().map(tree -> {
            Map<String, Object> map = new HashMap<>(3);
            map.put("id", tree.getId());
            map.put("label", tree.getName());
            
            List<Tree<String>> children = tree.getChildren();
            if (CollUtil.isNotEmpty(children)) {
                map.put("children", convertTreeToMap(children));
            }
            
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 将Tree转换为Map
     */
    private List<Map<String, Object>> convertTreeToMap(List<Tree<String>> treeList) {
        return treeList.stream().map(tree -> {
            Map<String, Object> map = new HashMap<>(3);
            map.put("id", tree.getId());
            map.put("label", tree.getName());
            
            List<Tree<String>> children = tree.getChildren();
            if (CollUtil.isNotEmpty(children)) {
                map.put("children", convertTreeToMap(children));
            }
            
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 构建前端所需的路由结构
     */
    private List<RouterVO> buildRouterTree(List<SysMenu> menuList) {
        if (CollUtil.isEmpty(menuList)) {
            return new ArrayList<>();
        }
        
        // 过滤掉按钮类型
        menuList = menuList.stream()
                .filter(menu -> menu.getMenuType() != 2)
                .collect(Collectors.toList());
        
        // 构建一个Map，key是菜单ID，value是菜单
        Map<String, SysMenu> menuMap = menuList.stream()
                .collect(Collectors.toMap(SysMenu::getId, m -> m, (e1, e2) -> e1));
        
        // 找出顶级菜单
        List<SysMenu> rootMenus = menuList.stream()
                .filter(menu -> "0".equals(menu.getParentId()) || !menuMap.containsKey(menu.getParentId()))
                .collect(Collectors.toList());
        
        // 构建路由树
        return rootMenus.stream()
                .map(menu -> getRouter(menu, menuMap))
                .collect(Collectors.toList());
    }

    /**
     * 构建单个路由
     */
    private RouterVO getRouter(SysMenu menu, Map<String, SysMenu> menuMap) {
        RouterVO router = new RouterVO();
        
        // 设置路由信息
        router.setName(getRouteName(menu));
        router.setPath(getRouterPath(menu));
        router.setComponent(getComponent(menu));
        router.setHidden(menu.getVisible() == 0);
        router.setAlwaysShow(menu.getAlwaysShow() == 1);
        
        // 设置路由元数据
        RouterVO.MetaVO meta = new RouterVO.MetaVO();
        meta.setTitle(menu.getName());
        meta.setIcon(menu.getIcon());
        meta.setKeepAlive(menu.getKeepAlive() == 1);
        meta.setPermission(menu.getPerms());
        router.setMeta(meta);
        
        // 处理子路由
        List<SysMenu> childMenus = getChildList(menu.getId(), menuMap);
        if (CollUtil.isNotEmpty(childMenus)) {
            router.setChildren(childMenus.stream()
                    .map(child -> getRouter(child, menuMap))
                    .collect(Collectors.toList()));
        }
        
        return router;
    }

    /**
     * 获取路由名称
     */
    private String getRouteName(SysMenu menu) {
        return StringUtils.capitalize(menu.getPath());
    }

    /**
     * 获取路由地址
     */
    private String getRouterPath(SysMenu menu) {
        if ("0".equals(menu.getParentId())) {
            return "/" + menu.getPath();
        }
        return menu.getPath();
    }

    /**
     * 获取组件信息
     */
    private String getComponent(SysMenu menu) {
        if (StringUtils.isNotBlank(menu.getComponent())) {
            return menu.getComponent();
        }
        
        if ("0".equals(menu.getParentId()) && menu.getMenuType() == 0) {
            return "Layout";
        }
        
        return "ParentView";
    }

    /**
     * 根据父节点ID获取所有子节点
     */
    private List<SysMenu> getChildList(String parentId, Map<String, SysMenu> menuMap) {
        return menuMap.values().stream()
                .filter(menu -> parentId.equals(menu.getParentId()))
                .collect(Collectors.toList());
    }

    /**
     * 设置菜单层级和路径
     */
    private void setMenuLevelAndPath(SysMenu menu) {
        if ("0".equals(menu.getParentId())) {
            // 顶级菜单
            menu.setLevel(1);
            menu.setTreePath("/0/");
        } else {
            // 子菜单
            SysMenu parentMenu = getById(menu.getParentId());
            if (parentMenu != null) {
                menu.setLevel(parentMenu.getLevel() + 1);
                menu.setTreePath(parentMenu.getTreePath() + parentMenu.getId() + "/");
            }
        }
    }

    /**
     * 更新子菜单的层级和路径
     */
    private void updateChildrenLevelAndPath(SysMenu parentMenu) {
        // 查询所有子菜单
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.<SysMenu>lambdaQuery()
                .likeRight(SysMenu::getTreePath, parentMenu.getTreePath() + parentMenu.getId() + "/");
        
        List<SysMenu> childMenus = list(wrapper);
        if (CollUtil.isEmpty(childMenus)) {
            return;
        }
        
        for (SysMenu childMenu : childMenus) {
            // 更新层级
            childMenu.setLevel(childMenu.getLevel() + (parentMenu.getLevel() - (childMenu.getLevel() - 1)));
            
            // 更新路径
            String oldPath = childMenu.getTreePath();
            String newPath = parentMenu.getTreePath() + parentMenu.getId() + "/"
                    + oldPath.substring(oldPath.indexOf(parentMenu.getId()) + parentMenu.getId().length() + 1);
            childMenu.setTreePath(newPath);
            
            updateById(childMenu);
        }
    }
} 