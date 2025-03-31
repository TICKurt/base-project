package com.example.common.storage;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件存储接口
 * 定义文件存储的通用操作
 * 
 * @author example
 */
public interface FileStorage {
    
    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @param path 存储路径（不包含文件名）
     * @return 文件访问URL
     * @throws IOException IO异常
     */
    String upload(MultipartFile file, String path) throws IOException;
    
    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param originalFilename 原始文件名
     * @param path 存储路径（不包含文件名）
     * @return 文件访问URL
     * @throws IOException IO异常
     */
    String upload(InputStream inputStream, String originalFilename, String path) throws IOException;
    
    /**
     * 上传文件内容
     *
     * @param content 文件内容（字节数组）
     * @param originalFilename 原始文件名
     * @param path 存储路径（不包含文件名）
     * @return 文件访问URL
     * @throws IOException IO异常
     */
    String upload(byte[] content, String originalFilename, String path) throws IOException;
    
    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 是否删除成功
     */
    boolean delete(String filePath);
    
    /**
     * 获取文件访问URL
     *
     * @param filePath 文件路径
     * @return 文件访问URL
     */
    String getFileUrl(String filePath);
    
    /**
     * 获取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容（输入流）
     * @throws IOException IO异常
     */
    InputStream getFileContent(String filePath) throws IOException;
    
    /**
     * 检查文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     */
    boolean exists(String filePath);
} 