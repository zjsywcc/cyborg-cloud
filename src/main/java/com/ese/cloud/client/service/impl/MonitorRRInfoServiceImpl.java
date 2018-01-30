package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.MonitorRRInfoDao;
import com.ese.cloud.client.entity.MonitorRRInfo;
import com.ese.cloud.client.service.MonitorRRInfoService;
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
@Service("monitorRRInfoService")
public class MonitorRRInfoServiceImpl implements MonitorRRInfoService {

    Logger logger = LoggerFactory.getLogger(MonitorRRInfoServiceImpl.class);

    @Autowired
    MonitorRRInfoDao monitorRRInfoDao;

    @Override
    public boolean add(MonitorRRInfo monitorRRInfo) {
        return monitorRRInfoDao.add(monitorRRInfo);
    }

    @Override
    public boolean addAll(List<MonitorRRInfo> monitorRRInfos) {
        return monitorRRInfoDao.addAll(monitorRRInfos);
    }

    @Override
    public MonitorRRInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return monitorRRInfoDao.findOneByQuery(query);
    }

    @Override
    public List<MonitorRRInfo> findByIsRead(boolean isRead) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return monitorRRInfoDao.findByQuery(query);
    }

    public List<MonitorRRInfo> findByIsReadAndAhead(boolean isRead, long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.addCriteria(Criteria.where("timestamp").gte(timestamp));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return monitorRRInfoDao.findByQuery(query);
    }

    public List<MonitorRRInfo> findByIsReadAndUpdate(boolean isRead, long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.addCriteria(Criteria.where("timestamp").gte(timestamp));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        List<MonitorRRInfo> rrInfos = monitorRRInfoDao.findByQuery(query);
        for(MonitorRRInfo rrInfo : rrInfos) {
            rrInfo.setRead(true);
            update(rrInfo);
        }
        return rrInfos;
    }

    @Override
    public List<MonitorRRInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return monitorRRInfoDao.findByQuery(query);
    }

    @Override
    public List<MonitorRRInfo> all() {
        Query query = new Query();
        return monitorRRInfoDao.findByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return monitorRRInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return monitorRRInfoDao.delete(query);
    }

    @Override
    public boolean update(MonitorRRInfo monitorRRInfo) {
        return monitorRRInfoDao.update(monitorRRInfo);
    }
}
