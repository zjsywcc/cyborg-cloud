package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.MonitorEMGInfoDao;
import com.ese.cloud.client.entity.MonitorEMGInfo;
import com.ese.cloud.client.service.MonitorEMGInfoService;
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
 * Created by wangchengcheng on 2017/11/9.
 */
@Service("monitorEMGInfoService")
public class MonitorEMGInfoServiceImpl implements MonitorEMGInfoService {

    Logger logger = LoggerFactory.getLogger(MonitorEMGInfoServiceImpl.class);

    @Autowired
    MonitorEMGInfoDao monitorEMGInfoDao;

    @Override
    public boolean add(MonitorEMGInfo monitorEMGInfo) {
        return monitorEMGInfoDao.add(monitorEMGInfo);
    }

    @Override
    public boolean addAll(List<MonitorEMGInfo> monitorEMGInfos) {
        return monitorEMGInfoDao.addAll(monitorEMGInfos);
    }

    @Override
    public MonitorEMGInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return monitorEMGInfoDao.findOneByQuery(query);
    }

    @Override
    public List<MonitorEMGInfo> findByIsRead(boolean isRead) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return monitorEMGInfoDao.findByQuery(query);
    }

    public List<MonitorEMGInfo> findByIsReadAndAhead(boolean isRead, long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isRead").is(isRead));
        query.addCriteria(Criteria.where("timestamp").gte(timestamp));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        return monitorEMGInfoDao.findByQuery(query);
    }

    @Override
    public List<MonitorEMGInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return monitorEMGInfoDao.findByQuery(query);
    }

    @Override
    public List<MonitorEMGInfo> all() {
        Query query = new Query();
        return monitorEMGInfoDao.findByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return monitorEMGInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return monitorEMGInfoDao.delete(query);
    }

    @Override
    public boolean update(MonitorEMGInfo monitorEMGInfo) {
        return monitorEMGInfoDao.update(monitorEMGInfo);
    }
}
