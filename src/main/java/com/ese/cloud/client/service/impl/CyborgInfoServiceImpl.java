package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.CyborgInfoDao;
import com.ese.cloud.client.entity.CyborgInfo;
import com.ese.cloud.client.service.CyborgInfoService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/10/16.
 */
@Service
public class CyborgInfoServiceImpl implements CyborgInfoService {

    Logger logger = LoggerFactory.getLogger(CyborgInfoServiceImpl.class);

    @Autowired
    CyborgInfoDao cyborgInfoDao;

    @Override
    public boolean add(CyborgInfo cyborgInfo) {
        return cyborgInfoDao.add(cyborgInfo);
    }

    @Override
    public CyborgInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return cyborgInfoDao.findOneByQuery(query);
    }

    @Override
    public List<CyborgInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return cyborgInfoDao.findByQuery(query);
    }

    @Override
    public List<CyborgInfo> all() {
        Query query = new Query();
        return cyborgInfoDao.findByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return cyborgInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return cyborgInfoDao.delete(query);
    }

    @Override
    public boolean update(CyborgInfo cyborgInfo) {
        return cyborgInfoDao.update(cyborgInfo);
    }
}
