package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.model.DictItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典项数据访问接口
 * 
 * @author example
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItem> {
    
    /**
     * 根据字典类型编码查询字典项
     *
     * @param dictTypeCode 字典类型编码
     * @return 字典项列表
     */
    @Select("SELECT * FROM sys_dict_item WHERE dict_type_code = #{dictTypeCode} AND status = 1 AND is_deleted = 0 ORDER BY sort")
    List<DictItem> selectByDictTypeCode(@Param("dictTypeCode") String dictTypeCode);
} 