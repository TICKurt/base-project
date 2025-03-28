package com.example.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.auth.domain.vo.LoginUserVO;
import com.example.auth.service.AuthService;
import com.example.business.domain.dto.TestItemDTO;
import com.example.business.domain.entity.TestItem;
import com.example.business.domain.vo.TestItemVO;
import com.example.business.mapper.TestItemMapper;
import com.example.business.service.TestItemService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试项目服务实现类
 * 
 * @author example
 */
@Service
@RequiredArgsConstructor
public class TestItemServiceImpl implements TestItemService {

    private final TestItemMapper testItemMapper;
    private final AuthService authService;

    @Override
    public Page<TestItemVO> page(Page<TestItemVO> page, String name) {
        // 查询条件
        LambdaQueryWrapper<TestItem> queryWrapper = new LambdaQueryWrapper<>();
        // 如果名称不为空，则加入查询条件
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(TestItem::getName, name);
        }
        // 按创建时间倒序排序
        queryWrapper.orderByDesc(TestItem::getCreatedAt);

        // 分页查询
        Page<TestItem> testItemPage = new Page<>(page.getCurrent(), page.getSize());
        testItemPage = testItemMapper.selectPage(testItemPage, queryWrapper);

        // 转换结果
        List<TestItemVO> records = new ArrayList<>();
        if (testItemPage.getRecords() != null && !testItemPage.getRecords().isEmpty()) {
            records = testItemPage.getRecords().stream().map(this::convertToVO).collect(Collectors.toList());
        }

        // 组装返回结果
        Page<TestItemVO> resultPage = new Page<>();
        resultPage.setCurrent(testItemPage.getCurrent());
        resultPage.setSize(testItemPage.getSize());
        resultPage.setTotal(testItemPage.getTotal());
        resultPage.setPages(testItemPage.getPages());
        resultPage.setRecords(records);

        return resultPage;
    }

    @Override
    public TestItemVO getById(Integer id) {
        TestItem testItem = testItemMapper.selectById(id);
        if (testItem == null) {
            return null;
        }
        return convertToVO(testItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TestItemVO add(TestItemDTO dto) {
        // 获取当前登录用户
        LoginUserVO loginUser = authService.getLoginUser();
        
        // 创建实体对象
        TestItem testItem = new TestItem();
        BeanUtils.copyProperties(dto, testItem);
        
        // 设置创建人
        testItem.setCreatedBy(loginUser.getUserId().intValue());
        
        // 插入数据库
        testItemMapper.insert(testItem);
        
        // 返回结果
        return convertToVO(testItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TestItemVO update(TestItemDTO dto) {
        // 验证项目是否存在
        TestItem existingItem = testItemMapper.selectById(dto.getId());
        if (existingItem == null) {
            throw new RuntimeException("项目不存在");
        }
        
        // 验证是否为创建者或管理员
        LoginUserVO loginUser = authService.getLoginUser();
        if (!existingItem.getCreatedBy().equals(loginUser.getUserId().intValue()) && 
                !loginUser.getRoles().contains("admin")) {
            throw new RuntimeException("只有创建者或管理员才能修改项目");
        }
        
        // 更新实体对象
        BeanUtils.copyProperties(dto, existingItem);
        
        // 更新数据库
        testItemMapper.updateById(existingItem);
        
        // 返回结果
        return convertToVO(existingItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer id) {
        // 验证项目是否存在
        TestItem existingItem = testItemMapper.selectById(id);
        if (existingItem == null) {
            throw new RuntimeException("项目不存在");
        }
        
        // 验证是否为创建者或管理员
        LoginUserVO loginUser = authService.getLoginUser();
        if (!existingItem.getCreatedBy().equals(loginUser.getUserId().intValue()) && 
                !loginUser.getRoles().contains("admin")) {
            throw new RuntimeException("只有创建者或管理员才能删除项目");
        }
        
        // 删除数据
        int rows = testItemMapper.deleteById(id);
        
        return rows > 0;
    }
    
    /**
     * 将实体对象转换为VO
     */
    private TestItemVO convertToVO(TestItem entity) {
        TestItemVO vo = new TestItemVO();
        BeanUtils.copyProperties(entity, vo);
        
        // TODO: 可以在这里根据创建人ID查询创建人名称
        // 这里简单实现，实际项目中应该调用用户服务查询
        vo.setCreatorName("用户" + entity.getCreatedBy());
        
        return vo;
    }
} 