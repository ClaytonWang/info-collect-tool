package com.dw3c.infocollecttool.service;

import com.dw3c.infocollecttool.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface  IFileUploadService {
    String uploadFile(MultipartFile file);
    Integer insert(UploadFile file);
    UploadFile getUploadFileById(Integer id);
    List<UploadFile> getAllUploadFiles();
    void updateUploadFile(UploadFile file);
    void deleteUploadFile(Integer id);
}
