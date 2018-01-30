package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.MonitorEEGInfoDao;
import com.ese.cloud.client.entity.MonitorEEGInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
@Repository
public class MonitorEEGInfoDaoImpl implements MonitorEEGInfoDao {

    Logger logger = Logger.getLogger(MonitorEEGInfoDaoImpl.class);

    final String COLLECTION = "monitoreeg_Info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(MonitorEEGInfo monitorEEGInfo) {
        try{
            mongoTemplate.insert(monitorEEGInfo, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add monitorEEGInfo error",e);
            return false;
        }
    }

    @Override
    public boolean addAll(List<MonitorEEGInfo> monitorEEGInfos) {
        try{
            mongoTemplate.insert(monitorEEGInfos, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add monitorEEGInfo list error",e);
            return false;
        }
    }

    @Override
    public List<MonitorEEGInfo> findByQuery(Query query) {
        try{
            return mongoTemplate.find(query, MonitorEEGInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find monitor eeg Info error",e);
            return null;
        }
    }

    @Override
    public MonitorEEGInfo findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query, MonitorEEGInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find one monitor eeg Info error",e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
        try{
            mongoTemplate.remove(query, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("delete monitor eeg Info error",e);
            return false;
        }
    }

    @Override
    public boolean update(MonitorEEGInfo monitorEEGInfo) {
        try{
            mongoTemplate.save(monitorEEGInfo, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("save monitor eeg Info error",e);
            return false;
        }
    }

    @Override
    public Long count(Query query) {
        try{
            return mongoTemplate.count(query, COLLECTION);
        }catch (Exception e){
            logger.error("count error",e);
            return 0L;
        }
    }
}
