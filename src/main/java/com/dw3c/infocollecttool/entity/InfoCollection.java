package com.dw3c.infocollecttool.entity;

import lombok.Data;

@Data
public class InfoCollection {
    Integer id;
    String centralName;
    String centralTeam;
    String approveEmail;
    String approvingDate;
    String approvingConclusion;
    String comments;
    String updatedAt;
    String createAt;
}
