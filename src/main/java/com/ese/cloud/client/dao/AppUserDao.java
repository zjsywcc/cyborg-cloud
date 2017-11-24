package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.app.AppUser;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


/**
 * Created by wangchengcheng on 2017/11/23.
 */
public interface AppUserDao {

    /**
     * 添加用户信息
     * @param userInfo
     * @return
     */
    public Boolean add(AppUser userInfo);


    public AppUser findById(String id);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public AppUser findByUsername(String username);


    /**
     * 根据查询语句查询用户是否存在
     * @param query 查询语句
     * @return 存在返回true,否则返回false
     */
    public Boolean findByQuery(Query query);


    /**
     * 根据查询语句查询用户是否存在
     * @param query 查询语句
     * @return 存在返回true,否则返回false
     */
    public List<AppUser> listByQuery(Query query);


    /**
     * 查询全部用户数量
     * @return
     */
    public Long getCount();


    public boolean update(AppUser appUser);


    public boolean updatePassword(String id,String password);


}
