package com.dw3c.infocollecttool.service.impl;

import com.dw3c.infocollecttool.entity.ScanLogs;
import com.dw3c.infocollecttool.mapper.IScanLogsMapper;
import com.dw3c.infocollecttool.service.IScanLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScanLogsServiceImpl implements IScanLogsService {


    @Autowired
    private IScanLogsMapper scanLogsMapper;

    @Override
    public Integer insert(ScanLogs logs) {
        return scanLogsMapper.insert(logs);
    }

    @Override
    public ScanLogs getById(Integer id) {
        return scanLogsMapper.getById(id);
    }

    @Override
    public List<ScanLogs> getAll() {
        return scanLogsMapper.getAll();
    }

    @Override
    public void update(ScanLogs logs) {
        scanLogsMapper.update(logs);
    }

    @Override
    public void delete(Integer id) {
        scanLogsMapper.delete(id);
    }
}
