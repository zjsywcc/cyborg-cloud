package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.ModuleInfoDao;
import com.ese.cloud.client.entity.ModuleInfo;
import com.ese.cloud.client.service.ModuleInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 模块信息实现类
 * Created by rencong on 16/12/23.
 */
@Service
public class ModuleInfoServiceImpl implements ModuleInfoService {

    Logger logger = LoggerFactory.getLogger(ModuleInfoServiceImpl.class);
    @Autowired
    ModuleInfoDao moduleInfoDao;

    @Override
    public boolean add(Integer level,String father,String name, String url, String key, String ico, String remark,Integer sort) {

        ModuleInfo moduleInfo = new ModuleInfo();
        moduleInfo.setIco(ico);
        moduleInfo.setKey(key);
        moduleInfo.setName(name);
        moduleInfo.setRemark(remark);
        moduleInfo.setUrl(url);
        moduleInfo.setLevel(level);
        moduleInfo.setParentId(father);
        moduleInfo.setSort(sort);

        return moduleInfoDao.add(moduleInfo);
    }

    @Override
    public boolean delete(String id) {
        return moduleInfoDao.delete(id);
    }

    @Override
    public boolean update(String id, Integer level,String father,String name, String url, String key, String ico, String remark,Integer sort) {

        ModuleInfo moduleInfo = new ModuleInfo();
        moduleInfo.setIco(ico);
        moduleInfo.setParentId(father);
        moduleInfo.setKey(key);
        moduleInfo.setName(name);
        moduleInfo.setRemark(remark);
        moduleInfo.setUrl(url);
        moduleInfo.setId(id);
        moduleInfo.setSort(sort);
        moduleInfo.setLevel(level);

        return moduleInfoDao.update(moduleInfo);
    }

    @Override
    public ModuleInfo findById(String id) {
        return moduleInfoDao.findById(id);
    }

    @Override
    public List<ModuleInfo> findAll() {
        return moduleInfoDao.getAll();
    }

    @Override
    public List<ModuleInfo> findByLevel(Integer level) {
        return moduleInfoDao.getByLevel(level);
    }

    @Override
    public List<ModuleInfo> findByIds(Set<String> ids) {
        return moduleInfoDao.findByIds(ids);
    }
}
