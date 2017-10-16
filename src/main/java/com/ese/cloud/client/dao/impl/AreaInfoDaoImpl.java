package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.AreaInfoDao;
import com.ese.cloud.client.entity.hunanUnicom.AreaInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 区域信息
 * Created by rencong on 17/1/24.
 */
@Repository
public class AreaInfoDaoImpl implements AreaInfoDao {

    Logger logger = Logger.getLogger(AreaInfoDaoImpl.class);

    final String COLLECTION = "area_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(AreaInfo areaInfo) {
        try{
            mongoTemplate.insert(areaInfo,COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("insert error",e);
            return false;
        }
    }

    @Override
    public List<AreaInfo> findByQuery(Query query) {
        try{
            return mongoTemplate.find(query,AreaInfo.class,COLLECTION);
        }catch (Exception e){
            logger.error("find error",e);
            return null;
        }
    }

    @Override
    public AreaInfo findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query,AreaInfo.class,COLLECTION);
        }catch (Exception e){
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
            logger.error("remove error",e);
            return false;
        }
    }

    @Override
    public boolean update(AreaInfo areaInfo) {
        try{
           mongoTemplate.save(areaInfo);
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
