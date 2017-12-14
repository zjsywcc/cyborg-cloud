package com.ese.cloud.client.dao.impl;


import com.ese.cloud.client.dao.UserInfoDao;
import com.ese.cloud.client.entity.UserInfo;
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
 * 用户信息操作
 * Created by rencong on 16/8/24.
 */
@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    Logger logger = Logger.getLogger(UserInfoDaoImpl.class);

    final String COLLECTION = "user_info";

    @Autowired
    MongoTemplate mongoTemplate;



    @Override
    public Boolean add(UserInfo userInfo) {
        try {
            mongoTemplate.save(userInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return null;
        }
    }

    @Override
    public UserInfo findByUsername(String username) {
        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("username").is(username));


            return mongoTemplate.findOne(query, UserInfo.class, COLLECTION);

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
    public List<UserInfo> getAll() {
        try {
            Query query = new Query();
            return mongoTemplate.find(query, UserInfo.class, COLLECTION);
        } catch (Exception e) {
            logger.error("findByUsername error:", e);
            return null;
        }
    }

    @Override
    public List<UserInfo> pageFind(int pageIndex, int pageSize) {
        try {
            Query query = new Query();
            query.skip(pageIndex);// skip相当于从那条记录开始
            query.limit(pageSize);// 从skip开始,取多少条记录

            return (List<UserInfo>) mongoTemplate.find(query, UserInfo.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long getCount() {
        try {
            Query query = new Query();

            return mongoTemplate.count(query, UserInfo.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0L;
        }
    }

    @Override
    public boolean delete(String id) {
        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));


            mongoTemplate.remove(query, UserInfo.class, COLLECTION);
            return true;

        } catch (Exception e) {
            logger.error("delete user error:", e);
            return false;
        }
    }

    @Override
    public boolean update(String id,String username,String email,String mob,String remark,String role,String wechat) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

            Update userInfo = new Update();
            userInfo.set("username",username);
            userInfo.set("email",email);
            userInfo.set("phone",mob);
            userInfo.set("remark",remark);
            userInfo.set("roleId",role);
            userInfo.set("wechat",wechat);

            mongoTemplate.updateFirst(query,userInfo,COLLECTION);
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

    @Override
    public UserInfo findById(String id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
            return mongoTemplate.findOne(query,UserInfo.class,COLLECTION);
        } catch (Exception e) {
            logger.error("find user by id error:", e);
            return null;
        }
    }
}
