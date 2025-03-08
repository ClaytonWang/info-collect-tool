package com.dw3c.infocollecttool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UploadFile {
    private Integer id;
    private String fileName;
    private String originalFileName;
    private String status;
    private String updatedAt;
    private String createAt;
}
