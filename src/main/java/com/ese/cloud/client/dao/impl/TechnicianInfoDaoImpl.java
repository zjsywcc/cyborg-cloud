package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.TechnicianInfoDao;
import com.ese.cloud.client.entity.hunanUnicom.TechnicianInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 微信人员数据库操作
 * Created by rencong on 17/1/23.
 */
@Repository
public class TechnicianInfoDaoImpl implements TechnicianInfoDao {

    Logger logger = Logger.getLogger(OutsourcedInfoDaoImpl.class);

    final String COLLECTION = "technician_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(TechnicianInfo technicianInfo) {
        try{
            mongoTemplate.insert(technicianInfo,COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("add error",e);
            return false;
        }
    }

    @Override
    public List<TechnicianInfo> findByQuery(Query query) {
        try{
            return mongoTemplate.find(query,TechnicianInfo.class,COLLECTION);
        }catch (Exception e){
            logger.error("find error",e);
            return null;
        }
    }

    @Override
    public TechnicianInfo findOneByQuery(Query query) {
        try{
           return mongoTemplate.findOne(query,TechnicianInfo.class,COLLECTION);
        }catch (Exception e){
            logger.error("delete error",e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
       try{
           mongoTemplate.remove(query,COLLECTION);
           return true;
       }catch (Exception e){
           logger.error("delete error",e);
           return false;
       }
    }

    @Override
    public boolean update(TechnicianInfo technicianInfo) {
        try{
            mongoTemplate.save(technicianInfo,COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("update error",e);
            return false;
        }
    }

    @Override
    public Long count(Query query) {
        try {
            return mongoTemplate.count(query, COLLECTION);
        } catch (Exception e) {
            logger.error("count error", e);
            return 0L;
        }
    }
}
