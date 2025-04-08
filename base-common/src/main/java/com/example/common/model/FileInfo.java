package com.example.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 文件信息
 * 
 * @author example
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

    /**
     * 原始文件名
     */
    private String originalFilename;
    
    /**
     * 存储文件名
     */
    private String filename;
    
    /**
     * 文件路径（相对路径）
     */
    private String path;
    
    /**
     * 文件访问URL
     */
    private String url;
    
    /**
     * 文件大小（字节）
     */
    private Long size;
    
    /**
     * 文件类型（MIME类型）
     */
    private String contentType;
    
    /**
     * 文件扩展名
     */
    private String extension;
    
    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;
    
    /**
     * 存储类型（local, oss, cos等）
     */
    private String storageType;

    public FileInfo(String originalFilename) {
        this.originalFilename = originalFilename;
    }
} 