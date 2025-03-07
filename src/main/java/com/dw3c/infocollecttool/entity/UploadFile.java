package com.dw3c.infocollecttool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UploadFile {
    Integer id;
    String fileName;
    String email;
    String updatedAt;
    String createAt;
}
