package com.dw3c.infocollecttool.entity;

import lombok.Data;

@Data
public class ScanLogs {
    private Integer id;
    private String fileId;
    private String status;
    private String errorMsg;
    private String updatedAt;
    private String createAt;
}
