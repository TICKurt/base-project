package com.example.common.storage;

import com.example.common.config.FileUploadConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 文件存储工厂
 * 根据配置创建对应的文件存储实现
 * 
 * @author example
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class FileStorageFactory {

    private final FileUploadConfig fileUploadConfig;

    /**
     * 创建文件存储实例
     *
     * @return 文件存储实现
     */
    public FileStorage createFileStorage() {
        String storageType = fileUploadConfig.getStorageType();
        
        log.info("初始化文件存储，类型: {}", storageType);
        
        switch (storageType.toLowerCase()) {
            case "oss":
                if (!fileUploadConfig.getOss().isEnabled()) {
                    log.warn("OSS存储未启用，使用本地存储");
                    return new LocalFileStorage(fileUploadConfig);
                }
                throw new UnsupportedOperationException("OSS存储尚未实现");
            case "cos":
                if (!fileUploadConfig.getCos().isEnabled()) {
                    log.warn("COS存储未启用，使用本地存储");
                    return new LocalFileStorage(fileUploadConfig);
                }
                throw new UnsupportedOperationException("COS存储尚未实现");
            case "local":
            default:
                return new LocalFileStorage(fileUploadConfig);
        }
    }
} 