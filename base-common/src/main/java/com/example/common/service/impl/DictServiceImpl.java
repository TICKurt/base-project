package com.example.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.mapper.DictItemMapper;
import com.example.common.mapper.DictTypeMapper;
import com.example.common.model.DictItem;
import com.example.common.model.DictType;
import com.example.common.model.dto.DictItemDTO;
import com.example.common.model.dto.DictTypeDTO;
import com.example.common.model.vo.DictItemVO;
import com.example.common.model.vo.DictTypeVO;
import com.example.common.service.DictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典服务实现类
 * 
 * @author example
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {

    private final DictTypeMapper dictTypeMapper;
    private final DictItemMapper dictItemMapper;

    @Override
    public IPage<DictTypeVO> pageDictType(Page<DictTypeVO> page, String name, String code, Integer status) {
        // 查询条件
        LambdaQueryWrapper<DictType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(name), DictType::getName, name)
                .like(StringUtils.isNotBlank(code), DictType::getCode, code)
                .eq(status != null, DictType::getStatus, status)
                .orderByDesc(DictType::getCreateTime);

        // 分页查询
        Page<DictType> dictTypePage = new Page<>(page.getCurrent(), page.getSize());
        IPage<DictType> dictTypeIPage = dictTypeMapper.selectPage(dictTypePage, queryWrapper);

        // 转换为VO
        IPage<DictTypeVO> result = new Page<>(dictTypeIPage.getCurrent(), dictTypeIPage.getSize(), dictTypeIPage.getTotal());
        result.setRecords(dictTypeIPage.getRecords().stream().map(this::convertToTypeVO).collect(Collectors.toList()));
        
        return result;
    }

    @Override
    @Cacheable(value = "dict:type", key = "#id", unless = "#result == null")
    public DictTypeVO getDictTypeById(String id) {
        DictType dictType = dictTypeMapper.selectById(id);
        if (dictType == null) {
            return null;
        }
        return convertToTypeVO(dictType);
    }

    @Override
    @Cacheable(value = "dict:type:code", key = "#code", unless = "#result == null")
    public DictTypeVO getDictTypeByCode(String code) {
        LambdaQueryWrapper<DictType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DictType::getCode, code);
        DictType dictType = dictTypeMapper.selectOne(queryWrapper);
        if (dictType == null) {
            return null;
        }
        return convertToTypeVO(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"dict:type", "dict:type:code", "dict:all"}, allEntries = true)
    public DictTypeVO addDictType(DictTypeDTO dto) {
        // 检查编码是否重复
        LambdaQueryWrapper<DictType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DictType::getCode, dto.getCode());
        if (dictTypeMapper.selectCount(queryWrapper) > 0) {
            throw new IllegalArgumentException("字典编码已存在");
        }

        // 实体转换
        DictType dictType = new DictType();
        BeanUtils.copyProperties(dto, dictType);
        
        // 保存
        dictTypeMapper.insert(dictType);
        
        return convertToTypeVO(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"dict:type", "dict:type:code", "dict:all", "dict:items"}, allEntries = true)
    public DictTypeVO updateDictType(DictTypeDTO dto) {
        // 检查是否存在
        DictType oldType = dictTypeMapper.selectById(dto.getId());
        if (oldType == null) {
            throw new IllegalArgumentException("字典类型不存在");
        }
        
        // 检查编码是否重复
        LambdaQueryWrapper<DictType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DictType::getCode, dto.getCode())
                   .ne(DictType::getId, dto.getId());
        if (dictTypeMapper.selectCount(queryWrapper) > 0) {
            throw new IllegalArgumentException("字典编码已存在");
        }

        // 实体转换
        DictType dictType = new DictType();
        BeanUtils.copyProperties(dto, dictType);
        
        // 更新
        dictTypeMapper.updateById(dictType);
        
        // 如果编码发生变化，更新字典项的typeCode
        if (!oldType.getCode().equals(dto.getCode())) {
            LambdaQueryWrapper<DictItem> itemWrapper = Wrappers.lambdaQuery();
            itemWrapper.eq(DictItem::getDictTypeId, dto.getId());
            
            List<DictItem> items = dictItemMapper.selectList(itemWrapper);
            if (!items.isEmpty()) {
                items.forEach(item -> {
                    item.setDictTypeCode(dto.getCode());
                    dictItemMapper.updateById(item);
                });
            }
        }
        
        return convertToTypeVO(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"dict:type", "dict:type:code", "dict:items", "dict:all"}, allEntries = true)
    public boolean deleteDictType(String id) {
        // 检查是否存在
        if (dictTypeMapper.selectById(id) == null) {
            throw new IllegalArgumentException("字典类型不存在");
        }
        
        // 删除字典项
        LambdaQueryWrapper<DictItem> itemWrapper = Wrappers.lambdaQuery();
        itemWrapper.eq(DictItem::getDictTypeId, id);
        dictItemMapper.delete(itemWrapper);
        
        // 删除字典类型
        return dictTypeMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"dict:type", "dict:type:code", "dict:items", "dict:all"}, allEntries = true)
    public boolean batchDeleteDictType(String[] ids) {
        // 删除字典项
        LambdaQueryWrapper<DictItem> itemWrapper = Wrappers.lambdaQuery();
        itemWrapper.in(DictItem::getDictTypeId, Arrays.asList(ids));
        dictItemMapper.delete(itemWrapper);
        
        // 删除字典类型
        return dictTypeMapper.deleteBatchIds(Arrays.asList(ids)) > 0;
    }

    @Override
    public IPage<DictItemVO> pageDictItem(Page<DictItemVO> page, String dictTypeId, String dictTypeCode, 
                                         String label, Integer status) {
        // 查询条件
        LambdaQueryWrapper<DictItem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(StringUtils.isNotBlank(dictTypeId), DictItem::getDictTypeId, dictTypeId)
                .eq(StringUtils.isNotBlank(dictTypeCode), DictItem::getDictTypeCode, dictTypeCode)
                .like(StringUtils.isNotBlank(label), DictItem::getLabel, label)
                .eq(status != null, DictItem::getStatus, status)
                .orderByAsc(DictItem::getSort);

        // 分页查询
        Page<DictItem> dictItemPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<DictItem> dictItemIPage = dictItemMapper.selectPage(dictItemPage, queryWrapper);

        // 转换为VO
        IPage<DictItemVO> result = new Page<>(dictItemIPage.getCurrent(), dictItemIPage.getSize(), dictItemIPage.getTotal());
        result.setRecords(dictItemIPage.getRecords().stream().map(this::convertToItemVO).collect(Collectors.toList()));
        
        return result;
    }

    @Override
    @Cacheable(value = "dict:items:id", key = "#id", unless = "#result == null")
    public DictItemVO getDictItemById(String id) {
        DictItem dictItem = dictItemMapper.selectById(id);
        if (dictItem == null) {
            return null;
        }
        return convertToItemVO(dictItem);
    }

    @Override
    @Cacheable(value = "dict:items:code", key = "#dictTypeCode", unless = "#result == null || #result.isEmpty()")
    public List<DictItemVO> getDictItemsByTypeCode(String dictTypeCode) {
        if (StringUtils.isBlank(dictTypeCode)) {
            return Collections.emptyList();
        }
        
        List<DictItem> items = dictItemMapper.selectByDictTypeCode(dictTypeCode);
        return items.stream().map(this::convertToItemVO).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "dict:items:typeid", key = "#dictTypeId", unless = "#result == null || #result.isEmpty()")
    public List<DictItemVO> getDictItemsByTypeId(String dictTypeId) {
        if (StringUtils.isBlank(dictTypeId)) {
            return Collections.emptyList();
        }
        
        LambdaQueryWrapper<DictItem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DictItem::getDictTypeId, dictTypeId)
                   .eq(DictItem::getStatus, 1)
                   .orderByAsc(DictItem::getSort);
        
        List<DictItem> items = dictItemMapper.selectList(queryWrapper);
        return items.stream().map(this::convertToItemVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"dict:items", "dict:all"}, allEntries = true)
    public DictItemVO addDictItem(DictItemDTO dto) {
        // 检查字典类型是否存在
        DictType dictType = dictTypeMapper.selectById(dto.getDictTypeId());
        if (dictType == null) {
            throw new IllegalArgumentException("字典类型不存在");
        }
        
        // 检查字典值是否重复
        LambdaQueryWrapper<DictItem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DictItem::getDictTypeId, dto.getDictTypeId())
                   .eq(DictItem::getValue, dto.getValue());
        if (dictItemMapper.selectCount(queryWrapper) > 0) {
            throw new IllegalArgumentException("字典值已存在");
        }

        // 实体转换
        DictItem dictItem = new DictItem();
        BeanUtils.copyProperties(dto, dictItem);
        dictItem.setDictTypeCode(dictType.getCode());
        
        // 保存
        dictItemMapper.insert(dictItem);
        
        return convertToItemVO(dictItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"dict:items", "dict:all"}, allEntries = true)
    public DictItemVO updateDictItem(DictItemDTO dto) {
        // 检查是否存在
        DictItem oldItem = dictItemMapper.selectById(dto.getId());
        if (oldItem == null) {
            throw new IllegalArgumentException("字典项不存在");
        }
        
        // 检查字典类型是否存在
        DictType dictType = dictTypeMapper.selectById(dto.getDictTypeId());
        if (dictType == null) {
            throw new IllegalArgumentException("字典类型不存在");
        }
        
        // 检查字典值是否重复
        LambdaQueryWrapper<DictItem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DictItem::getDictTypeId, dto.getDictTypeId())
                   .eq(DictItem::getValue, dto.getValue())
                   .ne(DictItem::getId, dto.getId());
        if (dictItemMapper.selectCount(queryWrapper) > 0) {
            throw new IllegalArgumentException("字典值已存在");
        }

        // 实体转换
        DictItem dictItem = new DictItem();
        BeanUtils.copyProperties(dto, dictItem);
        dictItem.setDictTypeCode(dictType.getCode());
        
        // 更新
        dictItemMapper.updateById(dictItem);
        
        return convertToItemVO(dictItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"dict:items", "dict:all"}, allEntries = true)
    public boolean deleteDictItem(String id) {
        // 检查是否存在
        if (dictItemMapper.selectById(id) == null) {
            throw new IllegalArgumentException("字典项不存在");
        }
        
        // 删除字典项
        return dictItemMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"dict:items", "dict:all"}, allEntries = true)
    public boolean batchDeleteDictItem(String[] ids) {
        return dictItemMapper.deleteBatchIds(Arrays.asList(ids)) > 0;
    }

    @Override
    @Cacheable(value = "dict:map", key = "#dictTypeCode", unless = "#result == null || #result.isEmpty()")
    public Map<String, String> getDictMap(String dictTypeCode) {
        List<DictItemVO> itemList = getDictItemsByTypeCode(dictTypeCode);
        if (itemList.isEmpty()) {
            return Collections.emptyMap();
        }
        
        Map<String, String> result = new HashMap<>(itemList.size());
        itemList.forEach(item -> result.put(item.getValue(), item.getLabel()));
        return result;
    }

    @Override
    @CacheEvict(value = {"dict:type", "dict:type:code", "dict:items", "dict:all", "dict:map"}, allEntries = true)
    public void refreshCache() {
        log.info("刷新字典缓存");
    }

    /**
     * 字典类型转换为视图对象
     *
     * @param dictType 字典类型
     * @return 字典类型视图对象
     */
    private DictTypeVO convertToTypeVO(DictType dictType) {
        if (dictType == null) {
            return null;
        }
        DictTypeVO vo = new DictTypeVO();
        BeanUtils.copyProperties(dictType, vo);
        return vo;
    }
    
    /**
     * 字典项转换为视图对象
     *
     * @param dictItem 字典项
     * @return 字典项视图对象
     */
    private DictItemVO convertToItemVO(DictItem dictItem) {
        if (dictItem == null) {
            return null;
        }
        DictItemVO vo = new DictItemVO();
        BeanUtils.copyProperties(dictItem, vo);
        return vo;
    }
}