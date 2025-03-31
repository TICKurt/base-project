package com.example.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * 资源配置
 * 配置静态资源访问
 * 
 * @author example
 */
@Configuration
@RequiredArgsConstructor
public class ResourceConfig implements WebMvcConfigurer {

    private final FileUploadConfig fileUploadConfig;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置上传文件访问路径
        String accessPrefix = fileUploadConfig.getAccessPrefix();
        if (!accessPrefix.startsWith("/")) {
            accessPrefix = "/" + accessPrefix;
        }
        if (!accessPrefix.endsWith("/")) {
            accessPrefix = accessPrefix + "/";
        }
        
        // 资源映射
        String uploadPath = fileUploadConfig.getBasePath();
        if (!uploadPath.endsWith(File.separator)) {
            uploadPath = uploadPath + File.separator;
        }
        
        registry.addResourceHandler(accessPrefix + "**")
                .addResourceLocations("file:" + uploadPath);
    }
} 