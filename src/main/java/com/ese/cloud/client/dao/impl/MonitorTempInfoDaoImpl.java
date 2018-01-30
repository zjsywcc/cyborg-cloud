package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.MonitorTempInfoDao;
import com.ese.cloud.client.entity.MonitorTempInfo;
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
public class MonitorTempInfoDaoImpl implements MonitorTempInfoDao {

    Logger logger = Logger.getLogger(MonitorTempInfoDaoImpl.class);

    final String COLLECTION = "monitortemp_Info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(MonitorTempInfo monitorTempInfo) {
        try{
            mongoTemplate.insert(monitorTempInfo, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add monitorTempInfo error",e);
            return false;
        }
    }

    @Override
    public boolean addAll(List<MonitorTempInfo> monitorTempInfos) {
        try{
            mongoTemplate.insert(monitorTempInfos, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add monitorTempInfo list error",e);
            return false;
        }
    }

    @Override
    public List<MonitorTempInfo> findByQuery(Query query) {
        try{
            return mongoTemplate.find(query, MonitorTempInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find monitor temp Info error",e);
            return null;
        }
    }

    @Override
    public MonitorTempInfo findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query, MonitorTempInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find one monitor temp Info error",e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
        try{
            mongoTemplate.remove(query, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("delete monitor temp Info error",e);
            return false;
        }
    }

    @Override
    public boolean update(MonitorTempInfo monitorTempInfo) {
        try{
            mongoTemplate.save(monitorTempInfo, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("save monitor temp Info error",e);
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
