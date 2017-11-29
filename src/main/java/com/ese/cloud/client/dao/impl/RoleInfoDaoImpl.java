package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.RoleInfoDao;
import com.ese.cloud.client.entity.RoleInfo;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rencong on 16/9/10.
 */
@Repository
public class RoleInfoDaoImpl implements RoleInfoDao {

    Logger logger = Logger.getLogger(UserInfoDaoImpl.class);

    final String COLLECTION = "role_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(RoleInfo roleInfo) {
        try {
            mongoTemplate.save(roleInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return false;
        }
    }

    @Override
    public boolean update(RoleInfo roleInfo) {
        try {
            mongoTemplate.save(roleInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return false;
        }
    }

    @Override
    public RoleInfo findById(String id) {
        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(new ObjectId(id)));


            return mongoTemplate.findOne(query, RoleInfo.class, COLLECTION);

        } catch (Exception e) {
            logger.error("findByUsername error:", e);
            return null;
        }
    }

    @Override
    public List<RoleInfo> find(Query query) {
        try {
            return mongoTemplate.find(query, RoleInfo.class, COLLECTION);
        } catch (Exception e) {
            logger.error("find error:", e);
            return null;
        }
    }

    @Override
    public Long count(Query query) {
        try {
            return mongoTemplate.count(query, RoleInfo.class, COLLECTION);
        } catch (Exception e) {
            logger.error("count error:", e);
            return null;
        }
    }

    @Override
    public boolean delete(Query query) {
        try {
             mongoTemplate.remove(query, RoleInfo.class, COLLECTION);
            return true;
        } catch (Exception e) {
            logger.error("remove error:", e);
            return false;
        }
    }
}
