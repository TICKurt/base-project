package com.example.workflow.config;

import org.flowable.engine.ProcessEngine;
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

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        // 设置流程图字体
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");

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

        // 设置流程图生成配置
        engineConfiguration.setCreateDiagramOnDeploy(true);
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
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