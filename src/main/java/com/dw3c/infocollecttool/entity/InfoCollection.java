package com.dw3c.infocollecttool.entity;

import lombok.Data;

@Data
public class InfoCollection {
    private Integer id;
    private Integer fileId;
    private String approverName;
    private String team;
    private String email;
    private String approvingDate;
    private String approvingConclusion;
    private String additionalComments;
    private String comments;
    private String updatedAt;
    private String createAt;
}
