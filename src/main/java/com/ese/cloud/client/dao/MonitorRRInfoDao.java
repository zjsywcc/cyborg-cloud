package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.MonitorRRInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
public interface MonitorRRInfoDao {

    /**
     * 添加RR信息
     * @param monitorRRInfo
     * @return
     */
    public boolean add(MonitorRRInfo monitorRRInfo);


    /**
     * 批量添加RR信息
     * @param monitorRRInfos
     * @return
     */
    public boolean addAll(List<MonitorRRInfo> monitorRRInfos);

    /**
     * 查询RR信息
     * @param query
     * @return
     */
    public List<MonitorRRInfo> findByQuery(Query query);

    /**
     * 查询一个RR信息
     * @param query
     * @return
     */
    public MonitorRRInfo findOneByQuery(Query query);

    /**
     * 删除RR信息
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改RR信息
     * @param monitorRRInfo
     * @return
     */
    public boolean update(MonitorRRInfo monitorRRInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);
}
