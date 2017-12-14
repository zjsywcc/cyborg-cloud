package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.ShortURLDao;
import com.ese.cloud.client.entity.ShortURL;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/30.
 */
@Repository
public class ShortURLDaoImpl implements ShortURLDao {

    Logger logger = Logger.getLogger(ShortURLDaoImpl.class);

    //表名
    final String tableName="short_url";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(ShortURL shortURL) {
        try{
            mongoTemplate.insert(shortURL,tableName);
            return true;
        }catch (Exception e){
            logger.error("short url insert error:",e);
            return false;
        }
    }

    @Override
    public ShortURL findOneByQuery(Query query) {
        try{
            return mongoTemplate.findOne(query, ShortURL.class, tableName);
        }catch (Exception e){
            logger.error("short url query error:",e);
            return null;
        }
    }

    @Override
    public List<ShortURL> findListByQuery(Query query) {
        try{
            return mongoTemplate.find(query, ShortURL.class, tableName);

        }catch (Exception e){
            logger.error("short url list query error:",e);
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
            WriteResult result = mongoTemplate.remove(query,ShortURL.class,tableName);
            if(result.getN() != 0){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            logger.error("short url delete error:",e);
            return false;
        }
    }

    @Override
    public boolean update(ShortURL shortURL) {
        try{
            mongoTemplate.save(shortURL, tableName);
            return true;
        }catch (Exception e){
            logger.error("short url save error:",e);
            System.out.println("short url save error:" + e);
            return false;
        }
    }

    @Override
    public long count(Query query) {
        try{
            return mongoTemplate.count(query, ShortURL.class, tableName);
        }catch (Exception e){
            logger.error("short url save error:",e);
            return 0l;
        }
    }




    public List<BasicDBObject> getDataNum(int num){
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project("realURL"),
                Aggregation.group("realURL").count().as("total"),
                Aggregation.match(Criteria.where("total").gt(num))
        );

        AggregationResults<BasicDBObject> outputType = mongoTemplate.aggregate(agg,tableName, BasicDBObject.class);
        /*for (Iterator<BasicDBObject> iterator = outputType.iterator(); iterator.hasNext();) {
            DBObject obj =iterator.next();
            System.out.println(obj.toString());
        }*/

        return outputType.getMappedResults();


    }


}
