package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.MonitorRRInfoDao;
import com.ese.cloud.client.entity.MonitorRRInfo;
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
public class MonitorRRInfoDaoImpl implements MonitorRRInfoDao {

    Logger logger = Logger.getLogger(MonitorRRInfoDaoImpl.class);

    final String COLLECTION = "monitorrr_Info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(MonitorRRInfo monitorRRInfo) {
        try{
            mongoTemplate.insert(monitorRRInfo, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add monitorRRInfo error",e);
            return false;
        }
    }

    @Override
    public boolean addAll(List<MonitorRRInfo> monitorRRInfos) {
        try{
            mongoTemplate.insert(monitorRRInfos, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add monitorRRInfo list error",e);
            return false;
        }
    }

    @Override
    public List<MonitorRRInfo> findByQuery(Query query) {
        try{
            return mongoTemplate.find(query, MonitorRRInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find monitor rr Info error",e);
            return null;
        }
    }

    @Override
    public MonitorRRInfo findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query, MonitorRRInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find one monitor rr Info error",e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
        try{
            mongoTemplate.remove(query, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("delete monitor rr Info error",e);
            return false;
        }
    }

    @Override
    public boolean update(MonitorRRInfo monitorRRInfo) {
        try{
            mongoTemplate.save(monitorRRInfo, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("save monitor rr Info error",e);
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
