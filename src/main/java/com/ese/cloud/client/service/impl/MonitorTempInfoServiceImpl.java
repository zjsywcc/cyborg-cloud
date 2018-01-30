package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.MonitorTempInfoDao;
import com.ese.cloud.client.entity.MonitorTempInfo;
import com.ese.cloud.client.service.MonitorTempInfoService;
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
 * Created by wangchengcheng on 2018/1/28.
 */
@Service("monitorTempInfoService")
public class MonitorTempInfoServiceImpl implements MonitorTempInfoService {

    Logger logger = LoggerFactory.getLogger(MonitorTempInfoServiceImpl.class);

    @Autowired
    MonitorTempInfoDao monitorTempInfoDao;

    @Override
    public boolean add(MonitorTempInfo monitorTempInfo) {
        return monitorTempInfoDao.add(monitorTempInfo);
    }

    @Override
    public boolean addAll(List<MonitorTempInfo> monitorTempInfos) {
        return monitorTempInfoDao.addAll(monitorTempInfos);
    }

    @Override
    public MonitorTempInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return monitorTempInfoDao.findOneByQuery(query);
    }

    @Override
    public List<MonitorTempInfo> findByIsRead(boolean isRead) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return monitorTempInfoDao.findByQuery(query);
    }

    public List<MonitorTempInfo> findByIsReadAndAhead(boolean isRead, long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.addCriteria(Criteria.where("timestamp").gte(timestamp));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return monitorTempInfoDao.findByQuery(query);
    }

    public List<MonitorTempInfo> findByIsReadAndUpdate(boolean isRead, long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.addCriteria(Criteria.where("timestamp").gte(timestamp));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        List<MonitorTempInfo> tempInfos = monitorTempInfoDao.findByQuery(query);
        for(MonitorTempInfo tempInfo : tempInfos) {
            tempInfo.setRead(true);
            update(tempInfo);
        }
        return tempInfos;
    }

    @Override
    public List<MonitorTempInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return monitorTempInfoDao.findByQuery(query);
    }

    @Override
    public List<MonitorTempInfo> all() {
        Query query = new Query();
        return monitorTempInfoDao.findByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return monitorTempInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return monitorTempInfoDao.delete(query);
    }

    @Override
    public boolean update(MonitorTempInfo monitorTempInfo) {
        return monitorTempInfoDao.update(monitorTempInfo);
    }
}
