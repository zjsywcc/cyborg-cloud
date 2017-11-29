package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.UserInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 代驾订单信息操作
 * Created by rencong on 16/8/24.
 */
public interface UserInfoDao {

    /**
     * 添加用户信息
     * @param userInfo
     * @return
     */
     public Boolean add(UserInfo userInfo);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
     public UserInfo findByUsername(String username);

    /**
     * 根据查询语句查询用户是否存在
     * @param query 查询语句
     * @return 存在返回true,否则返回false
     */
     public Boolean findByQuery(Query query);

    /**
     * 查询全部用户
     * @return
     */
    public List<UserInfo> getAll();

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<UserInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部用户数量
     * @return
     */
    public Long getCount();

    public boolean delete(String id);

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    public boolean update(String id,String username,String email,String mob,String remark,String role,String wechat);


    public boolean updatePassword(String id,String password);

    public UserInfo findById(String id);

}
