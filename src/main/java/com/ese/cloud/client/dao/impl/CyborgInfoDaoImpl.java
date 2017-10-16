package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.CyborgInfoDao;
import com.ese.cloud.client.entity.CyborgInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * api信息数据操作
 * Created by wangchengcheng on 17/10/16.
 */
@Repository
public class CyborgInfoDaoImpl implements CyborgInfoDao {

    Logger logger = Logger.getLogger(CyborgInfoDaoImpl.class);

    final String COLLECTION = "cyborg_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(CyborgInfo cyborgInfo) {
        try{
            mongoTemplate.insert(cyborgInfo, COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add cyborgInfo error",e);
            return false;
        }
    }

    @Override
    public List<CyborgInfo> findByQuery(Query query) {
        try{
            return mongoTemplate.find(query, CyborgInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find cyborg info error",e);
            return null;
        }
    }

    @Override
    public CyborgInfo findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query, CyborgInfo.class, COLLECTION);
        }catch(Exception e){
            logger.error("find one cyborg info error",e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
        try{
            mongoTemplate.remove(query, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("delete cyborg info error",e);
            return false;
        }
    }

    @Override
    public boolean update(CyborgInfo cyborgInfo) {
        try{
            mongoTemplate.save(cyborgInfo, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("save cyborg info error",e);
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
