package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.MonitorEMGInfoDao;
import com.ese.cloud.client.entity.MonitorEMGInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/11/9.
 */
public class MonitorEMGInfoDaoImpl implements MonitorEMGInfoDao {

    Logger logger = Logger.getLogger(MonitorEMGInfoDaoImpl.class);

    final String COLLECTION = "monitoremg_Info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(MonitorEMGInfo monitorEMGInfo) {
        try{
            mongoTemplate.insert(monitorEMGInfo, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add monitorEMGInfo error",e);
            return false;
        }
    }

    @Override
    public boolean addAll(List<MonitorEMGInfo> monitorEMGInfos) {
        try{
            mongoTemplate.insert(monitorEMGInfos, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add monitorEMGInfo list error",e);
            return false;
        }
    }

    @Override
    public List<MonitorEMGInfo> findByQuery(Query query) {
        try{
            return mongoTemplate.find(query, MonitorEMGInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find monitor emg Info error",e);
            return null;
        }
    }

    @Override
    public MonitorEMGInfo findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query, MonitorEMGInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find one monitor emg Info error",e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
        try{
            mongoTemplate.remove(query, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("delete monitor emg Info error",e);
            return false;
        }
    }

    @Override
    public boolean update(MonitorEMGInfo monitorEMGInfo) {
        try{
            mongoTemplate.save(monitorEMGInfo, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("save monitor emg Info error",e);
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
