package com.ese.cloud.client.service.unicom.impl;
import com.ese.cloud.client.entity.hunanUnicom.OutsourcedInfo;
import com.ese.cloud.client.service.unicom.OutsourcedInfoService;
import com.ese.cloud.client.dao.OutsourcedInfoDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外包公司信息管理
 * Created by RENCONG on 17/1/23.
 */
@Service
public class OutsourcedInfoServiceImpl implements OutsourcedInfoService {

    @Autowired
    OutsourcedInfoDao outsourcedInfoDao;

    @Override
    public boolean add(OutsourcedInfo outsourcedInfo){
        return outsourcedInfoDao.add(outsourcedInfo);
    }

    @Override
    public boolean delete(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

        return outsourcedInfoDao.delete(query);
    }

    @Override
    public long count() {
        Query query = new Query();
        return outsourcedInfoDao.count(query);
    }

    @Override
    public List<OutsourcedInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        //query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return outsourcedInfoDao.findByQuery(query);
    }

    @Override
    public List<OutsourcedInfo> all() {
        Query query = new Query();
        return outsourcedInfoDao.findByQuery(query);
    }

    @Override
    public boolean update(OutsourcedInfo outsourcedInfo) {
        return outsourcedInfoDao.update(outsourcedInfo);
    }

    @Override
    public OutsourcedInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return outsourcedInfoDao.findOneByQuery(query);
    }

}
