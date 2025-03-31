package com.example.common.service.impl;

import com.example.common.config.FileUploadConfig;
import com.example.common.model.FileInfo;
import com.example.common.service.FileService;
import com.example.common.storage.FileStorage;
import com.example.common.storage.FileStorageFactory;
import com.example.common.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件服务实现
 * 
 * @author example
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileUploadConfig fileUploadConfig;
    private final FileStorageFactory fileStorageFactory;
    
    private FileStorage fileStorage;
    
    @PostConstruct
    public void init() {
        this.fileStorage = fileStorageFactory.createFileStorage();
        log.info("文件服务初始化完成，存储类型: {}", fileUploadConfig.getStorageType());
    }

    @Override
    public FileInfo uploadFile(MultipartFile file) throws IOException {
        return uploadFile(file, FileUtils.getDateBasedPath());
    }

    @Override
    public FileInfo uploadFile(MultipartFile file, String directory) throws IOException {
        // 校验文件
        validateFile(file);
        
        // 处理目录，确保不以/开头或结尾
        String dir = formatDirectory(directory);
        
        // 上传文件
        String filePath = fileStorage.upload(file, dir);
        
        // 构建文件信息
        return buildFileInfo(file, filePath);
    }

    @Override
    public List<FileInfo> uploadFiles(List<MultipartFile> files) throws IOException {
        return uploadFiles(files, FileUtils.getDateBasedPath());
    }

    @Override
    public List<FileInfo> uploadFiles(List<MultipartFile> files, String directory) throws IOException {
        List<FileInfo> fileInfos = new ArrayList<>(files.size());
        
        for (MultipartFile file : files) {
            fileInfos.add(uploadFile(file, directory));
        }
        
        return fileInfos;
    }

    @Override
    public InputStream getFile(String filePath) throws IOException {
        return fileStorage.getFileContent(filePath);
    }

    @Override
    public boolean deleteFile(String filePath) {
        return fileStorage.delete(filePath);
    }

    @Override
    public boolean exists(String filePath) {
        return fileStorage.exists(filePath);
    }

    @Override
    public String getFileUrl(String filePath) {
        return fileStorage.getFileUrl(filePath);
    }
    
    /**
     * 校验文件
     *
     * @param file 文件
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }
        
        // 校验文件大小
        long maxSizeBytes = fileUploadConfig.getMaxSize() * 1024 * 1024;
        if (file.getSize() > maxSizeBytes) {
            throw new IllegalArgumentException(
                    String.format("文件大小超过限制，最大允许 %d MB", fileUploadConfig.getMaxSize()));
        }
        
        // 校验文件类型
        String extension = FileUtils.getFileExtension(file.getOriginalFilename());
        if (StringUtils.isBlank(extension)) {
            throw new IllegalArgumentException("无法识别的文件类型");
        }
        
        List<String> allowedExtensions = fileUploadConfig.getAllowedExtensionList();
        boolean isAllowed = allowedExtensions.stream()
                .anyMatch(ext -> ext.equalsIgnoreCase(extension));
        
        if (!isAllowed) {
            throw new IllegalArgumentException(
                    String.format("不支持的文件类型，允许的类型: %s", fileUploadConfig.getAllowedExtensions()));
        }
    }
    
    /**
     * 格式化目录路径
     *
     * @param directory 目录路径
     * @return 格式化后的路径
     */
    private String formatDirectory(String directory) {
        if (StringUtils.isBlank(directory)) {
            return "";
        }
        
        String dir = directory;
        if (dir.startsWith("/")) {
            dir = dir.substring(1);
        }
        if (dir.endsWith("/")) {
            dir = dir.substring(0, dir.length() - 1);
        }
        
        return dir;
    }
    
    /**
     * 构建文件信息
     *
     * @param file 上传的文件
     * @param filePath 文件存储路径
     * @return 文件信息
     */
    private FileInfo buildFileInfo(MultipartFile file, String filePath) {
        String extension = FileUtils.getFileExtension(file.getOriginalFilename());
        
        return FileInfo.builder()
                .originalFilename(file.getOriginalFilename())
                .filename(filePath.substring(filePath.lastIndexOf('/') + 1))
                .path(filePath)
                .url(fileStorage.getFileUrl(filePath))
                .size(file.getSize())
                .contentType(file.getContentType())
                .extension(extension)
                .uploadTime(LocalDateTime.now())
                .storageType(fileUploadConfig.getStorageType())
                .build();
    }
} 