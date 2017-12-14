package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.NetworkReportStatisticsDao;
import com.ese.cloud.client.entity.hunanUnicom.NetworkReportStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rencong on 16/12/28.
 */
@Repository
public class NetworkReportStatisticsDaoImpl implements NetworkReportStatisticsDao {

    private Logger logger = LoggerFactory.getLogger(NetworkReportStatisticsDaoImpl.class);

    private String COLLECTION_NAME = "network_report_statistics";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean add(NetworkReportStatistics statistics) {
        try{
            mongoTemplate.save(statistics,COLLECTION_NAME);
        }catch (Exception e){
            logger.error("mongo add error",e);
            return false;
        }
        return true;
    }

    @Override
    public List<NetworkReportStatistics> find(Query query) {
        try{
            return mongoTemplate.find(query,NetworkReportStatistics.class,COLLECTION_NAME);
        }catch (Exception e){
            logger.error("mongo find error",e);
            return null;
        }
    }
}
