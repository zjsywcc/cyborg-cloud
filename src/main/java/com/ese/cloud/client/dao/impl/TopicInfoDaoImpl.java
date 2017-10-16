package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.TopicInfoDao;
import com.ese.cloud.client.entity.TopicInfo;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * topic
 * Created by rencong on 17/2/15.
 */
@Repository
public class TopicInfoDaoImpl implements TopicInfoDao {


    Logger logger = Logger.getLogger(TopicInfoDaoImpl.class);

    //表名
    final String tableName="topic_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(TopicInfo appInfo) {
        try{
            mongoTemplate.insert(appInfo,tableName);
            return true;
        }catch (Exception e){
            logger.error("topic insert info error:",e);
            return false;
        }
    }

    @Override
    public TopicInfo findOneByQuery(Query query) {
        try{
            TopicInfo topicInfo = mongoTemplate.findOne(query,TopicInfo.class,tableName);
            return topicInfo;
        }catch (Exception e){
            logger.error("topic find one error:",e);
            return null;
        }
    }

    @Override
    public List<TopicInfo> findListByQuery(Query query) {
        try{
            return mongoTemplate.find(query,TopicInfo.class,tableName);
        }catch (Exception e){
            logger.error("topic info find list error:",e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
        try{
            WriteResult ws = mongoTemplate.remove(query,TopicInfo.class,tableName);
            if(ws.getN()!=0){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            logger.error("topic delete error",e);
            return false;
        }
    }

    @Override
    public boolean update(TopicInfo appInfo) {
        try{
            mongoTemplate.save(appInfo, tableName);
            return true;
        }catch (Exception e){
            logger.error("topic update error:",e);
            return false;
        }
    }

    @Override
    public long count(Query query) {
        try{
             return mongoTemplate.count(query,tableName);
        }catch (Exception e){
            logger.error("",e);
            return 0l;
        }
    }
}
