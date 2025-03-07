package com.dw3c.infocollecttool.service.impl;

import com.dw3c.infocollecttool.service.IFileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

    // 定义文件存储路径
    private static final String UPLOAD_DIR = "uploads/";
    // 允许上传的文件类型
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("xlsx", "xls");

    @Override
    public String uploadFile(MultipartFile file) {
        // 获取文件后缀名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : "";

        // 检查文件类型
        if (!isAllowedFileType(fileExtension)) {
            return "文件类型不支持，仅允许上传 .xlsx 格式的Excel文件。";
        }

        try {
            // 创建上传目录（如果不存在）
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 按时间戳重命名文件
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String newFileName = timestamp + "." + fileExtension;
            Path filePath = uploadPath.resolve(newFileName);

            // 保存文件
            Files.copy(file.getInputStream(), filePath);

            return "文件上传成功，重命名为：" + newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败：" + e.getMessage();
        }
    }

    private boolean isAllowedFileType(String fileExtension) {
        return ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase());
    }
}
