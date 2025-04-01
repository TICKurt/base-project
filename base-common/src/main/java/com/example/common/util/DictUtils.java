package com.example.common.util;

import com.example.common.model.vo.DictItemVO;
import com.example.common.service.DictService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * 提供便捷的字典操作方法
 * 
 * @author example
 */
@Component
public class DictUtils {

    private static DictService dictService;

    public DictUtils(DictService dictService) {
        DictUtils.dictService = dictService;
    }

    /**
     * 根据字典类型编码和字典值获取字典标签
     *
     * @param dictTypeCode 字典类型编码
     * @param value 字典值
     * @return 字典标签
     */
    public static String getDictLabel(String dictTypeCode, String value) {
        return getDictLabel(dictTypeCode, value, "");
    }

    /**
     * 根据字典类型编码和字典值获取字典标签，如果未找到则返回默认值
     *
     * @param dictTypeCode 字典类型编码
     * @param value 字典值
     * @param defaultValue 默认值
     * @return 字典标签
     */
    public static String getDictLabel(String dictTypeCode, String value, String defaultValue) {
        if (dictService == null) {
            return defaultValue;
        }
        
        Map<String, String> dictMap = dictService.getDictMap(dictTypeCode);
        return dictMap.getOrDefault(value, defaultValue);
    }

    /**
     * 根据字典类型编码和字典标签获取字典值
     *
     * @param dictTypeCode 字典类型编码
     * @param label 字典标签
     * @return 字典值
     */
    public static String getDictValue(String dictTypeCode, String label) {
        return getDictValue(dictTypeCode, label, "");
    }

    /**
     * 根据字典类型编码和字典标签获取字典值，如果未找到则返回默认值
     *
     * @param dictTypeCode 字典类型编码
     * @param label 字典标签
     * @param defaultValue 默认值
     * @return 字典值
     */
    public static String getDictValue(String dictTypeCode, String label, String defaultValue) {
        if (dictService == null) {
            return defaultValue;
        }
        
        List<DictItemVO> dictItems = dictService.getDictItemsByTypeCode(dictTypeCode);
        for (DictItemVO item : dictItems) {
            if (item.getLabel().equals(label)) {
                return item.getValue();
            }
        }
        return defaultValue;
    }

    /**
     * 获取字典下拉选项数据
     *
     * @param dictTypeCode 字典类型编码
     * @return 下拉选项数据
     */
    public static List<Map<String, Object>> getDictOptions(String dictTypeCode) {
        if (dictService == null) {
            return Collections.emptyList();
        }
        
        List<DictItemVO> dictItems = dictService.getDictItemsByTypeCode(dictTypeCode);
        List<Map<String, Object>> options = new ArrayList<>();
        
        for (DictItemVO item : dictItems) {
            Map<String, Object> option = new HashMap<>();
            option.put("value", item.getValue());
            option.put("label", item.getLabel());
            option.put("cssClass", item.getCssClass() == null ? "" : item.getCssClass());
            option.put("listClass", item.getListClass() == null ? "" : item.getListClass());
            option.put("isDefault", item.getIsDefault());
            options.add(option);
        }
        
        return options;
    }
} 