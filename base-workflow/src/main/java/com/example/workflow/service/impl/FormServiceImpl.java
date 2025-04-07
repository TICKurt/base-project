package com.example.workflow.service.impl;

import com.example.workflow.model.dto.FormDesignDTO;
import com.example.workflow.model.query.FormDesignQuery;
import com.example.workflow.service.FormService;
import com.example.workflow.utils.FormDataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.form.api.FormDefinition;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 表单服务实现类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final org.flowable.engine.FormService flowableFormService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createFormDesign(FormDesignDTO formDesign) {
        // 验证表单定义
        validateFormDesign(formDesign);

        // 生成表单标识
        if (StringUtils.isBlank(formDesign.getFormKey())) {
            formDesign.setFormKey("FORM_" + UUID.randomUUID().toString().replace("-", ""));
        }

        // 设置创建信息
        formDesign.setVersion(1);
        formDesign.setCreateTime(new Date());
        formDesign.setPublished(false);

        // TODO: 保存到数据库

        return formDesign.getFormKey();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormDesign(FormDesignDTO formDesign) {
        // 验证表单定义
        validateFormDesign(formDesign);

        // 检查表单是否存在
        FormDesignDTO existingForm = getFormDesignByKey(formDesign.getFormKey());
        if (existingForm == null) {
            throw new RuntimeException("表单不存在");
        }

        // 检查是否已发布
        if (Boolean.TRUE.equals(existingForm.getPublished())) {
            throw new RuntimeException("表单已发布，不能修改");
        }

        // 设置更新信息
        formDesign.setUpdateTime(new Date());

        // TODO: 更新到数据库
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFormDesign(String formKey) {
        // 检查表单是否存在
        FormDesignDTO existingForm = getFormDesignByKey(formKey);
        if (existingForm == null) {
            return;
        }

        // 检查是否已发布
        if (Boolean.TRUE.equals(existingForm.getPublished())) {
            throw new RuntimeException("表单已发布，不能删除");
        }

        // TODO: 从数据库中删除
    }

    @Override
    public List<FormDesignDTO> listFormDesigns(FormDesignQuery query) {
        // TODO: 从数据库查询
        return new ArrayList<>();
    }

    @Override
    public FormDesignDTO getFormDesignByKey(String formKey) {
        if (StringUtils.isBlank(formKey)) {
            return null;
        }

        // TODO: 从数据库查询
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishFormDesign(String formKey) {
        // 检查表单是否存在
        FormDesignDTO existingForm = getFormDesignByKey(formKey);
        if (existingForm == null) {
            throw new RuntimeException("表单不存在");
        }

        // 设置发布信息
        existingForm.setPublished(true);
        existingForm.setPublishTime(new Date());

        // TODO: 更新到数据库
    }

    @Override
    public Map<String, Object> getFormData(String formKey, String businessKey) {
        // TODO: 从数据库查询表单数据
        return new HashMap<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFormData(String formKey, String businessKey, Map<String, Object> formData) {
        // 检查表单是否存在
        FormDesignDTO formDesign = getFormDesignByKey(formKey);
        if (formDesign == null) {
            throw new RuntimeException("表单不存在");
        }

        // 验证表单数据
        Map<String, String> errors = FormDataUtils.validateFormData(formDesign, formData);
        if (!errors.isEmpty()) {
            throw new RuntimeException("表单数据验证失败：" + FormDataUtils.toJson(errors));
        }

        // TODO: 保存到数据库
    }

    @Override
    public Map<String, Object> getFormRenderData(String formKey, String businessKey) {
        // 获取表单定义
        FormDesignDTO formDesign = getFormDesignByKey(formKey);
        if (formDesign == null) {
            throw new RuntimeException("表单不存在");
        }

        // 获取表单数据
        Map<String, Object> formData = getFormData(formKey, businessKey);

        // 获取渲染数据
        return FormDataUtils.getFormRenderData(formDesign, formData);
    }

    @Override
    public Map<String, String> validateFormData(String formKey, Map<String, Object> formData) {
        // 获取表单定义
        FormDesignDTO formDesign = getFormDesignByKey(formKey);
        if (formDesign == null) {
            throw new RuntimeException("表单不存在");
        }

        // 验证表单数据
        return FormDataUtils.validateFormData(formDesign, formData);
    }

    @Override
    public List<Map<String, Object>> getFormHistoryData(String formKey, String businessKey) {
        // TODO: 从数据库查询历史数据
        return new ArrayList<>();
    }

    @Override
    public FormDesignDTO getTaskForm(String taskId) {
        // 获取任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }

        // 获取表单定义
        String formKey = task.getFormKey();
        if (StringUtils.isBlank(formKey)) {
            return null;
        }

        return getFormDesignByKey(formKey);
    }

    @Override
    public FormDesignDTO getStartForm(String processDefinitionId) {
        // 获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        if (processDefinition == null) {
            throw new RuntimeException("流程定义不存在");
        }

        // 获取启动表单定义
        String formKey = flowableFormService.getStartFormKey(processDefinitionId);
        if (StringUtils.isBlank(formKey)) {
            return null;
        }

        return getFormDesignByKey(formKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitTaskForm(String taskId, Map<String, Object> formData) {
        // 获取任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }

        // 获取表单定义
        String formKey = task.getFormKey();
        if (StringUtils.isBlank(formKey)) {
            throw new RuntimeException("任务没有关联表单");
        }

        // 验证表单数据
        Map<String, String> errors = validateFormData(formKey, formData);
        if (!errors.isEmpty()) {
            throw new RuntimeException("表单数据验证失败：" + FormDataUtils.toJson(errors));
        }

        // 保存表单数据
        saveFormData(formKey, task.getProcessInstanceId(), formData);

        // 提交任务表单
        taskService.completeTaskWithForm(taskId, formKey, null, formData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String submitStartForm(String processDefinitionId, Map<String, Object> formData) {
        // 获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        if (processDefinition == null) {
            throw new RuntimeException("流程定义不存在");
        }

        // 获取启动表单定义
        String formKey = flowableFormService.getStartFormKey(processDefinitionId);
        if (StringUtils.isBlank(formKey)) {
            throw new RuntimeException("流程定义没有启动表单");
        }

        // 验证表单数据
        Map<String, String> errors = validateFormData(formKey, formData);
        if (!errors.isEmpty()) {
            throw new RuntimeException("表单数据验证失败：" + FormDataUtils.toJson(errors));
        }

        // 保存表单数据（使用流程定义ID作为业务标识）
        saveFormData(formKey, processDefinitionId, formData);

        // 通过RuntimeService启动流程
        String businessKey = UUID.randomUUID().toString();
        return runtimeService.createProcessInstanceBuilder()
                .processDefinitionId(processDefinitionId)
                .businessKey(businessKey)
                .variables(formData)
                .start()
                .getId();
    }

    /**
     * 验证表单定义
     */
    private void validateFormDesign(FormDesignDTO formDesign) {
        if (formDesign == null) {
            throw new RuntimeException("表单定义不能为空");
        }
        if (StringUtils.isBlank(formDesign.getName())) {
            throw new RuntimeException("表单名称不能为空");
        }
        if (formDesign.getFields() == null || formDesign.getFields().isEmpty()) {
            throw new RuntimeException("表单字段不能为空");
        }
    }
} 