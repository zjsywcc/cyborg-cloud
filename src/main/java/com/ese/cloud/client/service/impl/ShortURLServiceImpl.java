package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.ShortURLDao;
import com.ese.cloud.client.entity.ShortURL;
import com.ese.cloud.client.service.ShortURLService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengchenyun on 2017/3/30.
 */
@Service
public class ShortURLServiceImpl implements ShortURLService {

    Logger logger = LoggerFactory.getLogger(ShortURLServiceImpl.class);

    @Autowired
    ShortURLDao shortURLDao;

    @Override
    public boolean add(ShortURL shortURL) {
        return shortURLDao.add(shortURL);
    }

    @Override
    public ShortURL findByShort(String shortURL) {
        Query query = new Query();
        query.addCriteria(Criteria.where("shortURL").is(shortURL));
        return shortURLDao.findOneByQuery(query);
    }

    @Override
    public ShortURL findByReal(String realURL) {
        Query query = new Query();
        query.addCriteria(Criteria.where("realURL").is(realURL));
        return shortURLDao.findOneByQuery(query);
    }

    @Override
    public ShortURL findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return shortURLDao.findOneByQuery(query);
    }

    @Override
    public boolean mobIsExist(String mob) {
        return false;
    }

    @Override
    public List<ShortURL> pageFind(int level, String userid, int pageIndex, int pageSize) {

        Query query = new Query();
        if (level != 1) {
            query.addCriteria(Criteria.where("userId").is(userid));
        }
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return shortURLDao.findListByQuery(query);
    }

    @Override
    public List<ShortURL> pageFindFilterIndividual(int pageIndex, int pageSize, String phone, String status, String date, String driverType) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        phone = phone.trim();
        status = status.trim();
        date = date.trim();
        Criteria criteria;
        List<Criteria> andCriteriaList = new ArrayList<Criteria>();
        if (!phone.isEmpty()) {
            andCriteriaList.add(Criteria.where("realURL").regex(phone));
        }
        if (!status.isEmpty()) {
            andCriteriaList.add(Criteria.where("status").regex(status));
        }
        if (!date.isEmpty()) {
            andCriteriaList.add(Criteria.where("createTime").regex(date));
        }
        if (!driverType.isEmpty()) {
            andCriteriaList.add(Criteria.where("driverType").regex(driverType));
        }
        if (andCriteriaList.isEmpty()) {
            criteria = new Criteria();
        } else {
            criteria = new Criteria().andOperator(andCriteriaList.toArray(new Criteria[andCriteriaList.size()]));
        }
        query.addCriteria(criteria);
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return shortURLDao.findListByQuery(query);
    }

    @Override
    public List<ShortURL> all() {
        Query query = new Query();
        return shortURLDao.findListByQuery(query);
    }

    @Override
    public Long countFilterIndividual(String phone, String status, String date, String driverType) {
        Query query = new Query();
        phone = phone.trim();
        status = status.trim();
        date = date.trim();
        Criteria criteria;
        List<Criteria> andCriteriaList = new ArrayList<Criteria>();
        if (!phone.isEmpty()) {
            andCriteriaList.add(Criteria.where("realURL").regex(phone));
        }
        if (!status.isEmpty()) {
            andCriteriaList.add(Criteria.where("status").regex(status));
        }
        if (!date.isEmpty()) {
            andCriteriaList.add(Criteria.where("createTime").regex(date));
        }
        if (!driverType.isEmpty()) {
            andCriteriaList.add(Criteria.where("driverType").regex(driverType));
        }
        if (andCriteriaList.isEmpty()) {
            criteria = new Criteria();
        } else {
            criteria = new Criteria().andOperator(andCriteriaList.toArray(new Criteria[andCriteriaList.size()]));
        }
        query.addCriteria(criteria);
        return shortURLDao.count(query);
    }


    public Long count(int level, String userid) {
        Query query = new Query();
        if (level != 1) {
            query.addCriteria(Criteria.where("userId").is(userid));
        }
        return shortURLDao.count(query);
    }

    /**
     * 查询某个业务线的短信发送总数
     * @param driverType
     * @return
     */
    public Long countByDriverType(String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return shortURLDao.count(query);
    }

    @Override
    public Long countSent() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("已发送"));
        return shortURLDao.count(query);
    }

    /**
     * 查询某个业务线已发送短信
     * @param driverType
     * @return
     */
    public Long countSentByDriverType(String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("已发送"));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return shortURLDao.count(query);
    }

    @Override
    public Long countSMSDaily(String date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").regex(date));
        return shortURLDao.count(query);
    }

    /**
     * 查询某个业务线日短信总量
     * @param date
     * @param driverType
     * @return
     */
    public Long countSMSDailyByDriverType(String date, String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").regex(date));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return shortURLDao.count(query);
    }

    @Override
    public Long countSentDaily(String date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("已发送"));
        query.addCriteria(Criteria.where("createTime").regex(date));
        return shortURLDao.count(query);
    }

    /**
     * 查询某个业务线日短信已发送的量
     * @param date
     * @param driverType
     * @return
     */
    public Long countSentDailyByDriverType(String date, String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("已发送"));
        query.addCriteria(Criteria.where("createTime").regex(date));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return shortURLDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return shortURLDao.delete(query);
    }

    @Override
    public boolean deleteByShortURL(String shortURL) {
        Query query = new Query();
        query.addCriteria(Criteria.where("shortURL").is(shortURL));
        return shortURLDao.delete(query);
    }

    @Override
    public boolean update(ShortURL shortURL) {
        return shortURLDao.update(shortURL);
    }

    @Override
    public ShortURL findByMob(String mob) {
        Query query = new Query();
        query.addCriteria(Criteria.where("realURL").is(mob));
        return shortURLDao.findOneByQuery(query);
    }

    @Override
    public long countByUser(long start, long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("已发送"));
        query.addCriteria(Criteria.where("createTime").gte(start).lte(end));

        return shortURLDao.count(query);
    }
}
