package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.RecordInfoDao;
import com.ese.cloud.client.entity.ReasonCount;
import com.ese.cloud.client.entity.RecordInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by mengchenyun on 2017/3/28.
 */
@Repository
public class RecordInfoDaoImpl implements RecordInfoDao {

    Logger logger = Logger.getLogger(RecordInfoDaoImpl.class);

    //表名
    final String tableName="record_info";

    @Autowired
    MongoTemplate mongoTemplate;
    
    @Override
    public boolean add(RecordInfo recordInfo) {
        try{
            mongoTemplate.save(recordInfo,tableName);
            return true;
        }catch (Exception e){
            logger.error("record info insert error:",e);
            return false;
        }
    }

    @Override
    public RecordInfo findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query, RecordInfo.class, tableName);
        }catch (Exception e){
            logger.error("record info query error:",e);
            return null;
        }
    }

    @Override
    public List<RecordInfo> findListByQuery(Query query) {
        try{
            return mongoTemplate.find(query, RecordInfo.class, tableName);


        }catch (Exception e){
            logger.error("record info list query error:",e);
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
            WriteResult result = mongoTemplate.remove(query,RecordInfo.class,tableName);
            if(result.getN() != 0){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            logger.error("record info delete error:",e);
            return false;
        }
    }

    @Override
    public boolean update(RecordInfo recordInfo) {
        try{
            mongoTemplate.save(recordInfo, tableName);
            return true;
        }catch (Exception e){
            logger.error("record info save error:",e);
            return false;
        }
    }

    @Override
    public long count(Query query) {
        try{
            return mongoTemplate.count(query, RecordInfo.class, tableName);
        }catch (Exception e){
            logger.error("record info save error:",e);
            return 0l;
        }
    }

    @Override
    public long countDistinctByLoginMob(Query query) {
        try{
            return mongoTemplate.getCollection(tableName).distinct("loginMob", query.getQueryObject()).size();
        }catch (Exception e){
            logger.error("record info get distinct count error:",e);
            return 0l;
        }
    }

    @Override
    public boolean update(String id, Update update) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

        try{
            mongoTemplate.updateFirst(query,update, tableName);
            return true;
        }catch (Exception e){
            logger.error("record info save error:",e);
            return false;
        }
    }

    public List<ReasonCount> getReasonCount(long start, long end) {
        GroupOperation group = group("reason").count().as("y").first("reason").as("name");
//        MatchOperation match = match(Criteria.where("createTime").lte(end).gte(start));
        Aggregation agg = newAggregation(group, project("name", "y"));
        AggregationResults<ReasonCount> groupResults
                = mongoTemplate.aggregate(agg, tableName, ReasonCount.class);
        List<ReasonCount> result = groupResults.getMappedResults();

        return result;
    }


    public List<BasicDBObject> getDataNum(int num){
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project("loginMob"),
                Aggregation.group("loginMob").count().as("total"),
                Aggregation.match(Criteria.where("total").gt(num))
        );

        AggregationResults<BasicDBObject> outputType = mongoTemplate.aggregate(agg,tableName, BasicDBObject.class);
        /*for (Iterator<BasicDBObject> iterator = outputType.iterator(); iterator.hasNext();) {
            DBObject obj =iterator.next();
            System.out.println(obj.toString());
        }*/

        return outputType.getMappedResults();


    }

    @Override
    public boolean checkUpdateMobSuccess(String mob) {
        Query query = new Query();
        query.addCriteria(Criteria.where("loginMob").is(mob));
        query.addCriteria(Criteria.where("result").is(1));

        try{
            return mongoTemplate.exists(query, tableName);
        }catch (Exception e){
            logger.error("record info check mob  error:",e);
            return false;
        }
    }


}
