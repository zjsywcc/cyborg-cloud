package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.MetricInfoDao;
import com.ese.cloud.client.entity.MetricInfo;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/1.
 */
@Repository
public class MetricInfoDaoImpl implements MetricInfoDao {

    Logger logger = Logger.getLogger(MetricInfoDaoImpl.class);

    //表名
    final String tableName="metric_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(MetricInfo metricInfo) {
        try{
            mongoTemplate.insert(metricInfo, tableName);
            return true;
        }catch (Exception e){
            logger.error("metric insert info error:",e);
            return false;
        }
    }

    @Override
    public MetricInfo findOneByQuery(Query query) {
        try{
            MetricInfo metricInfo = mongoTemplate.findOne(query, MetricInfo.class, tableName);
            return metricInfo;
        }catch (Exception e){
            logger.error("metric find one error:",e);
            return null;
        }
    }

    @Override
    public List<MetricInfo> findListByQuery(Query query) {
        try{
            return mongoTemplate.find(query, MetricInfo.class, tableName);
        }catch (Exception e){
            logger.error("metric info find list error:",e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
        try{
            WriteResult ws = mongoTemplate.remove(query, MetricInfo.class, tableName);
            if(ws.getN()!=0){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            logger.error("metric delete error",e);
            return false;
        }
    }

    @Override
    public boolean update(MetricInfo metricInfo) {
        try {
            mongoTemplate.save(metricInfo, tableName);
            return true;
        } catch (Exception e){
            logger.error("metric update error:",e);
            return false;
        }
    }

    @Override
    public long count(Query query) {
        try{
            return mongoTemplate.count(query, tableName);
        }catch (Exception e){
            logger.error("",e);
            return 0l;
        }
    }
}
