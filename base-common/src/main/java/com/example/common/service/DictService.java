package com.example.common.service;

import com.github.pagehelper.PageInfo;
import com.example.common.model.dto.DictItemDTO;
import com.example.common.model.dto.DictTypeDTO;
import com.example.common.model.vo.DictItemVO;
import com.example.common.model.vo.DictTypeVO;

import java.util.List;
import java.util.Map;

/**
 * 字典服务接口
 * 
 * @author example
 */
public interface DictService {

    /**
     * 分页查询字典类型
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param name 字典名称（可选）
     * @param code 字典编码（可选）
     * @param status 状态（可选）
     * @return 分页数据
     */
    PageInfo<DictTypeVO> pageDictType(int pageNum, int pageSize, String name, String code, Integer status);

    /**
     * 根据ID获取字典类型
     *
     * @param id 主键ID
     * @return 字典类型
     */
    DictTypeVO getDictTypeById(String id);

    /**
     * 根据编码获取字典类型
     *
     * @param code 字典编码
     * @return 字典类型
     */
    DictTypeVO getDictTypeByCode(String code);

    /**
     * 新增字典类型
     *
     * @param dto 字典类型DTO
     * @return 新增后的字典类型
     */
    DictTypeVO addDictType(DictTypeDTO dto);

    /**
     * 修改字典类型
     *
     * @param dto 字典类型DTO
     * @return 修改后的字典类型
     */
    DictTypeVO updateDictType(DictTypeDTO dto);

    /**
     * 删除字典类型
     *
     * @param id 主键ID
     * @return 是否成功
     */
    boolean deleteDictType(String id);

    /**
     * 批量删除字典类型
     *
     * @param ids ID数组
     * @return 是否成功
     */
    boolean batchDeleteDictType(String[] ids);

    /**
     * 分页查询字典项
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param dictTypeId 字典类型ID（可选）
     * @param dictTypeCode 字典类型编码（可选）
     * @param label 字典标签（可选）
     * @param status 状态（可选）
     * @return 分页数据
     */
    PageInfo<DictItemVO> pageDictItem(int pageNum, int pageSize, String dictTypeId, String dictTypeCode, String label, Integer status);

    /**
     * 根据ID获取字典项
     *
     * @param id 主键ID
     * @return 字典项
     */
    DictItemVO getDictItemById(String id);

    /**
     * 根据字典类型编码查询字典项列表
     *
     * @param dictTypeCode 字典类型编码
     * @return 字典项列表
     */
    List<DictItemVO> getDictItemsByTypeCode(String dictTypeCode);

    /**
     * 根据字典类型ID查询字典项列表
     *
     * @param dictTypeId 字典类型ID
     * @return 字典项列表
     */
    List<DictItemVO> getDictItemsByTypeId(String dictTypeId);

    /**
     * 新增字典项
     *
     * @param dto 字典项DTO
     * @return 新增后的字典项
     */
    DictItemVO addDictItem(DictItemDTO dto);

    /**
     * 修改字典项
     *
     * @param dto 字典项DTO
     * @return 修改后的字典项
     */
    DictItemVO updateDictItem(DictItemDTO dto);

    /**
     * 删除字典项
     *
     * @param id 主键ID
     * @return 是否成功
     */
    boolean deleteDictItem(String id);

    /**
     * 批量删除字典项
     *
     * @param ids ID数组
     * @return 是否成功
     */
    boolean batchDeleteDictItem(String[] ids);

    /**
     * 根据字典类型编码获取键值Map
     *
     * @param dictTypeCode 字典类型编码
     * @return 键值Map
     */
    Map<String, String> getDictMap(String dictTypeCode);
    
    /**
     * 刷新缓存
     */
    void refreshCache();
} 