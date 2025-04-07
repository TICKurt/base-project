package com.example.workflow.service;

import com.example.workflow.model.dto.ProcessDefinitionDTO;
import com.example.workflow.model.query.ProcessDefinitionQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 流程定义服务接口
 *
 * @author Author
 * @version 1.0
 */
public interface IProcessDefinitionService {

    /**
     * 部署流程定义
     *
     * @param file BPMN文件
     * @param name 流程名称
     * @param category 流程分类
     * @return 部署ID
     */
    String deploy(MultipartFile file, String name, String category);

    /**
     * 分页查询流程定义列表
     *
     * @param query 查询参数
     * @return 流程定义列表
     */
    List<ProcessDefinitionDTO> list(ProcessDefinitionQuery query);

    /**
     * 获取流程定义详情
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程定义详情
     */
    ProcessDefinitionDTO getById(String processDefinitionId);

    /**
     * 删除流程定义
     *
     * @param deploymentId 部署ID
     * @param cascade 是否级联删除（删除相关的流程实例、任务等）
     */
    void deleteByDeploymentId(String deploymentId, boolean cascade);

    /**
     * 激活流程定义
     *
     * @param processDefinitionId 流程定义ID
     */
    void activate(String processDefinitionId);

    /**
     * 挂起流程定义
     *
     * @param processDefinitionId 流程定义ID
     */
    void suspend(String processDefinitionId);

    /**
     * 获取流程定义的XML内容
     *
     * @param processDefinitionId 流程定义ID
     * @return XML内容
     */
    String getProcessDefinitionXML(String processDefinitionId);

    /**
     * 获取流程图
     *
     * @param processDefinitionId 流程定义ID
     * @return 图片字节数组
     */
    byte[] getProcessDiagram(String processDefinitionId);
} 