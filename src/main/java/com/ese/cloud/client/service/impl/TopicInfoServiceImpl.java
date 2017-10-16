package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.TopicInfoDao;
import com.ese.cloud.client.entity.TopicInfo;
import com.ese.cloud.client.service.TopicInfoService;
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
public class TopicInfoServiceImpl implements TopicInfoService {

    Logger logger = LoggerFactory.getLogger(TopicInfoServiceImpl.class);

    @Autowired
    TopicInfoDao topicInfoDao;

    @Override
    public boolean add(TopicInfo topicInfo) {
        return topicInfoDao.add(topicInfo);
    }

    @Override
    public TopicInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return topicInfoDao.findOneByQuery(query);
    }

    @Override
    public List<TopicInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return topicInfoDao.findListByQuery(query);
    }

    @Override
    public List<TopicInfo> all() {
        Query query = new Query();
        return topicInfoDao.findListByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return topicInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return topicInfoDao.delete(query);
    }

    @Override
    public boolean update(TopicInfo topicInfo) {
        return topicInfoDao.update(topicInfo);
    }
}
