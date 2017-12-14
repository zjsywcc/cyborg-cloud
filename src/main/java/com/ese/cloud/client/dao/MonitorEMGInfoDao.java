package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.MonitorEMGInfo;
import com.ese.cloud.client.entity.MonitorEMGInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/11/9.
 */
public interface MonitorEMGInfoDao {

    /**
     * 添加EMG信息
     * @param monitorEMGInfo
     * @return 
     */
    public boolean add(MonitorEMGInfo monitorEMGInfo);


    /**
     * 批量添加EMG信息
     * @param monitorEMGInfos
     * @return
     */
    public boolean addAll(List<MonitorEMGInfo> monitorEMGInfos);

    /**
     * 查询EMG信息
     * @param query
     * @return
     */
    public List<MonitorEMGInfo> findByQuery(Query query);

    /**
     * 查询一个EMG信息
     * @param query
     * @return
     */
    public MonitorEMGInfo findOneByQuery(Query query);

    /**
     * 删除EMG信息
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改EMG信息
     * @param monitorEMGInfo
     * @return
     */
    public boolean update(MonitorEMGInfo monitorEMGInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);
}
