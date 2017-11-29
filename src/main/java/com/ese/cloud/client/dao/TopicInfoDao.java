package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.TopicInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * topic管理
 * Created by rencong on 17/2/15.
 */
public interface TopicInfoDao {
    /**
     * 添加app应用
     * @param appInfo
     */
    public boolean add(TopicInfo appInfo);

    /**
     * 查询app信息
     * @param query 查询对象
     * @return
     */
    public TopicInfo findOneByQuery(Query query);

    /**
     * 查询applist
     * @param query 查询对象
     * @return
     */
    public List<TopicInfo> findListByQuery(Query query);

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
    public boolean update(TopicInfo appInfo);

    /**
     * 求和
     * @param query
     * @return
     */
    public long count(Query query);
}
