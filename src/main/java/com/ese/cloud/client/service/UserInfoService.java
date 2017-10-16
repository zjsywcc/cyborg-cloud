package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.UserInfo;

import java.util.List;

/**
 * 用户信息服务
 * Created by rencong on 16/9/10.
 */
public interface UserInfoService {

    /***
     * 获取用户信息对象
     * @param username 用户名
     * @return 用户信息
     */
    public UserInfo getUserInfoByUsername(String username);

    /**
     * 添加用户信息
     * @param userInfo
     * @return
     */
    public boolean add(UserInfo userInfo);



    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param email 电子邮件
     * @return 注册成功true,失败false
     */
    public Boolean register(String username,String password,String email);


    public List<UserInfo> pageFind(int pageIndex, int pageSize);

    public Long count();

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public boolean delete(String id);

    public boolean update(String id,String username,String email,String mob,String remark,String role,String wechat);

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
    public boolean validateUserInfo(String id,String type,String value);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public UserInfo findById(String id);

}
