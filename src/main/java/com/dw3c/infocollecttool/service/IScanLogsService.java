package com.dw3c.infocollecttool.service;

import com.dw3c.infocollecttool.entity.InfoCollection;
import com.dw3c.infocollecttool.entity.ScanLogs;

import java.util.List;

public interface IScanLogsService {
    Integer insert(ScanLogs logs);
    ScanLogs getById(Integer id);
    List<ScanLogs> getAll();
    void update(ScanLogs logs);
    void delete(Integer id);
}
