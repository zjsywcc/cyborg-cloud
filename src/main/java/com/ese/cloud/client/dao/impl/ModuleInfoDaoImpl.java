package com.ese.cloud.client.dao.impl;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.dao.ModuleInfoDao;
import com.ese.cloud.client.entity.ModuleInfo;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * 功能模块数据库操作
 * Created by rencong on 16/10/10.
 */
@Repository
public class ModuleInfoDaoImpl implements ModuleInfoDao {

    Logger logger = Logger.getLogger(ModuleInfoDaoImpl.class);

    final String COLLECTION = "module_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(ModuleInfo moduleInfo) {

        logger.info(JSON.toJSON(moduleInfo));

        try {
            mongoTemplate.insert(moduleInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("add error:", e);
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        logger.info(id);

        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
            WriteResult result =  mongoTemplate.remove(query, COLLECTION);
            if(result.getN() == 0){
                return false;
            }else {
                return true;
            }

        } catch (Exception e) {
            logger.error("delete error:", e);
            return false;
        }
    }

    @Override
    public boolean update(ModuleInfo moduleInfo) {
        logger.info(JSON.toJSON(moduleInfo));

        try {
            mongoTemplate.save(moduleInfo, COLLECTION);
            return  true;

        } catch (Exception e) {
            logger.error("update error:", e);
            return false;
        }
    }

    @Override
    public ModuleInfo findById(String id) {

        logger.info(id);

        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(new ObjectId(id)));
            return mongoTemplate.findOne(query, ModuleInfo.class, COLLECTION);

        } catch (Exception e) {
            logger.error("findbyid error:", e);
            return null;
        }
    }

    @Override
    public List<ModuleInfo> findByIds(Set<String> ids) {

        try {

            Query query = new Query();
//            query.addCriteria(Criteria.where("id").in(ids));
            query.with(new Sort(Sort.Direction.ASC, "sort"));//降序排序
            return mongoTemplate.find(query, ModuleInfo.class, COLLECTION);

        } catch (Exception e) {
            logger.error("findbyid error:", e);
            return null;
        }
    }

    @Override
    public boolean isExistKey(String key) {
        logger.info("key:"+key);

        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("key").is(key));
            Long count =  mongoTemplate.count(query,COLLECTION);
            if(count == 0){
                return  false;
            }else {
                return true;
            }

        } catch (Exception e) {
            logger.error("key error:", e);
            return true;
        }
    }

    @Override
    public List<ModuleInfo> getAll() {
        try {

            return mongoTemplate.findAll(ModuleInfo.class,COLLECTION);


        } catch (Exception e) {
            logger.error("查询全部模块信息错误:", e);
            return null;
        }
    }

    @Override
    public List<ModuleInfo> getByLevel(Integer level) {

        try {

            Query query = new Query();
            query.addCriteria(Criteria.where("level").is(level));
            return mongoTemplate.find(query,ModuleInfo.class,COLLECTION);

        } catch (Exception e) {
            logger.error("根据level查询模块信息错误:", e);
            return null;
        }
    }
}
