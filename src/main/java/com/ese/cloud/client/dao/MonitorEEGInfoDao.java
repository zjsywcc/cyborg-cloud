package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.MonitorEEGInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
public interface MonitorEEGInfoDao {

    /**
     * 添加EEG信息
     * @param monitorEEGInfo
     * @return
     */
    public boolean add(MonitorEEGInfo monitorEEGInfo);


    /**
     * 批量添加EEG信息
     * @param monitorEEGInfos
     * @return
     */
    public boolean addAll(List<MonitorEEGInfo> monitorEEGInfos);

    /**
     * 查询EEG信息
     * @param query
     * @return
     */
    public List<MonitorEEGInfo> findByQuery(Query query);

    /**
     * 查询一个EEG信息
     * @param query
     * @return
     */
    public MonitorEEGInfo findOneByQuery(Query query);

    /**
     * 删除EEG信息
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改EEG信息
     * @param monitorEEGInfo
     * @return
     */
    public boolean update(MonitorEEGInfo monitorEEGInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);
}
