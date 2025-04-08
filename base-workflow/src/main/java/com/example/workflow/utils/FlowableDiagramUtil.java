package com.example.workflow.utils;

import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Flowable流程图工具类
 *
 * @author Author
 * @version 1.0
 */
@Slf4j
@Component
public class FlowableDiagramUtil {

    private final RepositoryService repositoryService;
    private final SpringProcessEngineConfiguration processEngineConfiguration;
    
    // 定义备选字体，确保至少有一个可用
    private static final String[] FONT_NAMES = {"宋体", "SimSun", "Microsoft YaHei", "Arial", "Helvetica", "sans-serif"};

    public FlowableDiagramUtil(RepositoryService repositoryService, 
                               SpringProcessEngineConfiguration processEngineConfiguration) {
        this.repositoryService = repositoryService;
        this.processEngineConfiguration = processEngineConfiguration;
    }

    /**
     * 获取流程图输入流
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程图输入流
     */
    public InputStream getProcessDiagramInputStream(String processDefinitionId) {
        try {
            // 先尝试获取已存在的流程图
            try {
                InputStream inputStream = repositoryService.getProcessDiagram(processDefinitionId);
                if (inputStream != null) {
                    log.info("成功从存储库获取流程图: {}", processDefinitionId);
                    return inputStream;
                }
            } catch (Exception e) {
                log.warn("从存储库获取流程图失败: {}", e.getMessage());
            }

            // 如果不存在则动态生成
            log.info("尝试动态生成流程图: {}", processDefinitionId);
            return generateDiagram(processDefinitionId);
        } catch (Exception e) {
            log.error("获取流程图失败: {}", processDefinitionId, e);
            return null;
        }
    }

    /**
     * 动态生成流程图
     *
     * @param processDefinitionId 流程定义ID
     * @return 流程图输入流
     */
    public InputStream generateDiagram(String processDefinitionId) throws IOException {
        // 获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        
        if (processDefinition == null) {
            log.warn("流程定义不存在: {}", processDefinitionId);
            return null;
        }
        
        // 获取BPMN模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        if (bpmnModel == null || bpmnModel.getProcesses().isEmpty()) {
            log.warn("无法获取BPMN模型或模型中没有流程: {}", processDefinitionId);
            return null;
        }
        
        // 尝试多种方法生成流程图
        InputStream inputStream = null;
        
        // 方法1：使用配置中的流程图生成器
        try {
            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
            if (diagramGenerator == null) {
                log.warn("配置中的流程图生成器为空，使用默认生成器");
                diagramGenerator = new DefaultProcessDiagramGenerator();
            }
            
            // 尝试使用不同的字体
            for (String fontName : FONT_NAMES) {
                try {
                    log.info("尝试使用字体 '{}' 生成流程图", fontName);
                    inputStream = diagramGenerator.generateDiagram(
                            bpmnModel, 
                            "png",
                            fontName,
                            fontName,
                            fontName,
                            null,  // 使用null替代ClassLoader，避免可能的问题
                            1.0,
                            true);
                    
                    if (inputStream != null) {
                        log.info("使用字体 '{}' 成功生成流程图", fontName);
                        return inputStream;
                    }
                } catch (Exception e) {
                    log.warn("使用字体 '{}' 生成流程图失败: {}", fontName, e.getMessage());
                    // 继续尝试下一个字体
                }
            }
        } catch (Exception e) {
            log.warn("使用配置的流程图生成器失败: {}", e.getMessage());
        }
        
        // 方法2：使用默认流程图生成器的简化方法
        if (inputStream == null) {
            try {
                log.info("尝试使用默认流程图生成器的简化方法");
                DefaultProcessDiagramGenerator defaultGenerator = new DefaultProcessDiagramGenerator();
                inputStream = defaultGenerator.generatePngDiagram(bpmnModel, true);
                
                if (inputStream != null) {
                    log.info("使用简化方法成功生成流程图");
                    return inputStream;
                }
            } catch (Exception e) {
                log.warn("使用简化方法生成流程图失败: {}", e.getMessage());
            }
        }
        
        // 方法3：最小配置尝试
        if (inputStream == null) {
            try {
                log.info("尝试使用最小配置生成流程图");
                DefaultProcessDiagramGenerator minimalGenerator = new DefaultProcessDiagramGenerator();
                inputStream = minimalGenerator.generateDiagram(
                        bpmnModel,
                        "png",
                        "Arial",
                        "Arial",
                        "Arial",
                        null,
                        1.0,
                        false);  // 设置为false，简化生成过程
                
                if (inputStream != null) {
                    log.info("使用最小配置成功生成流程图");
                    return inputStream;
                }
            } catch (Exception e) {
                log.error("所有流程图生成方法均失败: {}", e.getMessage(), e);
            }
        }
        
        log.error("无法生成流程图: {}", processDefinitionId);
        return null;
    }
} 