package com.dw3c.infocollecttool.mapper;

import com.dw3c.infocollecttool.entity.InfoCollection;
import com.dw3c.infocollecttool.entity.ScanLogs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInfoCollectionMapper {

    Integer insert(InfoCollection info);
    InfoCollection getById(Integer id);
    List<InfoCollection> getAll();
    void update(InfoCollection info);
    void delete(Integer id);
}


