package com.dw3c.infocollecttool.service;

import org.springframework.web.multipart.MultipartFile;

public interface  IFileUploadService {
    String uploadFile(MultipartFile file);
}
