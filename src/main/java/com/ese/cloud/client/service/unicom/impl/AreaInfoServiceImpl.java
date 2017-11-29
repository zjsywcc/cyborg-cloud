package com.ese.cloud.client.service.unicom.impl;

import org.bson.types.ObjectId;
import com.ese.cloud.client.dao.AreaInfoDao;
import com.ese.cloud.client.entity.hunanUnicom.AreaInfo;
import com.ese.cloud.client.service.unicom.AreaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * 区域信息服务
 * Created by rencong on 17/1/24.
 */
@Service
public class AreaInfoServiceImpl implements AreaInfoService{


    @Autowired
    AreaInfoDao areaInfoDao;


    @Override
    public boolean add(AreaInfo areaInfo) {

        return areaInfoDao.add(areaInfo);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return areaInfoDao.delete(query);
    }


    @Override
    public boolean update(AreaInfo areaInfo) {
        return areaInfoDao.update(areaInfo);
    }

    @Override
    public AreaInfo getInfoById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return areaInfoDao.findOneByQuery(query);
    }

    @Override
    public AreaInfo getInfoByCode(String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(code));
        return areaInfoDao.findOneByQuery(query);
    }
}
