package com.example.common.controller;

import com.github.pagehelper.PageInfo;
import com.example.auth.annotation.RequiresPermission;
import com.example.auth.utils.ResponseResult;
import com.example.common.model.dto.DictItemDTO;
import com.example.common.model.dto.DictTypeDTO;
import com.example.common.model.vo.DictItemVO;
import com.example.common.model.vo.DictTypeVO;
import com.example.common.service.DictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 字典控制器
 * 
 * @author example
 */
@RestController
@RequestMapping("/dict")
@Slf4j
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    // =============================== 字典类型操作 ===============================

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
    @GetMapping("/type/page")
    @RequiresPermission("system:dict:list")
    public ResponseResult<PageInfo<DictTypeVO>> pageDictType(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Integer status) {
        PageInfo<DictTypeVO> result = dictService.pageDictType(pageNum, pageSize, name, code, status);
        return ResponseResult.success(result);
    }

    /**
     * 根据ID获取字典类型
     *
     * @param id 主键ID
     * @return 字典类型
     */
    @GetMapping("/type/{id}")
    @RequiresPermission("system:dict:query")
    public ResponseResult<DictTypeVO> getDictTypeById(@PathVariable String id) {
        DictTypeVO dictType = dictService.getDictTypeById(id);
        return ResponseResult.success(dictType);
    }

    /**
     * 根据编码获取字典类型
     *
     * @param code 字典编码
     * @return 字典类型
     */
    @GetMapping("/type/code/{code}")
    public ResponseResult<DictTypeVO> getDictTypeByCode(@PathVariable String code) {
        DictTypeVO dictType = dictService.getDictTypeByCode(code);
        return ResponseResult.success(dictType);
    }

    /**
     * 新增字典类型
     *
     * @param dto 字典类型DTO
     * @return 新增后的字典类型
     */
    @PostMapping("/type")
    @RequiresPermission("system:dict:add")
    public ResponseResult<DictTypeVO> addDictType(@Valid @RequestBody DictTypeDTO dto) {
        DictTypeVO dictType = dictService.addDictType(dto);
        return ResponseResult.success(dictType);
    }

    /**
     * 修改字典类型
     *
     * @param dto 字典类型DTO
     * @return 修改后的字典类型
     */
    @PutMapping("/type")
    @RequiresPermission("system:dict:edit")
    public ResponseResult<DictTypeVO> updateDictType(@Valid @RequestBody DictTypeDTO dto) {
        DictTypeVO dictType = dictService.updateDictType(dto);
        return ResponseResult.success(dictType);
    }

    /**
     * 删除字典类型
     *
     * @param id 主键ID
     * @return 是否成功
     */
    @DeleteMapping("/type/{id}")
    @RequiresPermission("system:dict:delete")
    public ResponseResult<Boolean> deleteDictType(@PathVariable String id) {
        boolean result = dictService.deleteDictType(id);
        return ResponseResult.success(result);
    }

    /**
     * 批量删除字典类型
     *
     * @param ids ID数组
     * @return 是否成功
     */
    @DeleteMapping("/type/batch")
    @RequiresPermission("system:dict:delete")
    public ResponseResult<Boolean> batchDeleteDictType(@RequestBody String[] ids) {
        boolean result = dictService.batchDeleteDictType(ids);
        return ResponseResult.success(result);
    }

    // =============================== 字典项操作 ===============================

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
    @GetMapping("/item/page")
    @RequiresPermission("system:dict:list")
    public ResponseResult<PageInfo<DictItemVO>> pageDictItem(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String dictTypeId,
            @RequestParam(required = false) String dictTypeCode,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) Integer status) {
        PageInfo<DictItemVO> result = dictService.pageDictItem(pageNum, pageSize, dictTypeId, dictTypeCode, label, status);
        return ResponseResult.success(result);
    }

    /**
     * 根据ID获取字典项
     *
     * @param id 主键ID
     * @return 字典项
     */
    @GetMapping("/item/{id}")
    @RequiresPermission("system:dict:query")
    public ResponseResult<DictItemVO> getDictItemById(@PathVariable String id) {
        DictItemVO dictItem = dictService.getDictItemById(id);
        return ResponseResult.success(dictItem);
    }

    /**
     * 根据字典类型编码查询字典项列表
     *
     * @param dictTypeCode 字典类型编码
     * @return 字典项列表
     */
    @GetMapping("/data/{dictTypeCode}")
    public ResponseResult<List<DictItemVO>> getDictItemsByTypeCode(@PathVariable String dictTypeCode) {
        List<DictItemVO> dictItems = dictService.getDictItemsByTypeCode(dictTypeCode);
        return ResponseResult.success(dictItems);
    }

    /**
     * 根据字典类型编码获取键值Map
     *
     * @param dictTypeCode 字典类型编码
     * @return 键值Map
     */
    @GetMapping("/map/{dictTypeCode}")
    public ResponseResult<Map<String, String>> getDictMap(@PathVariable String dictTypeCode) {
        Map<String, String> dictMap = dictService.getDictMap(dictTypeCode);
        return ResponseResult.success(dictMap);
    }

    /**
     * 根据字典类型ID查询字典项列表
     *
     * @param dictTypeId 字典类型ID
     * @return 字典项列表
     */
    @GetMapping("/item/type/{dictTypeId}")
    @RequiresPermission("system:dict:query")
    public ResponseResult<List<DictItemVO>> getDictItemsByTypeId(@PathVariable String dictTypeId) {
        List<DictItemVO> dictItems = dictService.getDictItemsByTypeId(dictTypeId);
        return ResponseResult.success(dictItems);
    }

    /**
     * 新增字典项
     *
     * @param dto 字典项DTO
     * @return 新增后的字典项
     */
    @PostMapping("/item")
    @RequiresPermission("system:dict:add")
    public ResponseResult<DictItemVO> addDictItem(@Valid @RequestBody DictItemDTO dto) {
        DictItemVO dictItem = dictService.addDictItem(dto);
        return ResponseResult.success(dictItem);
    }

    /**
     * 修改字典项
     *
     * @param dto 字典项DTO
     * @return 修改后的字典项
     */
    @PutMapping("/item")
    @RequiresPermission("system:dict:edit")
    public ResponseResult<DictItemVO> updateDictItem(@Valid @RequestBody DictItemDTO dto) {
        DictItemVO dictItem = dictService.updateDictItem(dto);
        return ResponseResult.success(dictItem);
    }

    /**
     * 删除字典项
     *
     * @param id 主键ID
     * @return 是否成功
     */
    @DeleteMapping("/item/{id}")
    @RequiresPermission("system:dict:delete")
    public ResponseResult<Boolean> deleteDictItem(@PathVariable String id) {
        boolean result = dictService.deleteDictItem(id);
        return ResponseResult.success(result);
    }

    /**
     * 批量删除字典项
     *
     * @param ids ID数组
     * @return 是否成功
     */
    @DeleteMapping("/item/batch")
    @RequiresPermission("system:dict:delete")
    public ResponseResult<Boolean> batchDeleteDictItem(@RequestBody String[] ids) {
        boolean result = dictService.batchDeleteDictItem(ids);
        return ResponseResult.success(result);
    }

    /**
     * 刷新缓存
     *
     * @return 是否成功
     */
    @DeleteMapping("/refresh")
    @RequiresPermission("system:dict:edit")
    public ResponseResult<Void> refreshCache() {
        dictService.refreshCache();
        return ResponseResult.success();
    }
} 