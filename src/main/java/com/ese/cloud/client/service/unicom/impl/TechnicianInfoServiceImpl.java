package com.ese.cloud.client.service.unicom.impl;

import com.ese.cloud.client.dao.TechnicianInfoDao;
import com.ese.cloud.client.entity.hunanUnicom.TechnicianInfo;
import com.ese.cloud.client.service.unicom.TechnicianInfoService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维修人员服务
 * Created by rencong on 17/1/23.
 */
@Service
public class TechnicianInfoServiceImpl implements TechnicianInfoService {

    @Autowired
    TechnicianInfoDao technicianInfoDao;

    @Override
    public boolean add(TechnicianInfo technicianInfo) {
        return technicianInfoDao.add(technicianInfo);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return technicianInfoDao.delete(query);
    }

    @Override
    public long count() {
        Query query = new Query();
        return technicianInfoDao.count(query);
    }

    @Override
    public List<TechnicianInfo> pageFind(int pageIndex, int pageSize) {

        Query query = new Query();
        //query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录

        return technicianInfoDao.findByQuery(query);
    }

    @Override
    public List<TechnicianInfo> allInterfaceList() {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(2));
        return technicianInfoDao.findByQuery(query);
    }



    @Override
    public TechnicianInfo findOneById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

        return technicianInfoDao.findOneByQuery(query);
    }

    @Override
    public boolean update(TechnicianInfo technicianInfo) {
        return technicianInfoDao.update(technicianInfo);
    }
}
