package com.example.workflow.service;

import com.example.common.model.FileInfo;
import com.example.workflow.model.dto.BpmnDeployDTO;
import com.example.workflow.model.vo.ProcessDefinitionVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 流程定义服务接口
 *
 * @author Author
 * @version 1.0
 */
public interface ProcessDefinitionService {

    /**
     * 部署流程定义
     *
     * @param name     流程定义名称
     * @param category 流程定义分类
     * @param file     流程定义文件
     * @return 部署ID
     */
    String deploy(String name, String category, MultipartFile file);

    /**
     * 通过输入流部署流程定义
     *
     * @param name        流程定义名称
     * @param category    流程定义分类
     * @param resourceName 资源名称
     * @param inputStream 输入流
     * @return 部署ID
     */
    String deployByInputStream(String name, String category, String resourceName, InputStream inputStream);

    /**
     * 通过BPMN XML内容部署流程定义
     *
     * @param deployDTO BPMN部署DTO，包含名称、分类、XML内容等
     * @return 部署ID
     */
    String deployByBpmnXml(BpmnDeployDTO deployDTO);

    /**
     * 删除流程定义
     *
     * @param deploymentId 部署ID
     * @param cascade     是否级联删除
     */
    void deleteByDeploymentId(String deploymentId, boolean cascade);

    /**
     * 获取流程定义列表
     *
     * @param category 流程定义分类
     * @param key      流程定义标识
     * @param name     流程定义名称
     * @param tenantId 租户ID
     * @return 流程定义列表
     */
    List<ProcessDefinitionVO> list(String category, String key, String name, String tenantId);

    /**
     * 获取流程定义
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程定义
     */
    ProcessDefinitionVO getById(String processDefinitionId);

    /**
     * 获取最新版本的流程定义
     *
     * @param processDefinitionKey 流程定义标识
     * @return 流程定义
     */
    ProcessDefinitionVO getLatestByKey(String processDefinitionKey);

    /**
     * 获取流程定义XML
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程定义XML
     */
    String getProcessDefinitionXML(String processDefinitionId);

    /**
     * 获取流程图
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程图文件信息
     */
    FileInfo getProcessDiagram(String processDefinitionId);

    /**
     * 获取流程资源
     *
     * @param deploymentId 部署ID
     * @param resourceName 资源名称
     * @return 资源字节数组
     */
    byte[] getResource(String deploymentId, String resourceName);

    /**
     * 验证BPMN文件
     *
     * @param file BPMN文件
     * @return 是否有效
     */
    boolean validateBpmnFile(MultipartFile file);

    /**
     * 验证BPMN XML内容
     *
     * @param bpmnXml BPMN XML内容
     * @return 是否有效
     */
    boolean validateBpmnXml(String bpmnXml);

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
} 