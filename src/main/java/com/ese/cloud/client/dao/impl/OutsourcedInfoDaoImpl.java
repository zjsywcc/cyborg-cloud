package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.entity.hunanUnicom.OutsourcedInfo;
import com.ese.cloud.client.dao.OutsourcedInfoDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 外包公司信息管理
 * Created by RENCONGon 17/1/23.
 */
@Repository
public class OutsourcedInfoDaoImpl implements OutsourcedInfoDao {

    Logger logger = Logger.getLogger(OutsourcedInfoDaoImpl.class);

    final String COLLECTION = "outsourced_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(OutsourcedInfo outsourcedInfo) {
        try {
            mongoTemplate.save(outsourcedInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return false;
        }
    }

    @Override
    public List<OutsourcedInfo> findByQuery(Query query) {
        try {
            return mongoTemplate.find(query,OutsourcedInfo.class, COLLECTION);
        } catch (Exception e) {
            logger.error("find error:", e);
            return null;
        }
    }

    @Override
    public OutsourcedInfo findOneByQuery(Query query) {
        try {
            return mongoTemplate.findOne(query,OutsourcedInfo.class, COLLECTION);
        } catch (Exception e) {
            logger.error("find one error:", e);
            return null;
        }
    }


    @Override
    public boolean delete(Query query) {
        try {
             mongoTemplate.remove(query,OutsourcedInfo.class, COLLECTION);
            return true;
        } catch (Exception e) {
            logger.error("delete error:", e);
            return false;
        }
    }

    @Override
    public boolean update(OutsourcedInfo outsourcedInfo) {

        try {
            mongoTemplate.save(outsourcedInfo,COLLECTION);
            return true;
        } catch (Exception e) {
            logger.error("SAVE error:", e);
            return false;
        }
    }

    @Override
    public Long count(Query query) {
        try {
            return mongoTemplate.count(query,COLLECTION);
        } catch (Exception e) {
            logger.error("SAVE error:", e);
            return 0L;
        }
    }
}
