package com.dw3c.infocollecttool.service.impl;

import com.dw3c.infocollecttool.entity.InfoCollection;
import com.dw3c.infocollecttool.entity.UploadFile;
import com.dw3c.infocollecttool.mapper.IInfoCollectionMapper;
import com.dw3c.infocollecttool.service.IInfoCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoCollectionServiceImpl implements IInfoCollectionService {

    @Autowired
    private IInfoCollectionMapper infoCollectionMapper;

    @Override
    public Integer insert(InfoCollection info) {
        return infoCollectionMapper.insert(info);
    }

    @Override
    public InfoCollection getById(Integer id) {
        return infoCollectionMapper.getById(id);
    }

    @Override
    public List<InfoCollection> getAll() {
        return infoCollectionMapper.getAll();
    }

    @Override
    public void update(InfoCollection info) {
        infoCollectionMapper.update(info);
    }

    @Override
    public void delete(Integer id) {
        infoCollectionMapper.delete(id);
    }
}
