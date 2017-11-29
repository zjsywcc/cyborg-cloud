package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.entity.hunanUnicom.NetworkReportTrace;
import org.apache.log4j.Logger;
import com.ese.cloud.client.dao.NetworkReportTraceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 故障跟踪信息数据库澳洲
 * Created by rencong on 16/12/24.
 */
@Repository
public class NetworkReportTraceDaoImpl implements NetworkReportTraceDao{

    Logger logger = Logger.getLogger(NetworkReportDaoImpl.class);

    final String COLLECTION = "unicom_report_trace_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(NetworkReportTrace networkReport) {
        try {
            mongoTemplate.insert(networkReport, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return false;
        }
    }

    @Override
    public List<NetworkReportTrace> findByReportID(String id) {
        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("reportId").is(id));
            return mongoTemplate.find(query, NetworkReportTrace.class, COLLECTION);
        } catch (Exception e) {
            logger.error("findByReportID error:", e);
            return null;
        }
    }
}
