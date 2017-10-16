package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.ClientNetworkInfoDao;
import com.ese.cloud.client.entity.hunanUnicom.ClientNetworkInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 客户相关联的网络设备信息
 * Created by user on 16/12/27.
 */
@Repository
public class ClientNetworkInfoDaoImpl implements ClientNetworkInfoDao {


    Logger logger = Logger.getLogger(ClientNetworkInfoDaoImpl.class);

    final String COLLECTION = "client_network_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ClientNetworkInfo> find(Query query) {
        try {
            return mongoTemplate.find(query,ClientNetworkInfo.class, COLLECTION);

        } catch (Exception e) {
            logger.error("find error:", e);
            return null;
        }
    }

    @Override
    public boolean del(Query query) {
        try {
            mongoTemplate.remove(query,ClientNetworkInfo.class, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("del error:", e);
            return false;
        }
    }

    @Override
    public boolean update(ClientNetworkInfo clientInfo) {
        try {
            mongoTemplate.save(clientInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("update error:", e);
            return false;
        }
    }

    @Override
    public boolean add(ClientNetworkInfo clientInfo) {
        try {
            mongoTemplate.insert(clientInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return false;
        }
    }
}
