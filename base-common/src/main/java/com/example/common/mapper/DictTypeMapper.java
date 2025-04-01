package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.model.DictType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典类型数据访问接口
 * 
 * @author example
 */
@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {
    
} 