package com.dw3c.infocollecttool.mapper;

import com.dw3c.infocollecttool.entity.UploadFile;

import java.util.List;

public interface IFileUploadMapper {
    Integer insert(UploadFile file);
    UploadFile getUploadFileById(Integer id);
    List<UploadFile> getAllUploadFiles();
    List<UploadFile> getRawFiles();
    void updateUploadFile(UploadFile file);
    void deleteUploadFile(Integer id);
}
