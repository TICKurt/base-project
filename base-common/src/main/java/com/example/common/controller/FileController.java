package com.example.common.controller;

import com.example.auth.annotation.RequiresPermission;
import com.example.common.model.FileInfo;
import com.example.common.service.FileService;
import com.example.common.util.FileUtils;
import com.example.auth.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
     * @param file      文件
     * @param directory 目录（可选）
     * @return 文件信息
     */
    @RequiresPermission("common:file:upload")
    @PostMapping("/upload")
    public Result<FileInfo> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "directory", required = false) String directory) {
        try {
            FileInfo fileInfo;
            if (StringUtils.isBlank(directory)) {
                fileInfo = fileService.uploadFile(file);
            } else {
                fileInfo = fileService.uploadFile(file, directory);
            }
            return Result.ok(fileInfo);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            return Result.error("上传文件失败" + e.getMessage());
        }
    }

    /**
     * 批量上传文件
     *
     * @param files     文件列表
     * @param directory 目录（可选）
     * @return 文件信息列表
     */
    @RequiresPermission("common:file:upload")
    @PostMapping("/batch-upload")
    public Result<List<FileInfo>> batchUpload(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(value = "directory", required = false) String directory) {
        try {
            List<FileInfo> fileInfos;
            if (StringUtils.isBlank(directory)) {
                fileInfos = fileService.uploadFiles(files);
            } else {
                fileInfos = fileService.uploadFiles(files, directory);
            }
            return Result.ok(fileInfos);
        } catch (IOException e) {
            log.error("批量上传文件失败", e);
            return Result.error("批量上传文件失败" + e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return 文件资源
     */
    @RequiresPermission("common:file:download")
    @GetMapping("/download/{*filePath}")
    public ResponseEntity<Resource> download(@PathVariable("filePath") String filePath) {
        log.info("开始下载文件: {}", filePath);
        
        // 检查文件是否存在
        if (!fileService.exists(filePath)) {
            log.warn("文件不存在: {}", filePath);
            return ResponseEntity.notFound().build();
        }

        try {
            // 获取文件名
            String filename = filePath.substring(filePath.lastIndexOf('/') + 1);
            String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString())
                    .replaceAll("\\+", "%20");
            
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename);
            
            // 添加缓存控制
            headers.setCacheControl("private, max-age=3600");
            headers.setPragma("no-cache");
            
            // 设置内容类型
            MediaType mediaType = getMediaType(filename);
            
            // 获取文件输入流
            InputStream inputStream = fileService.getFile(filePath);
            
            // 创建资源
            InputStreamResource resource = new InputStreamResource(inputStream);
            
            // 返回文件
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            log.error("下载文件失败: {}", filePath, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
    /**
     * 获取文件的MediaType
     *
     * @param filename 文件名
     * @return MediaType
     */
    private MediaType getMediaType(String filename) {
        String extension = FileUtils.getFileExtension(filename);
        
        // 设置常见的内容类型
        if (FileUtils.isImage(filename)) {
            if ("png".equalsIgnoreCase(extension)) {
                return MediaType.IMAGE_PNG;
            } else if ("gif".equalsIgnoreCase(extension)) {
                return MediaType.IMAGE_GIF;
            } else if ("jpg".equalsIgnoreCase(extension) || "jpeg".equalsIgnoreCase(extension)) {
                return MediaType.IMAGE_JPEG;
            }
        } else if ("pdf".equalsIgnoreCase(extension)) {
            return MediaType.APPLICATION_PDF;
        } else if ("txt".equalsIgnoreCase(extension)) {
            return MediaType.TEXT_PLAIN;
        } else if ("html".equalsIgnoreCase(extension) || "htm".equalsIgnoreCase(extension)) {
            return MediaType.TEXT_HTML;
        } else if ("xml".equalsIgnoreCase(extension)) {
            return MediaType.APPLICATION_XML;
        } else if ("json".equalsIgnoreCase(extension)) {
            return MediaType.APPLICATION_JSON;
        } else if ("js".equalsIgnoreCase(extension)) {
            return new MediaType("application", "javascript");
        } else if ("css".equalsIgnoreCase(extension)) {
            return new MediaType("text", "css");
        } else if ("csv".equalsIgnoreCase(extension)) {
            return new MediaType("text", "csv");
        } else if ("bpmn".equalsIgnoreCase(extension) || "bpmn20.xml".equalsIgnoreCase(extension)) {
            return MediaType.APPLICATION_XML;
        }
        
        // 默认使用二进制流
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 删除结果
     */
    @RequiresPermission("common:file:delete")
    @DeleteMapping
    public Result<Map<String, Object>> delete(@RequestParam("path") String filePath) {
        Map<String, Object> result = new HashMap<>();
        boolean deleted = fileService.deleteFile(filePath);
        result.put("success", deleted);
        result.put("path", filePath);

        return Result.ok(result);
    }

    /**
     * 获取文件信息
     *
     * @param filePath 文件路径
     * @return 文件信息
     */
    @RequiresPermission("common:file:info")
    @GetMapping("/info")
    public Result<Map<String, Object>> getInfo(@RequestParam("path") String filePath) {
        Map<String, Object> result = new HashMap<>();

        boolean exists = fileService.exists(filePath);
        if (!exists) {
            result.put("exists", false);
            return Result.ok(result);
        }

        result.put("exists", true);
        result.put("path", filePath);
        result.put("url", fileService.getFileUrl(filePath));
        result.put("filename", filePath.substring(filePath.lastIndexOf('/') + 1));
        result.put("extension", FileUtils.getFileExtension(filePath));

        return Result.ok(result);
    }
} 