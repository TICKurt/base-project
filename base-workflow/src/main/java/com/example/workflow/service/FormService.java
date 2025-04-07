package com.example.workflow.service;

import com.example.workflow.model.dto.FormDesignDTO;
import com.example.workflow.model.query.FormDesignQuery;

import java.util.List;
import java.util.Map;

/**
 * 表单服务接口
 *
 * @author Author
 * @version 1.0
 */
public interface FormService {

    /**
     * 创建表单定义
     *
     * @param formDesign 表单定义信息
     * @return 表单定义ID
     */
    String createFormDesign(FormDesignDTO formDesign);

    /**
     * 更新表单定义
     *
     * @param formDesign 表单定义信息
     */
    void updateFormDesign(FormDesignDTO formDesign);

    /**
     * 删除表单定义
     *
     * @param formKey 表单标识
     */
    void deleteFormDesign(String formKey);

    /**
     * 获取表单定义列表
     *
     * @param query 查询条件
     * @return 表单定义列表
     */
    List<FormDesignDTO> listFormDesigns(FormDesignQuery query);

    /**
     * 获取表单定义详情
     *
     * @param formKey 表单标识
     * @return 表单定义详情
     */
    FormDesignDTO getFormDesignByKey(String formKey);

    /**
     * 发布表单定义
     *
     * @param formKey 表单标识
     */
    void publishFormDesign(String formKey);

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
     * @return 验证结果（key为字段名，value为错误信息）
     */
    Map<String, String> validateFormData(String formKey, Map<String, Object> formData);

    /**
     * 获取表单历史数据
     *
     * @param formKey 表单标识
     * @param businessKey 业务标识
     * @return 历史数据列表
     */
    List<Map<String, Object>> getFormHistoryData(String formKey, String businessKey);

    /**
     * 获取流程任务表单
     *
     * @param taskId 任务ID
     * @return 表单定义详情
     */
    FormDesignDTO getTaskForm(String taskId);

    /**
     * 获取流程启动表单
     *
     * @param processDefinitionId 流程定义ID
     * @return 表单定义详情
     */
    FormDesignDTO getStartForm(String processDefinitionId);

    /**
     * 提交任务表单
     *
     * @param taskId 任务ID
     * @param formData 表单数据
     */
    void submitTaskForm(String taskId, Map<String, Object> formData);

    /**
     * 提交启动表单
     *
     * @param processDefinitionId 流程定义ID
     * @param formData 表单数据
     * @return 流程实例ID
     */
    String submitStartForm(String processDefinitionId, Map<String, Object> formData);
} 