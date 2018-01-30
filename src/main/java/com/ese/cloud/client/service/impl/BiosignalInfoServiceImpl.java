package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.BiosignalInfoDao;
import com.ese.cloud.client.entity.BiosignalInfo;
import com.ese.cloud.client.service.BiosignalInfoService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/27.
 */
@Service("biosignalInfoService")
public class BiosignalInfoServiceImpl implements BiosignalInfoService {

    Logger logger = LoggerFactory.getLogger(BiosignalInfoServiceImpl.class);

    @Autowired
    BiosignalInfoDao biosignalInfoDao;

    @Override
    public boolean add(BiosignalInfo biosignalInfo) {
        return biosignalInfoDao.add(biosignalInfo);
    }

    @Override
    public boolean addAll(List<BiosignalInfo> biosignalInfos) {
        return biosignalInfoDao.addAll(biosignalInfos);
    }

    @Override
    public BiosignalInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return biosignalInfoDao.findOneByQuery(query);
    }

    @Override
    public List<BiosignalInfo> findByIsRead(boolean isRead) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return biosignalInfoDao.findByQuery(query);
    }

    public List<BiosignalInfo> findByIsReadAndAhead(boolean isRead, long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.addCriteria(Criteria.where("timestamp").gte(timestamp));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return biosignalInfoDao.findByQuery(query);
    }

    public List<BiosignalInfo> findByIsReadAndUpdate(boolean isRead, long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.addCriteria(Criteria.where("timestamp").gte(timestamp));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        List<BiosignalInfo> emgInfos = biosignalInfoDao.findByQuery(query);
        for(BiosignalInfo emgInfo : emgInfos) {
            emgInfo.setRead(true);
            update(emgInfo);
        }
        return emgInfos;
    }

    @Override
    public List<BiosignalInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return biosignalInfoDao.findByQuery(query);
    }

    @Override
    public List<BiosignalInfo> all() {
        Query query = new Query();
        return biosignalInfoDao.findByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return biosignalInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return biosignalInfoDao.delete(query);
    }

    @Override
    public boolean update(BiosignalInfo biosignalInfo) {
        return biosignalInfoDao.update(biosignalInfo);
    }
}
