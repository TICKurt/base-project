package com.example.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件工具类
 * 
 * @author example
 */
public class FileUtils {

    private static final String[] ALLOWED_IMAGE_EXTENSIONS = {"jpg", "jpeg", "png", "gif", "bmp"};
    private static final String[] ALLOWED_DOCUMENT_EXTENSIONS = {"doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "txt"};
    private static final String[] ALLOWED_ARCHIVE_EXTENSIONS = {"zip", "rar", "7z", "tar", "gz"};
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 扩展名（小写，不包含点）
     */
    public static String getFileExtension(String filename) {
        if (StringUtils.isBlank(filename)) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex < 0 || lastDotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }

    /**
     * 获取文件扩展名
     *
     * @param file 文件
     * @return 扩展名（小写，不包含点）
     */
    public static String getFileExtension(MultipartFile file) {
        if (file == null) {
            return "";
        }
        return getFileExtension(file.getOriginalFilename());
    }

    /**
     * 检查文件扩展名是否在允许的扩展名列表中
     *
     * @param filename 文件名
     * @param allowedExtensions 允许的扩展名列表
     * @return 是否允许
     */
    public static boolean isAllowedExtension(String filename, String[] allowedExtensions) {
        String extension = getFileExtension(filename);
        if (StringUtils.isBlank(extension)) {
            return false;
        }
        for (String allowedExtension : allowedExtensions) {
            if (extension.equalsIgnoreCase(allowedExtension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查文件扩展名是否在允许的扩展名列表中
     *
     * @param file 文件
     * @param allowedExtensions 允许的扩展名列表
     * @return 是否允许
     */
    public static boolean isAllowedExtension(MultipartFile file, String[] allowedExtensions) {
        if (file == null) {
            return false;
        }
        return isAllowedExtension(file.getOriginalFilename(), allowedExtensions);
    }

    /**
     * 生成文件名
     * 使用当前日期时间和UUID生成唯一文件名
     *
     * @param extension 文件扩展名（不含点）
     * @return 生成的文件名
     */
    public static String generateFilename(String extension) {
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DATE_FORMATTER);
        String time = now.format(TIME_FORMATTER);
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        
        StringBuilder sb = new StringBuilder();
        sb.append(date).append("_").append(time).append("_").append(uuid);
        
        if (StringUtils.isNotBlank(extension)) {
            sb.append(".").append(extension);
        }
        
        return sb.toString();
    }

    /**
     * 清理文件名，移除不安全字符
     *
     * @param filename 原始文件名
     * @return 安全的文件名
     */
    public static String sanitizeFilename(String filename) {
        if (StringUtils.isBlank(filename)) {
            return "unknown";
        }
        
        // 替换不安全字符
        String sanitized = filename.replaceAll("[\\\\/:*?\"<>|]", "_");
        
        // 如果文件名太长，截断它
        if (sanitized.length() > 200) {
            String extension = getFileExtension(sanitized);
            if (StringUtils.isNotBlank(extension)) {
                sanitized = sanitized.substring(0, 195) + "." + extension;
            } else {
                sanitized = sanitized.substring(0, 200);
            }
        }
        
        return sanitized;
    }

    /**
     * 获取基于日期的存储路径
     * 例如：2023/05/12
     *
     * @return 基于日期的目录路径
     */
    public static String getDateBasedPath() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        
        return String.format("%d/%02d/%02d", year, month, day);
    }

    /**
     * 检查文件是否为图片
     *
     * @param filename 文件名
     * @return 是否为图片
     */
    public static boolean isImage(String filename) {
        return isAllowedExtension(filename, ALLOWED_IMAGE_EXTENSIONS);
    }

    /**
     * 检查文件是否为文档
     *
     * @param filename 文件名
     * @return 是否为文档
     */
    public static boolean isDocument(String filename) {
        return isAllowedExtension(filename, ALLOWED_DOCUMENT_EXTENSIONS);
    }

    /**
     * 检查文件是否为压缩文件
     *
     * @param filename 文件名
     * @return 是否为压缩文件
     */
    public static boolean isArchive(String filename) {
        return isAllowedExtension(filename, ALLOWED_ARCHIVE_EXTENSIONS);
    }

    /**
     * 创建目录（如果不存在）
     *
     * @param directory 目录路径
     * @return 是否成功创建（或已存在）
     */
    public static boolean createDirectoryIfNotExists(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

    /**
     * 获取不带扩展名的文件名
     *
     * @param filename 文件名
     * @return 不带扩展名的文件名
     */
    public static String getFilenameWithoutExtension(String filename) {
        if (StringUtils.isBlank(filename)) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex < 0) {
            return filename;
        }
        return filename.substring(0, lastDotIndex);
    }
    
    /**
     * 获取文件所在的目录路径
     *
     * @param fullPath 完整路径
     * @return 目录路径
     */
    public static String getDirectoryPath(String fullPath) {
        if (StringUtils.isBlank(fullPath)) {
            return "";
        }
        Path path = Paths.get(fullPath);
        Path parent = path.getParent();
        return parent != null ? parent.toString() : "";
    }
} 