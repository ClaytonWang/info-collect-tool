package com.dw3c.infocollecttool.mapper;

import com.dw3c.infocollecttool.entity.UploadFile;

import java.util.List;

public interface IFileUploadMapper {
    void insert(UploadFile file);
    UploadFile getUploadFileById(String id);
    List<UploadFile> getAllUploadFiles();
    void updateUploadFile(UploadFile file);
    void deleteUploadFile(Long id);
}
