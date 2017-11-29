package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.ClientInfoDao;
import com.ese.cloud.client.entity.ClientInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户信息操作
 * Created by rencong on 16/12/27.
 */
@Repository
public class ClientInfoDaoImpl implements ClientInfoDao {


    Logger logger = Logger.getLogger(ClientInfoDaoImpl.class);

    final String COLLECTION = "client_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ClientInfo> find(Query query) {
        try {
            return mongoTemplate.find(query,ClientInfo.class, COLLECTION);

        } catch (Exception e) {
            logger.error("find error:", e);
            return null;
        }
    }

    @Override
    public Long count(Query query) {
        try {
            return mongoTemplate.count(query,ClientInfo.class, COLLECTION);

        } catch (Exception e) {
            logger.error("count error:", e);
            return null;
        }
    }

    @Override
    public boolean del(Query query) {
        try {
            mongoTemplate.remove(query,ClientInfo.class, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("del error:", e);
            return false;
        }
    }

    @Override
    public boolean update(ClientInfo clientInfo) {
        try {
            mongoTemplate.save(clientInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("update error:", e);
            return false;
        }
    }

    @Override
    public boolean add(ClientInfo clientInfo) {
        try {
            mongoTemplate.insert(clientInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return false;
        }
    }
}
