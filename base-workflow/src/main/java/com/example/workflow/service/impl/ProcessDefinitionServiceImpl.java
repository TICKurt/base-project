package com.example.workflow.service.impl;

import com.example.workflow.model.dto.BpmnDeployDTO;
import com.example.workflow.model.dto.ProcessDefinitionDTO;
import com.example.workflow.model.query.ProcessDefinitionQuery;
import com.example.workflow.model.vo.ProcessDefinitionVO;
import com.example.workflow.service.ProcessDefinitionService;
import com.example.workflow.utils.FlowableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipInputStream;

/**
 * 流程定义服务实现类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

    private final RepositoryService repositoryService;
    private final BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deploy(String name, String category, MultipartFile file) {
        try {
            // 获取文件名和扩展名
            String fileName = file.getOriginalFilename();
            String extension = StringUtils.substringAfterLast(fileName, ".");

            // 根据文件类型部署
            Deployment deployment;
            if ("zip".equalsIgnoreCase(extension)) {
                // ZIP文件部署
                try (InputStream inputStream = file.getInputStream();
                     ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
                    deployment = repositoryService.createDeployment()
                            .name(name)
                            .category(category)
                            .addZipInputStream(zipInputStream)
                            .deploy();
                }
            } else {
                // 单个文件部署
                try (InputStream inputStream = file.getInputStream()) {
                    deployment = repositoryService.createDeployment()
                            .name(name)
                            .category(category)
                            .addInputStream(fileName, inputStream)
                            .deploy();
                }
            }

            return deployment.getId();
        } catch (IOException e) {
            log.error("部署流程定义文件失败", e);
            throw new RuntimeException("部署流程定义文件失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deployByInputStream(String name, String category, String resourceName, InputStream inputStream) {
        try {
            // 部署流程定义
            Deployment deployment = repositoryService.createDeployment()
                    .name(name)
                    .category(category)
                    .addInputStream(resourceName, inputStream)
                    .deploy();

            return deployment.getId();
        } catch (Exception e) {
            log.error("部署流程定义失败", e);
            throw new RuntimeException("部署流程定义失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDeploymentId(String deploymentId, boolean cascade) {
        try {
            // 删除流程定义
            repositoryService.deleteDeployment(deploymentId, cascade);
        } catch (Exception e) {
            log.error("删除流程定义失败", e);
            throw new RuntimeException("删除流程定义失败：" + e.getMessage());
        }
    }

    @Override
    public List<ProcessDefinitionVO> list(String category, String key, String name, String tenantId) {
        // 创建查询对象
        org.flowable.engine.repository.ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        // 设置查询条件
        if (StringUtils.isNotBlank(category)) {
            processDefinitionQuery.processDefinitionCategory(category);
        }
        if (StringUtils.isNotBlank(key)) {
            processDefinitionQuery.processDefinitionKey(key);
        }
        if (StringUtils.isNotBlank(name)) {
            processDefinitionQuery.processDefinitionName(name);
        }
        if (StringUtils.isNotBlank(tenantId)) {
            processDefinitionQuery.processDefinitionTenantId(tenantId);
        }

        // 设置排序
        processDefinitionQuery.orderByProcessDefinitionKey().asc()
                .orderByProcessDefinitionVersion().desc();

        // 查询结果
        List<ProcessDefinition> processDefinitions = processDefinitionQuery.list();

        // 转换为VO对象
        List<ProcessDefinitionVO> result = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitions) {
            result.add(FlowableUtils.toProcessDefinitionVO(processDefinition));
        }

        return result;
    }

    @Override
    public ProcessDefinitionVO getById(String processDefinitionId) {
        // 查询流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();

        // 转换为VO对象
        return processDefinition != null ? FlowableUtils.toProcessDefinitionVO(processDefinition) : null;
    }

    @Override
    public ProcessDefinitionVO getLatestByKey(String processDefinitionKey) {
        // 查询最新版本的流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();

        // 转换为VO对象
        return processDefinition != null ? FlowableUtils.toProcessDefinitionVO(processDefinition) : null;
    }

    @Override
    public String getProcessDefinitionXML(String processDefinitionId) {
        try {
            // 获取BPMN XML输入流
            InputStream inputStream = repositoryService.getProcessModel(processDefinitionId);
            if (inputStream == null) {
                return null;
            }

            // 转换为字符串
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("获取流程定义BPMN XML失败", e);
            throw new RuntimeException("获取流程定义BPMN XML失败：" + e.getMessage());
        }
    }

    @Override
    public byte[] getProcessDiagram(String processDefinitionId) {
        try {
            // 获取流程图输入流
            InputStream inputStream = repositoryService.getProcessDiagram(processDefinitionId);
            if (inputStream == null) {
                return null;
            }

            // 转换为字节数组
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            log.error("获取流程图失败", e);
            throw new RuntimeException("获取流程图失败：" + e.getMessage());
        }
    }

    @Override
    public byte[] getResource(String deploymentId, String resourceName) {
        try {
            // 获取资源输入流
            InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
            if (inputStream == null) {
                return null;
            }

            // 转换为字节数组
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            log.error("获取流程资源失败", e);
            throw new RuntimeException("获取流程资源失败：" + e.getMessage());
        }
    }

    @Override
    public boolean validateBpmnFile(MultipartFile file) {
        try {
            // 尝试部署流程定义（不保存）
            String fileName = file.getOriginalFilename();
            try (InputStream inputStream = file.getInputStream()) {
                Deployment deployment = repositoryService.createDeployment()
                        .name("Validation_" + System.currentTimeMillis())
                        .addInputStream(fileName, inputStream)
                        .tenantId("VALIDATION")
                        .deploy();
                
                // 验证是否成功部署
                List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery()
                        .deploymentId(deployment.getId())
                        .list();
                
                // 删除验证用的部署
                repositoryService.deleteDeployment(deployment.getId(), true);
                
                return !definitions.isEmpty();
            }
        } catch (Exception e) {
            log.error("验证BPMN文件失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void activate(String processDefinitionId) {
        try {
            // 激活流程定义
            repositoryService.activateProcessDefinitionById(processDefinitionId);
        } catch (Exception e) {
            log.error("激活流程定义失败", e);
            throw new RuntimeException("激活流程定义失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspend(String processDefinitionId) {
        try {
            // 挂起流程定义
            repositoryService.suspendProcessDefinitionById(processDefinitionId);
        } catch (Exception e) {
            log.error("挂起流程定义失败", e);
            throw new RuntimeException("挂起流程定义失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deployByBpmnXml(BpmnDeployDTO deployDTO) {
        try {
            // 验证参数
            if (StringUtils.isBlank(deployDTO.getName())) {
                throw new IllegalArgumentException("流程名称不能为空");
            }
            if (StringUtils.isBlank(deployDTO.getBpmnXml())) {
                throw new IllegalArgumentException("BPMN XML内容不能为空");
            }

            // 验证BPMN XML内容是否有效
            if (!validateBpmnXml(deployDTO.getBpmnXml())) {
                throw new IllegalArgumentException("无效的BPMN XML内容");
            }

            // 生成资源名称
            String resourceName = StringUtils.isNotBlank(deployDTO.getResourceName()) 
                ? deployDTO.getResourceName() 
                : deployDTO.getName().replaceAll("\\s+", "_") + ".bpmn20.xml";
            
            // 确保文件扩展名为.bpmn20.xml
            if (!resourceName.endsWith(".bpmn20.xml")) {
                resourceName = resourceName + ".bpmn20.xml";
            }

            // 转换为输入流
            InputStream inputStream = new ByteArrayInputStream(deployDTO.getBpmnXml().getBytes(StandardCharsets.UTF_8));

            // 创建部署构建器
            org.flowable.engine.repository.DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                    .name(deployDTO.getName())
                    .addInputStream(resourceName, inputStream);

            // 设置分类
            if (StringUtils.isNotBlank(deployDTO.getCategory())) {
                deploymentBuilder.category(deployDTO.getCategory());
            }

            // 设置租户ID
            if (StringUtils.isNotBlank(deployDTO.getTenantId())) {
                deploymentBuilder.tenantId(deployDTO.getTenantId());
            }

            // 部署流程
            Deployment deployment = deploymentBuilder.deploy();
            
            return deployment.getId();
        } catch (Exception e) {
            log.error("通过BPMN XML部署流程定义失败", e);
            throw new RuntimeException("通过BPMN XML部署流程定义失败：" + e.getMessage(), e);
        }
    }

    @Override
    public boolean validateBpmnXml(String bpmnXml) {
        if (StringUtils.isBlank(bpmnXml)) {
            return false;
        }

        try {
            // 生成唯一资源名称
            String resourceName = "validation_" + UUID.randomUUID().toString() + ".bpmn20.xml";
            
            // 创建输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bpmnXml.getBytes(StandardCharsets.UTF_8));
            
            // 创建临时部署
            Deployment deployment = repositoryService.createDeployment()
                    .name("Validation_" + System.currentTimeMillis())
                    .addInputStream(resourceName, inputStream)
                    .tenantId("VALIDATION")
                    .deploy();
            
            // 验证是否成功部署
            List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId())
                    .list();
            
            // 删除验证用的部署
            repositoryService.deleteDeployment(deployment.getId(), true);
            
            return !definitions.isEmpty();
        } catch (Exception e) {
            log.error("验证BPMN XML内容失败", e);
            return false;
        }
    }
} 