package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.AppInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 应用信息
 * Created by rencong on 17/2/15.
 */
public interface AppInfoDao {

    /**
     * 添加app应用
     * @param appInfo
     */
    public boolean add(AppInfo appInfo);

    /**
     * 查询app信息
     * @param query 查询对象
     * @return
     */
    public AppInfo findOneByQuery(Query query);

    /**
     * 查询applist
     * @param query 查询对象
     * @return
     */
    public List<AppInfo> findListByQuery(Query query);

    /**
     * 根据查询语句查询应用是否存在
     * @param query 查询语句
     * @return 存在返回true,否则返回false
     */
    public Boolean findByQuery(Query query);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改
     * @param appInfo
     * @return
     */
    public boolean update(AppInfo appInfo);

    /**
     * 求和
     * @param query
     * @return
     */
    public long count(Query query);


}
