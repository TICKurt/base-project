package com.example.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.business.domain.dto.TestItemDTO;
import com.example.business.domain.vo.TestItemVO;

/**
 * 测试项目服务接口
 * 
 * @author example
 */
public interface TestItemService {

    /**
     * 分页查询测试项目
     *
     * @param page 分页参数
     * @param name 项目名称（可选）
     * @return 分页数据
     */
    Page<TestItemVO> page(Page<TestItemVO> page, String name);

    /**
     * 根据ID获取测试项目
     *
     * @param id 主键ID
     * @return 测试项目
     */
    TestItemVO getById(Integer id);

    /**
     * 新增测试项目
     *
     * @param dto 测试项目DTO
     * @return 新增后的测试项目
     */
    TestItemVO add(TestItemDTO dto);

    /**
     * 修改测试项目
     *
     * @param dto 测试项目DTO
     * @return 修改后的测试项目
     */
    TestItemVO update(TestItemDTO dto);

    /**
     * 删除测试项目
     *
     * @param id 主键ID
     * @return 是否成功
     */
    boolean delete(Integer id);
} 