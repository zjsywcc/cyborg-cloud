package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.MonitorTempInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
public interface MonitorTempInfoDao {

    /**
     * 添加Temp信息
     * @param monitorTempInfo
     * @return
     */
    public boolean add(MonitorTempInfo monitorTempInfo);


    /**
     * 批量添加Temp信息
     * @param monitorTempInfos
     * @return
     */
    public boolean addAll(List<MonitorTempInfo> monitorTempInfos);

    /**
     * 查询Temp信息
     * @param query
     * @return
     */
    public List<MonitorTempInfo> findByQuery(Query query);

    /**
     * 查询一个Temp信息
     * @param query
     * @return
     */
    public MonitorTempInfo findOneByQuery(Query query);

    /**
     * 删除Temp信息
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改Temp信息
     * @param monitorTempInfo
     * @return
     */
    public boolean update(MonitorTempInfo monitorTempInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);
}
