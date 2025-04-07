package com.example.workflow.service;

import com.example.workflow.model.dto.FormDesignDTO;
import com.example.workflow.model.query.FormDesignQuery;

import java.util.List;
import java.util.Map;

/**
 * 表单设计服务接口
 *
 * @author Author
 * @version 1.0
 */
public interface IFormDesignService {

    /**
     * 创建表单设计
     *
     * @param formDesign 表单设计信息
     * @return 表单设计ID
     */
    String create(FormDesignDTO formDesign);

    /**
     * 更新表单设计
     *
     * @param formDesign 表单设计信息
     */
    void update(FormDesignDTO formDesign);

    /**
     * 删除表单设计
     *
     * @param formKey 表单标识
     */
    void delete(String formKey);

    /**
     * 查询表单设计列表
     *
     * @param query 查询参数
     * @return 表单设计列表
     */
    List<FormDesignDTO> list(FormDesignQuery query);

    /**
     * 获取表单设计详情
     *
     * @param formKey 表单标识
     * @return 表单设计详情
     */
    FormDesignDTO getByFormKey(String formKey);

    /**
     * 发布表单设计
     *
     * @param formKey 表单标识
     */
    void publish(String formKey);

    /**
     * 获取表单数据
     *
     * @param formKey 表单标识
     * @param businessKey 业务标识
     * @return 表单数据
     */
    Map<String, Object> getFormData(String formKey, String businessKey);

    /**
     * 保存表单数据
     *
     * @param formKey 表单标识
     * @param businessKey 业务标识
     * @param formData 表单数据
     */
    void saveFormData(String formKey, String businessKey, Map<String, Object> formData);

    /**
     * 获取表单渲染数据
     *
     * @param formKey 表单标识
     * @param businessKey 业务标识
     * @return 表单渲染数据
     */
    Map<String, Object> getFormRenderData(String formKey, String businessKey);

    /**
     * 验证表单数据
     *
     * @param formKey 表单标识
     * @param formData 表单数据
     * @return 验证结果
     */
    Map<String, String> validateFormData(String formKey, Map<String, Object> formData);

    /**
     * 获取表单历史数据
     *
     * @param formKey 表单标识
     * @param businessKey 业务标识
     * @return 表单历史数据列表
     */
    List<Map<String, Object>> getFormHistoryData(String formKey, String businessKey);
} 