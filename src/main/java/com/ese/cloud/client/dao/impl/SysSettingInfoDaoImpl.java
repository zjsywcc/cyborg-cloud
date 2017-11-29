package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.SysSettingInfoDao;
import com.ese.cloud.client.entity.SysSettingInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by user on 17/4/18.
 */
@Repository
public class SysSettingInfoDaoImpl implements SysSettingInfoDao{

    Logger logger = Logger.getLogger(SysSettingInfoDaoImpl.class);

    final String COLLECTION = "syssetting_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean updateSendMsgStatus(boolean status) {
        try {
            Query query = new Query();
            Update update = new Update();
            update.set("sendResultStatus", status);
            mongoTemplate.upsert(query, update, COLLECTION);
            return true;
        }catch (Exception e){
            logger.error("update error",e);
            return false;
        }
    }

    @Override
    public SysSettingInfo getSysConfig() {
        try {
            Query query = new Query();
            return mongoTemplate.findOne(query,SysSettingInfo.class,COLLECTION);
        }catch (Exception e){
            logger.error("get sys config error",e);
            return null;
        }
    }
}
