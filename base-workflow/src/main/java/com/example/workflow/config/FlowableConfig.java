package com.example.workflow.config;

import org.flowable.engine.ProcessEngine;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flowable配置类
 *
 * @author Author
 * @version 1.0
 */
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    // 定义备选字体，确保至少有一个可用
    private static final String[] FONT_NAMES = {"宋体", "SimSun", "Microsoft YaHei", "Arial", "Helvetica", "sans-serif"};

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        // 选择一个可用的字体
        String fontName = selectAvailableFont();
        
        // 设置流程图字体
        engineConfiguration.setActivityFontName(fontName);
        engineConfiguration.setLabelFontName(fontName);
        engineConfiguration.setAnnotationFontName(fontName);

        // 设置历史级别为full，保存所有历史数据
        engineConfiguration.setHistory("full");

        // 设置异步执行器
        engineConfiguration.setAsyncExecutorActivate(true);

        // 开启任务关系计数
        engineConfiguration.setEnableTaskRelationshipCounts(true);

        // 设置邮件服务器配置
        engineConfiguration.setMailServerHost("smtp.example.com");
        engineConfiguration.setMailServerPort(25);
        engineConfiguration.setMailServerDefaultFrom("workflow@example.com");
        engineConfiguration.setMailServerUsername("username");
        engineConfiguration.setMailServerPassword("password");
        engineConfiguration.setMailServerUseSSL(true);

        // 增强流程图生成配置
        engineConfiguration.setCreateDiagramOnDeploy(true); // 部署时创建流程图
        engineConfiguration.setDrawSequenceFlowNameWithNoLabelDI(true); // 即使没有标签也显示顺序流名称
        engineConfiguration.setEnableSafeBpmnXml(true); // 启用安全的BPMN XML处理
        engineConfiguration.setEnableProcessDefinitionInfoCache(true); // 启用流程定义信息缓存
        engineConfiguration.setAlwaysLookupLatestDefinitionVersion(false); // 使用精确版本而不是最新版本
        engineConfiguration.setEnableEventDispatcher(true); // 启用事件分发器
        
        // 设置自定义的流程图生成器
        engineConfiguration.setProcessDiagramGenerator(processDiagramGenerator());
    }
    
    /**
     * 提供自定义的流程图生成器
     */
    @Bean
    public ProcessDiagramGenerator processDiagramGenerator() {
        return new DefaultProcessDiagramGenerator();
    }
    
    /**
     * 选择一个可用的字体
     */
    private String selectAvailableFont() {
        // 默认使用第一个字体
        return FONT_NAMES[0];
    }

    @Bean
    public ProcessEngine processEngine(SpringProcessEngineConfiguration configuration) {
        return configuration.buildProcessEngine();
    }

    @Bean
    public org.flowable.engine.RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public org.flowable.engine.TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public org.flowable.engine.RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public org.flowable.engine.HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public org.flowable.engine.ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    public org.flowable.engine.FormService flowableFormService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }
} 