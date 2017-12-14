package com.ese.cloud.client.service;


import com.ese.cloud.client.entity.app.AppUser;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/11/23.
 */
public interface AppUserService {

    /***
     * 获取用户信息对象
     * @param username 用户名
     * @return 用户信息
     */
    public AppUser getAppUserByUsername(String username);

    /**
     * 添加用户信息
     * @param userInfo
     * @return
     */
    public boolean add(AppUser userInfo);



    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param email 电子邮件
     * @return 注册成功true,失败false
     */
    public Boolean register(String username,String password,String email);



    public Long count();


    public boolean update(AppUser appUser);

    /**
     * 修改密码
     * @param uid 用户id
     * @param password 密码
     * @return
     */
    public boolean updatePassword(String uid,String password);

    /**
     * 验证用户信息
     * @param id 用户id,非必须
     * @param type 类型:0-用户名,1-手机号,2-邮箱,3-微信
     * @param value 值
     * @return
     */
    public boolean validateAppUser(String id,String type,String value);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public AppUser findById(String id);
}
