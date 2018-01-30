package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.MonitorEEGInfoDao;
import com.ese.cloud.client.entity.MonitorEEGInfo;
import com.ese.cloud.client.service.MonitorEEGInfoService;
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
@Service("monitorEEGInfoService")
public class MonitorEEGInfoServiceImpl implements MonitorEEGInfoService {

    Logger logger = LoggerFactory.getLogger(MonitorEEGInfoServiceImpl.class);

    @Autowired
    MonitorEEGInfoDao monitorEEGInfoDao;

    @Override
    public boolean add(MonitorEEGInfo monitorEEGInfo) {
        return monitorEEGInfoDao.add(monitorEEGInfo);
    }

    @Override
    public boolean addAll(List<MonitorEEGInfo> monitorEEGInfos) {
        return monitorEEGInfoDao.addAll(monitorEEGInfos);
    }

    @Override
    public MonitorEEGInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return monitorEEGInfoDao.findOneByQuery(query);
    }

    @Override
    public List<MonitorEEGInfo> findByIsRead(boolean isRead) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return monitorEEGInfoDao.findByQuery(query);
    }

    public List<MonitorEEGInfo> findByIsReadAndAhead(boolean isRead, long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.addCriteria(Criteria.where("timestamp").gte(timestamp));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return monitorEEGInfoDao.findByQuery(query);
    }

    public List<MonitorEEGInfo> findByIsReadAndUpdate(boolean isRead, long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.addCriteria(Criteria.where("timestamp").gte(timestamp));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        List<MonitorEEGInfo> eegInfos = monitorEEGInfoDao.findByQuery(query);
        for(MonitorEEGInfo eegInfo : eegInfos) {
            eegInfo.setRead(true);
            update(eegInfo);
        }
        return eegInfos;
    }

    @Override
    public List<MonitorEEGInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return monitorEEGInfoDao.findByQuery(query);
    }

    @Override
    public List<MonitorEEGInfo> all() {
        Query query = new Query();
        return monitorEEGInfoDao.findByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return monitorEEGInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return monitorEEGInfoDao.delete(query);
    }

    @Override
    public boolean update(MonitorEEGInfo monitorEEGInfo) {
        return monitorEEGInfoDao.update(monitorEEGInfo);
    }
}
