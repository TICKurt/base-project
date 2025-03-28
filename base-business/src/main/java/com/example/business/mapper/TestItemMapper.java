package com.example.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.business.domain.entity.TestItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试项目数据访问层
 * 
 * @author example
 */
@Mapper
public interface TestItemMapper extends BaseMapper<TestItem> {
} 