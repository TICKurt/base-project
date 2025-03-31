package com.example.common.storage;

import com.example.common.config.FileUploadConfig;
import com.example.common.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 本地文件存储实现
 * 
 * @author example
 */
@Slf4j
@RequiredArgsConstructor
public class LocalFileStorage implements FileStorage {

    private final FileUploadConfig fileUploadConfig;

    @Override
    public String upload(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            return upload(inputStream, originalFilename, path);
        }
    }

    @Override
    public String upload(InputStream inputStream, String originalFilename, String path) throws IOException {
        // 创建存储目录
        String storePath = fileUploadConfig.getBasePath() + File.separator + path;
        Path targetDir = Paths.get(storePath);
        Files.createDirectories(targetDir);
        
        // 生成文件名
        String filename;
        if (fileUploadConfig.isUseOriginalFilename()) {
            filename = FileUtils.sanitizeFilename(originalFilename);
        } else {
            String fileExtension = FileUtils.getFileExtension(originalFilename);
            filename = FileUtils.generateFilename(fileExtension);
        }
        
        // 保存文件 - 使用字节数组以确保完全读取
        Path targetPath = targetDir.resolve(filename);
        
        // 读取输入流内容到字节数组
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        
        // 写入文件
        Files.write(targetPath, buffer.toByteArray());
        
        return path + "/" + filename;
    }

    @Override
    public String upload(byte[] content, String originalFilename, String path) throws IOException {
        return upload(new ByteArrayInputStream(content), originalFilename, path);
    }

    @Override
    public boolean delete(String filePath) {
        try {
            Path path = Paths.get(fileUploadConfig.getBasePath(), filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("删除文件失败: {}", filePath, e);
            return false;
        }
    }

    @Override
    public String getFileUrl(String filePath) {
        return fileUploadConfig.getAccessPrefix() + "/" + filePath;
    }

    @Override
    public InputStream getFileContent(String filePath) throws IOException {
        Path path = Paths.get(fileUploadConfig.getBasePath(), filePath);
        if (!Files.exists(path)) {
            throw new IOException("文件不存在: " + filePath);
        }
        return new FileInputStream(path.toFile());
    }

    @Override
    public boolean exists(String filePath) {
        Path path = Paths.get(fileUploadConfig.getBasePath(), filePath);
        return Files.exists(path);
    }
} 