package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.APIInfoDao;
import org.bson.types.ObjectId;
import com.ese.cloud.client.entity.APIInfo;
import com.ese.cloud.client.service.APIInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * API服务
 * Created by RENCONG on 17/1/24.
 */
@Service
public class APIInfoServiceImpl implements APIInfoService{

    Logger logger = LoggerFactory.getLogger(APIInfoServiceImpl.class);

    @Autowired
    APIInfoDao apiInfoDao;

    @Override
    public boolean add(APIInfo apiInfo) {

        try{
            apiInfoDao.add(apiInfo);
            return true;
        }catch (Exception e){
            logger.error("add error",e);
        }

        return false;
    }

    @Override
    public boolean delete(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

        try{
            apiInfoDao.delete(query);
            return true;
        }catch (Exception e){
            logger.error("delete error",e);
            return false;
        }

    }

    @Override
    public long count() {
        Query query = new Query();
        try{
            return apiInfoDao.count(query);
        }catch (Exception e){
            logger.error("delete error",e);
            return 0L;
        }
    }

    @Override
    public List<APIInfo> pageFind(int pageIndex, int pageSize) {

        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录

        try{
            return apiInfoDao.findByQuery(query);
        }catch (Exception e){
            logger.error("find error",e);
            return null;
        }
    }

    @Override
    public boolean update(APIInfo apiInfo) {
        return apiInfoDao.update(apiInfo);
    }

    @Override
    public APIInfo findById(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

        try{
            return apiInfoDao.findOneByQuery(query);
        }catch (Exception e){
            logger.error("find error",e);
            return null;
        }
    }
}
