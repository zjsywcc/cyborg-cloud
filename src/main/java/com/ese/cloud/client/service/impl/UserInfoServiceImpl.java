package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.contants.GlobleVariable;
import com.ese.cloud.client.dao.UserInfoDao;
import com.ese.cloud.client.entity.UserInfo;
import com.ese.cloud.client.service.UserInfoService;
import com.ese.cloud.client.util.EmailServer;
import com.ese.cloud.client.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户信息服务实现类
 * Created by rencong on 16/9/10.
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;


    @Override
    public UserInfo getUserInfoByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }

    @Override
    public boolean add(UserInfo userInfo) {


        String pwd = PasswordUtil.genRandomNum(8);

        String password = new Md5Hash(pwd, GlobleVariable.MD5_SALT).toString();

        userInfo.setPassword(password);


        if(userInfoDao.add(userInfo)){

            return true;
        }else {
            return false;
        }

    }



    @Override
    public boolean update(String id,String username,String email,String mob,String remark,String role,String wechat) {
        return userInfoDao.update(id,username,email,mob,remark,role,wechat);
    }

    @Override
    public boolean updatePassword(String uid, String password) {
        return userInfoDao.updatePassword(uid,new Md5Hash(password, GlobleVariable.MD5_SALT).toString());
    }

    @Override
    public boolean validateUserInfo(String id, String type, String value) {
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


        return userInfoDao.findByQuery(query);
    }

    @Override
    public UserInfo findById(String id) {
        return userInfoDao.findById(id);
    }

    @Override
    public Boolean register(String username, String password, String email) {

        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(0);//未认证
        userInfo.setUsername(username);
        userInfo.setPassword(new Md5Hash(password, GlobleVariable.MD5_SALT).toString());
        userInfo.setCreateTime(new Date());
        userInfo.setEmail(email);
        userInfo.setRoleId(null);

        return userInfoDao.add(userInfo);
       // return null;
    }

    @Override
    public List<UserInfo> pageFind(int pageIndex, int pageSize) {
        return userInfoDao.pageFind(pageIndex,pageSize);
    }

    @Override
    public Long count() {
        return userInfoDao.getCount();
    }

    @Override
    public boolean delete(String id) {
        return userInfoDao.delete(id);
    }
}
