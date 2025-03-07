package com.dw3c.infocollecttool.controller;

import com.dw3c.infocollecttool.service.IFileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "文件上传", description = "文件上传相关接口")
public class FileUploadController {

    @Autowired
    private IFileUploadService fileUploadService;

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "上传Excel文件", description = "上传一个文件，并按时间戳重命名")
    public String handleFileUpload(@Parameter(
            description = "要上传的文件",
            required = true,
            content = @Content(
                    mediaType = "multipart/form-data",
                    schema = @Schema(type = "string", format = "binary")
            ))
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return "文件为空，请选择文件上传。";
        }

        return fileUploadService.uploadFile(file);
    }

}