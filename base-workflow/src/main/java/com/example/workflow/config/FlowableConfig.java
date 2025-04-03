package com.example.workflow.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {
    
    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
        
        // 设置自动部署验证
        engineConfiguration.setValidateFlowable5EntitiesEnabled(false);
//        engineConfiguration.setValidateExpressions(false);
        
        // 设置异步执行器
        engineConfiguration.setAsyncExecutorActivate(false);
        
        // 启用数据库历史记录
        engineConfiguration.setDatabaseSchemaUpdate("true");
    }
} 