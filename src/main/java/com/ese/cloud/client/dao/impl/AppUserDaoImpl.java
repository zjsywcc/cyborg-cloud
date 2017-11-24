package com.ese.cloud.client.dao.impl;


import com.ese.cloud.client.dao.AppUserDao;
import com.ese.cloud.client.entity.app.AppUser;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/11/23.
 */
@Repository
public class AppUserDaoImpl implements AppUserDao {

    Logger logger = Logger.getLogger(AppUserDaoImpl.class);

    final String COLLECTION = "appuser_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Boolean add(AppUser userInfo) {
        try {
            mongoTemplate.save(userInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return null;
        }
    }

    @Override
    public AppUser findById(String id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
            return mongoTemplate.findOne(query,AppUser.class,COLLECTION);
        } catch (Exception e) {
            logger.error("find user by id error:", e);
            return null;
        }
    }

    @Override
    public AppUser findByUsername(String username) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("username").is(username));
            return mongoTemplate.findOne(query, AppUser.class, COLLECTION);
        } catch (Exception e) {
            logger.error("findByUsername error:", e);
            return null;
        }
    }

    @Override
    public Boolean findByQuery(Query query) {
        try {
            if(mongoTemplate.count(query, COLLECTION)==0){
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
    public List<AppUser> listByQuery(Query query) {
        try {
            return  mongoTemplate.find(query, AppUser.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long getCount() {
        try {
            Query query = new Query();
            return mongoTemplate.count(query, AppUser.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0L;
        }
    }

    @Override
    public boolean update(AppUser appUser) {
        try {
            Query query = new Query();
            Update update = new Update();
            update.set("sex", appUser.getSex());
            update.set("age", appUser.getAge());
            update.set("fatigue_index", appUser.getFatigue_index());
            update.set("token", appUser.getToken());
            mongoTemplate.updateFirst(query, update, COLLECTION);
            return true;
        } catch (Exception e) {
            logger.error("update user error:", e);
            return false;
        }
    }

    @Override
    public boolean updatePassword(String id, String password) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
            Update userInfo = new Update();
            userInfo.set("password",password);
            mongoTemplate.updateFirst(query,userInfo,COLLECTION);
            return true;
        } catch (Exception e) {
            logger.error("update user password error:", e);
            return false;
        }
    }
}
