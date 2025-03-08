package com.dw3c.infocollecttool.entity;

import lombok.Data;

@Data
public class ScanLogs {
    private Integer id;
    private Integer fileId;
    private String logMsg;
    private String updatedAt;
    private String createAt;
}
