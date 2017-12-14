package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.MetricInfoDao;
import com.ese.cloud.client.entity.MetricInfo;
import com.ese.cloud.client.service.MetricInfoService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/1.
 */
@Service
public class MetricInfoServiceImpl implements MetricInfoService {

    Logger logger = LoggerFactory.getLogger(MetricInfoService.class);

    @Autowired
    MetricInfoDao metricInfoDao;

    @Override
    public boolean add(MetricInfo metricInfo) {
        return metricInfoDao.add(metricInfo);
    }

    @Override
    public MetricInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return metricInfoDao.findOneByQuery(query);
    }

    @Override
    public List<MetricInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return metricInfoDao.findListByQuery(query);
    }

    @Override
    public List<MetricInfo> all() {
        Query query = new Query();
        return metricInfoDao.findListByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return metricInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return metricInfoDao.delete(query);
    }

    @Override
    public boolean update(MetricInfo metricInfo) {
        return metricInfoDao.update(metricInfo);
    }
}
