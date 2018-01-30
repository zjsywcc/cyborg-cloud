package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.BiosignalInfoDao;
import com.ese.cloud.client.entity.BiosignalInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/27.
 */
@Repository
public class BiosignalInfoDaoImpl implements BiosignalInfoDao {

    Logger logger = Logger.getLogger(BiosignalInfoDaoImpl.class);

    final String COLLECTION = "biosignal_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(BiosignalInfo biosignalInfo) {
        try{
            mongoTemplate.insert(biosignalInfo, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add biosignalInfo error",e);
            return false;
        }
    }

    @Override
    public boolean addAll(List<BiosignalInfo> biosignalInfos) {
        try{
            mongoTemplate.insert(biosignalInfos, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add biosignalInfo list error",e);
            return false;
        }
    }

    @Override
    public List<BiosignalInfo> findByQuery(Query query) {
        try{
            return mongoTemplate.find(query, BiosignalInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find monitor emg Info error",e);
            return null;
        }
    }

    @Override
    public BiosignalInfo findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query, BiosignalInfo.class, COLLECTION);
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
    public boolean update(BiosignalInfo biosignalInfo) {
        try{
            mongoTemplate.save(biosignalInfo, COLLECTION);
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
