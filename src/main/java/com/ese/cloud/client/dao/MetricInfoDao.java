package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.MetricInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/1.
 */
public interface MetricInfoDao {

    /**
     * 添加指标
     * @param metricInfo
     */
    public boolean add(MetricInfo metricInfo);

    /**
     * 查询指标信息
     * @param query 查询对象
     * @return
     */
    public MetricInfo findOneByQuery(Query query);

    /**
     * 查询metric list
     * @param query 查询对象
     * @return
     */
    public List<MetricInfo> findListByQuery(Query query);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改
     * @param metricInfo
     * @return
     */
    public boolean update(MetricInfo metricInfo);

    /**
     * 求和
     * @param query
     * @return
     */
    public long count(Query query);
}
