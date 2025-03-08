package com.dw3c.infocollecttool.service;

import com.dw3c.infocollecttool.entity.InfoCollection;
import com.dw3c.infocollecttool.entity.UploadFile;

import java.util.List;

public interface IInfoCollectionService {
    Integer insert(InfoCollection info);
    InfoCollection getById(Integer id);
    List<InfoCollection> getAll();
    void update(InfoCollection info);
    void delete(Integer id);
}
