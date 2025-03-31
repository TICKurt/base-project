package com.example.common.service;

import com.example.common.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 文件服务接口
 * 
 * @author example
 */
public interface FileService {

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return 文件信息
     * @throws IOException IO异常
     */
    FileInfo uploadFile(MultipartFile file) throws IOException;

    /**
     * 上传文件到指定目录
     *
     * @param file 上传的文件
     * @param directory 目录
     * @return 文件信息
     * @throws IOException IO异常
     */
    FileInfo uploadFile(MultipartFile file, String directory) throws IOException;

    /**
     * 批量上传文件
     *
     * @param files 上传的文件列表
     * @return 文件信息列表
     * @throws IOException IO异常
     */
    List<FileInfo> uploadFiles(List<MultipartFile> files) throws IOException;

    /**
     * 批量上传文件到指定目录
     *
     * @param files 上传的文件列表
     * @param directory 目录
     * @return 文件信息列表
     * @throws IOException IO异常
     */
    List<FileInfo> uploadFiles(List<MultipartFile> files, String directory) throws IOException;

    /**
     * 获取文件
     *
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException IO异常
     */
    InputStream getFile(String filePath) throws IOException;

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 是否删除成功
     */
    boolean deleteFile(String filePath);

    /**
     * 检查文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     */
    boolean exists(String filePath);

    /**
     * 获取文件访问URL
     *
     * @param filePath 文件路径
     * @return 访问URL
     */
    String getFileUrl(String filePath);
} 