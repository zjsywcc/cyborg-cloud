package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.AppInfoDao;
import com.ese.cloud.client.entity.AppInfo;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



/**
 * app信息数据库操作
 */
@Repository
public class AppInfoDaoImpl implements AppInfoDao {

    Logger logger = Logger.getLogger(AppInfoDaoImpl.class);

    //表名
    final String tableName="app_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(AppInfo appInfo) {
        try{
            mongoTemplate.insert(appInfo,tableName);
            return true;
        }catch (Exception e){
            logger.error("app info insert error:",e);
            return false;
        }
    }

    @Override
    public AppInfo findOneByQuery(Query query) {
        try{
           return mongoTemplate.findOne(query,AppInfo.class,tableName);

        }catch (Exception e){
            logger.error("app info query error:",e);
            return null;
        }
    }

    @Override
    public List<AppInfo> findListByQuery(Query query) {
        try{
            return mongoTemplate.find(query,AppInfo.class,tableName);

        }catch (Exception e){
            logger.error("app info list query error:",e);
            return null;
        }
    }

    @Override
    public Boolean findByQuery(Query query) {
        try {
            if(mongoTemplate.count(query, tableName)==0){
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            logger.error("findbyquery error:", e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
        try{
            WriteResult result = mongoTemplate.remove(query,AppInfo.class,tableName);
            if(result.getN() != 0){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            logger.error("app info delete error:",e);
            return false;
        }
    }

    @Override
    public boolean update(AppInfo appInfo) {
        try{
            mongoTemplate.save(appInfo,tableName);
            return true;
        }catch (Exception e){
            logger.error("app info save error:",e);
            System.out.println("app info save error:" + e);
            return false;
        }
    }

    @Override
    public long count(Query query) {
        try{
            return mongoTemplate.count(query,AppInfo.class,tableName);
        }catch (Exception e){
            logger.error("app info save error:",e);
            return 0l;
        }
    }
}
