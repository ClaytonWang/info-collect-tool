package com.dw3c.infocollecttool.service.impl;

import com.dw3c.infocollecttool.entity.UploadFile;
import com.dw3c.infocollecttool.mapper.IFileUploadMapper;
import com.dw3c.infocollecttool.service.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

    // 定义文件存储路径
    @Value("${app.upload.dir:uploads/}")
    private String UPLOAD_DIR;
    // 允许上传的文件类型
    @Value("${app.upload.allowed-extensions:xlsx,xls}")
    private  List<String> ALLOWED_EXTENSIONS;

    @Autowired
    private IFileUploadMapper uploadFileMapper;

    @Override
    public String uploadFile(MultipartFile file) {
        // 获取文件后缀名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : "";
        String newFileName = originalFilename != null ? originalFilename.substring(0, originalFilename.lastIndexOf(".")) +System.currentTimeMillis()+ "." + fileExtension : "";
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

            Path filePath = uploadPath.resolve(newFileName);

            uploadFileMapper.insert(new UploadFile(null,newFileName,originalFilename,"UPLOADED",null,null));
            // 保存文件
            Files.copy(file.getInputStream(), filePath);

            return "文件上传成功，重命名为：" + newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败：" + e.getMessage();
        }
    }

    @Override
    public Integer insert(UploadFile file) {
        return uploadFileMapper.insert(file);
    }

    @Override
    public UploadFile getUploadFileById(Integer id) {
        return uploadFileMapper.getUploadFileById(id);
    }

    @Override
    public List<UploadFile> getAllUploadFiles() {
        return uploadFileMapper.getAllUploadFiles();
    }

    @Override
    public List<UploadFile> getRawFiles() {
        return uploadFileMapper.getRawFiles();
    }

    @Override
    public void updateUploadFile(UploadFile file) {
        uploadFileMapper.updateUploadFile(file);
    }

    @Override
    public void deleteUploadFile(Integer id) {
        uploadFileMapper.deleteUploadFile(id);
    }

    private boolean isAllowedFileType(String fileExtension) {
        return ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase());
    }
}
