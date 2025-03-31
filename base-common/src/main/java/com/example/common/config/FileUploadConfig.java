package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * 文件上传配置类
 * 
 * @author example
 */
@Configuration
@ConfigurationProperties(prefix = "file.upload")
@EnableConfigurationProperties
@Data
public class FileUploadConfig {
    /**
     * 上传文件保存路径
     */
    private String basePath = "uploads";
    
    /**
     * 允许上传的文件类型
     */
    private String allowedExtensions = "jpg,jpeg,png,gif,bmp,doc,docx,xls,xlsx,ppt,pptx,pdf,zip,rar,txt";
    
    /**
     * 文件大小限制（单位：MB）
     */
    private long maxSize = 10;
    
    /**
     * 是否使用原始文件名
     */
    private boolean useOriginalFilename = false;
    
    /**
     * 资源访问前缀
     */
    private String accessPrefix = "/files";
    
    /**
     * 临时文件目录
     */
    private String tempDir = "temp";
    
    /**
     * 文件存储的方式：local-本地存储, oss-对象存储, cos-腾讯云存储
     */
    private String storageType = "local";
    
    /**
     * OSS配置
     */
    private OssConfig oss = new OssConfig();
    
    /**
     * COS配置
     */
    private CosConfig cos = new CosConfig();
    
    /**
     * 获取允许的文件扩展名列表
     */
    public List<String> getAllowedExtensionList() {
        return Arrays.asList(allowedExtensions.split(","));
    }
    
    /**
     * OSS配置类
     */
    @Data
    public static class OssConfig {
        /**
         * 是否启用
         */
        private boolean enabled = false;
        
        /**
         * 访问密钥ID
         */
        private String accessKeyId;
        
        /**
         * 访问密钥密码
         */
        private String accessKeySecret;
        
        /**
         * 端点
         */
        private String endpoint;
        
        /**
         * 存储桶名称
         */
        private String bucketName;
        
        /**
         * 访问域名
         */
        private String domain;
        
        /**
         * 基础路径
         */
        private String basePath = "";
    }
    
    /**
     * COS配置类
     */
    @Data
    public static class CosConfig {
        /**
         * 是否启用
         */
        private boolean enabled = false;
        
        /**
         * 访问密钥ID
         */
        private String secretId;
        
        /**
         * 访问密钥密码
         */
        private String secretKey;
        
        /**
         * 区域
         */
        private String region;
        
        /**
         * 存储桶名称
         */
        private String bucketName;
        
        /**
         * 访问域名
         */
        private String domain;
        
        /**
         * 基础路径
         */
        private String basePath = "";
    }
} 