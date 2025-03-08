package com.dw3c.infocollecttool.mapper;

import com.dw3c.infocollecttool.entity.ScanLogs;
import com.dw3c.infocollecttool.entity.UploadFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IScanLogs {
    void insert(@Param("log") ScanLogs log);
    ScanLogs getById(@Param("id") String id);
    List<ScanLogs> getAll();
    void update(@Param("log") ScanLogs log);
    void delete(@Param("id") Long id);
}
