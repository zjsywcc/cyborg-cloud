package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.AppInfoDao;
import com.ese.cloud.client.entity.AppInfo;
import com.ese.cloud.client.service.AppInfoService;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rencong on 17/2/15.
 */
@Service
public class AppInfoServiceImpl implements AppInfoService {

    Logger logger = LoggerFactory.getLogger(AppInfoServiceImpl.class);

    @Autowired
    AppInfoDao appInfoDao;

    @Override
    public boolean add(AppInfo appInfo) {

        return appInfoDao.add(appInfo);

    }

    @Override
    public AppInfo findById(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return appInfoDao.findOneByQuery(query);
    }

    @Override
    public List<AppInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return appInfoDao.findListByQuery(query);
    }

    @Override
    public List<AppInfo> all() {
        Query query = new Query();
        return appInfoDao.findListByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();

        return appInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return appInfoDao.delete(query);
    }

    @Override
    public boolean update(AppInfo appInfo) {

        return appInfoDao.update(appInfo);
    }

    @Override
    public boolean validateAppInfo(String id,String type,String value) {
        Query query = new Query();
        if(StringUtils.isNotEmpty(id)) {
            query.addCriteria(Criteria.where("_id").ne(new ObjectId(id)));
        }
        if(StringUtils.equals("0",type)){
            query.addCriteria(Criteria.where("name").is(value));
        }
        if(StringUtils.equals("1",type)){
            query.addCriteria(Criteria.where("code").is(value));
        }
        if(StringUtils.equals("2",type)){
            query.addCriteria(Criteria.where("owner").is(value));
        }
        if(StringUtils.equals("3",type)){
            query.addCriteria(Criteria.where("mob").is(value));
        }
        if(StringUtils.equals("4",type)){
            query.addCriteria(Criteria.where("email").is(value));
        }
        return appInfoDao.findByQuery(query);
    }
}
