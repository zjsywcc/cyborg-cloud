package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.APIInfoDao;
import com.ese.cloud.client.entity.APIInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * api信息数据操作
 * Created by rencong on 17/1/24.
 */
@Repository
public class APIInfoDaoImpl implements APIInfoDao {

    Logger logger = Logger.getLogger(APIInfoDaoImpl.class);

    final String COLLECTION = "api_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(APIInfo apiInfo) {

        try{
            mongoTemplate.insert(apiInfo,COLLECTION);
            return true;
        }catch(Exception e){
            logger.error("add error",e);
            return false;
        }
    }

    @Override
    public List<APIInfo> findByQuery(Query query) {
        try{
            return mongoTemplate.find(query,APIInfo.class,COLLECTION);
        }catch(Exception e){
            logger.error("find error",e);
            return null;
        }
    }

    @Override
    public APIInfo findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query,APIInfo.class,COLLECTION);
        }catch(Exception e){
            logger.error("find one error",e);
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
    public boolean update(APIInfo apiInfo) {

        try{
            mongoTemplate.save(apiInfo,COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("save error",e);
            return false;
        }

    }

    @Override
    public Long count(Query query) {

        try{
           return mongoTemplate.count(query,COLLECTION);
        }catch (Exception e){
            logger.error("count error",e);
            return 0L;
        }

    }
}
