package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.contants.GlobleVariable;
import com.ese.cloud.client.dao.AppUserDao;
import com.ese.cloud.client.entity.app.AppUser;
import com.ese.cloud.client.service.AppUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/11/23.
 */
@Service("appUserService")
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    AppUserDao appUserDao;

    @Override
    public AppUser getAppUserByUsername(String username) {
        return appUserDao.findByUsername(username);
    }

    @Override
    public boolean add(AppUser userInfo) {
        return appUserDao.add(userInfo);
    }

    @Override
    public Boolean register(String username, String password, String email) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "uid"));
        List<AppUser> appUsers = appUserDao.listByQuery(query);
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        if(appUsers.isEmpty()) {
            appUser.setUid(0);
        } else {
            appUser.setUid(appUsers.get(0).getUid());
        }
        appUser.setPassword(new Md5Hash(password, GlobleVariable.MD5_SALT).toString());
        appUser.setEmail(email);
        return appUserDao.add(appUser);
    }



    @Override
    public Long count() {
        return appUserDao.getCount();
    }

    @Override
    public boolean update(AppUser appUser) {
        return appUserDao.update(appUser);
    }

    @Override
    public boolean updatePassword(String uid, String password) {
        return appUserDao.updatePassword(uid,new Md5Hash(password, GlobleVariable.MD5_SALT).toString());
    }

    @Override
    public boolean validateAppUser(String id, String type, String value) {
        Query query = new Query();
        if(StringUtils.isNotEmpty(id)) {
            query.addCriteria(Criteria.where("_id").ne(new ObjectId(id)));
        }
        if(StringUtils.equals("0",type)){
            query.addCriteria(Criteria.where("username").is(value));
        }
        if(StringUtils.equals("1",type)){
            query.addCriteria(Criteria.where("phone").is(value));
        }
        if(StringUtils.equals("2",type)){
            query.addCriteria(Criteria.where("email").is(value));
        }
        if(StringUtils.equals("3",type)){
            query.addCriteria(Criteria.where("wechat").is(value));
        }
        return appUserDao.findByQuery(query);
    }

    @Override
    public AppUser findById(String id) {
        return appUserDao.findById(id);
    }
}
