package com.ese.cloud.client.dao.impl;

import com.ese.cloud.client.dao.NetworkReportDao;
import com.ese.cloud.client.entity.hunanUnicom.NetworkReport;
import com.ese.cloud.client.contants.ReportStatus;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 故障DB操作
 * Created by rencong on 16/12/24.
 */
@Repository
public class NetworkReportDaoImpl implements NetworkReportDao {

    Logger logger = Logger.getLogger(NetworkReportDaoImpl.class);

    final String COLLECTION = "unicom_report_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(NetworkReport report) {
        try {
            mongoTemplate.save(report, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return false;
        }
    }

    @Override
    public boolean del(String id) {
        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
            mongoTemplate.remove(query, NetworkReport.class, COLLECTION);
            return true;

        } catch (Exception e) {
            logger.error("del error:", e);
            return false;
        }
    }

    @Override
    public boolean updateStatus(String id, Integer status) {
        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

            Update update = new Update();
            update.set("status",status);
            if(status == ReportStatus.CHECKED.getIndex()){
                update.set("finishTime",new Date().getTime());
            }


            mongoTemplate.upsert(query,update, COLLECTION);

            return true;

        } catch (Exception e) {
            logger.error("update error:", e);
            return false;
        }
    }


    @Override
    public List<NetworkReport> pageFind(int pageIndex, int pageSize,Query query) {
        try {

            query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序
            query.skip(pageIndex);// skip相当于从那条记录开始
            query.limit(pageSize);// 从skip开始,取多少条记录


            return  mongoTemplate.find(query, NetworkReport.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long getCount() {
        try {
            Query query = new Query();

            return mongoTemplate.count(query, NetworkReport.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0L;
        }
    }

    @Override
    public List<NetworkReport> pageFindUnCheck(int pageIndex, int pageSize) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("status").ne(2));
            query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序
            query.skip(pageIndex);// skip相当于从那条记录开始
            query.limit(pageSize);// 从skip开始,取多少条记录

            return  mongoTemplate.find(query, NetworkReport.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<NetworkReport> find(Query query) {
        try {
            return  mongoTemplate.find(query, NetworkReport.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long count(Query query) {
        try {
            return  mongoTemplate.count(query, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long getUnCheckCount() {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("status").ne(2));
            //query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序

            return mongoTemplate.count(query, NetworkReport.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0L;
        }
    }

    @Override
    public List<NetworkReport> findByCreateTime(Long start, Long end) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("createTime").gte(start).andOperator(Criteria.where("createTime").lte(end)));
            query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序


            return  mongoTemplate.find(query, NetworkReport.class, COLLECTION);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(NetworkReport networkReport) {
        try {
            mongoTemplate.save(networkReport,COLLECTION);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
