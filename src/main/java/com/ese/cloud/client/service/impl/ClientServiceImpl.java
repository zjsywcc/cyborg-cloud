package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.ClientInfoDao;
import com.ese.cloud.client.entity.ClientInfo;
import com.ese.cloud.client.service.ClientService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户信息服务类
 * Created by rencong on 16/12/27.
 */
@Service
public class ClientServiceImpl implements ClientService {

    Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientInfoDao clientInfoDao;

    @Override
    public boolean add(String name, Integer type, String address, String mobphone, String telephone, String contact,String OLT,String ip,String accessNo,String remark,Double lat,Double lng) {

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setAccessNo(accessNo);
        clientInfo.setAddress(address);
        clientInfo.setContact(contact);
        clientInfo.setIp(ip);
        clientInfo.setMobPhone(mobphone);
        clientInfo.setName(name);
        clientInfo.setOLT(OLT);
        clientInfo.setTelePhone(telephone);
        clientInfo.setType(type);
        clientInfo.setRemark(remark);


        return clientInfoDao.add(clientInfo);
    }

    @Override
    public boolean add(ClientInfo clientInfo) {

        Query query = new Query();
        query.addCriteria(Criteria.where("geoID").is(clientInfo.getGeoID()));

        if(clientInfoDao.find(query) == null){
            clientInfoDao.add(clientInfo);
        }

        return true;
    }

    @Override
    public boolean update(String id, String name, Integer type, String address, String mobphone, String telephone, String contact,String OLT,String ip,String accessNo,String remark,Double lat,Double lng) {

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setAccessNo(accessNo);
        clientInfo.setAddress(address);
        clientInfo.setContact(contact);
        clientInfo.setIp(ip);
        clientInfo.setMobPhone(mobphone);
        clientInfo.setName(name);
        clientInfo.setOLT(OLT);
        clientInfo.setTelePhone(telephone);
        clientInfo.setType(type);
        clientInfo.setId(id);
        clientInfo.setRemark(remark);


        return clientInfoDao.update(clientInfo);
    }

    @Override
    public boolean delById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return clientInfoDao.del(query);
    }

    @Override
    public List<ClientInfo> findAll() {
        return clientInfoDao.find(new Query());
    }

    @Override
    public ClientInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

        List<ClientInfo> infos = clientInfoDao.find(query);

        if(infos == null || infos.size() == 0){
            return null;
        }else{
            return infos.get(0);
        }

    }

    @Override
    public List<ClientInfo> pageFind(int pageIndex, int pageSize,Query query) {


        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录


        logger.info("page find query="+query.getQueryObject().toString());

        return clientInfoDao.find(query);
    }

    @Override
    public Long count() {

        Query query = new Query();

        return clientInfoDao.count(query);
    }

    @Override
    public List<ClientInfo> likeByName(String name) {

        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(name,"i"));

        return clientInfoDao.find(query);
    }
}
