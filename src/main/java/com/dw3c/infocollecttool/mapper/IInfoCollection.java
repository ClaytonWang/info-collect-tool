package com.dw3c.infocollecttool.mapper;

import com.dw3c.infocollecttool.entity.InfoCollection;
import com.dw3c.infocollecttool.entity.ScanLogs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInfoCollection {

    void insert(@Param("info") InfoCollection info);
    InfoCollection getById(@Param("id") String id);
    List<InfoCollection> getAll();
    void update(@Param("log") InfoCollection info);
    void delete(@Param("id") Long id);
}


