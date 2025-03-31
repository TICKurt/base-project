package com.example.common.controller;

import com.example.common.model.FileInfo;
import com.example.common.service.FileService;
import com.example.common.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传下载控制器
 * 
 * @author example
 */
@RestController
@RequestMapping("/files")
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 上传单个文件
     *
     * @param file 文件
     * @param directory 目录（可选）
     * @return 文件信息
     */
    @PostMapping("/upload")
    public ResponseEntity<FileInfo> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "directory", required = false) String directory) {
        try {
            FileInfo fileInfo;
            if (StringUtils.isBlank(directory)) {
                fileInfo = fileService.uploadFile(file);
            } else {
                fileInfo = fileService.uploadFile(file, directory);
            }
            return ResponseEntity.ok(fileInfo);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 批量上传文件
     *
     * @param files 文件列表
     * @param directory 目录（可选）
     * @return 文件信息列表
     */
    @PostMapping("/batch-upload")
    public ResponseEntity<List<FileInfo>> batchUpload(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(value = "directory", required = false) String directory) {
        try {
            List<FileInfo> fileInfos;
            if (StringUtils.isBlank(directory)) {
                fileInfos = fileService.uploadFiles(files);
            } else {
                fileInfos = fileService.uploadFiles(files, directory);
            }
            return ResponseEntity.ok(fileInfos);
        } catch (IOException e) {
            log.error("批量上传文件失败", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    @GetMapping("/download/**")
    public ResponseEntity<Resource> download(@RequestParam("path") String filePath) {
        // 检查文件是否存在
        if (!fileService.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }
        
        try (InputStream inputStream = fileService.getFile(filePath)) {
            // 获取文件内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] fileContent = baos.toByteArray();
            
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileContent));
            
            // 获取文件名
            String filename = filePath.substring(filePath.lastIndexOf('/') + 1);
            String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString())
                    .replaceAll("\\+", "%20");
            
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename);
            
            // 设置内容类型
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            String extension = FileUtils.getFileExtension(filename);
            if (FileUtils.isImage(filename)) {
                mediaType = MediaType.IMAGE_JPEG;
                if ("png".equalsIgnoreCase(extension)) {
                    mediaType = MediaType.IMAGE_PNG;
                } else if ("gif".equalsIgnoreCase(extension)) {
                    mediaType = MediaType.IMAGE_GIF;
                }
            } else if ("pdf".equalsIgnoreCase(extension)) {
                mediaType = MediaType.APPLICATION_PDF;
            }
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            log.error("下载文件失败: {}", filePath, e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> delete(@RequestParam("path") String filePath) {
        Map<String, Object> result = new HashMap<>();
        boolean deleted = fileService.deleteFile(filePath);
        result.put("success", deleted);
        result.put("path", filePath);
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 获取文件信息
     *
     * @param filePath 文件路径
     * @return 文件信息
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo(@RequestParam("path") String filePath) {
        Map<String, Object> result = new HashMap<>();
        
        boolean exists = fileService.exists(filePath);
        if (!exists) {
            result.put("exists", false);
            return ResponseEntity.ok(result);
        }
        
        result.put("exists", true);
        result.put("path", filePath);
        result.put("url", fileService.getFileUrl(filePath));
        result.put("filename", filePath.substring(filePath.lastIndexOf('/') + 1));
        result.put("extension", FileUtils.getFileExtension(filePath));
        
        return ResponseEntity.ok(result);
    }
} 