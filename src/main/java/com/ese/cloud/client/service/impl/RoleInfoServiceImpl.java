package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.RoleInfoDao;
import com.ese.cloud.client.entity.RoleInfo;
import com.ese.cloud.client.service.RoleInfoService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rencong on 16/9/10.
 */
@Service("roleInfoService")
public class RoleInfoServiceImpl implements RoleInfoService {


    Logger logger = LoggerFactory.getLogger(RoleInfoServiceImpl.class);

    @Autowired
    RoleInfoDao roleInfoDao;

    @Override
    public boolean add(RoleInfo roleInfo) {

        return roleInfoDao.add(roleInfo);

    }

    @Override
    public RoleInfo findById(String id) {
        return roleInfoDao.findById(id);
    }

    @Override
    public List<RoleInfo> pageFind(int pageIndex, int pageSize) {

        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录

        return roleInfoDao.find(query);
    }

    @Override
    public List<RoleInfo> all() {
        Query query = new Query();
        return roleInfoDao.find(query);
    }

    @Override
    public Long count(Query query) {
        return roleInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

        return roleInfoDao.delete(query);
    }

    @Override
    public boolean update(RoleInfo roleInfo) {
        return roleInfoDao.update(roleInfo);
    }


}
