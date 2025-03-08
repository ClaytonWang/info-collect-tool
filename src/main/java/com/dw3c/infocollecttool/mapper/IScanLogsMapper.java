package com.dw3c.infocollecttool.mapper;

import com.dw3c.infocollecttool.entity.ScanLogs;
import com.dw3c.infocollecttool.entity.UploadFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IScanLogsMapper {
    Integer insert(ScanLogs log);
    ScanLogs getById(Integer id);
    List<ScanLogs> getAll();
    void update(ScanLogs log);
    void delete(Integer id);
}
