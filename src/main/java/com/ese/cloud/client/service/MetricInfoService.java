package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.MetricInfo;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/1.
 */
public interface MetricInfoService {

    /**
     * 添加
     * @param metricInfo
     * @return
     */
    public boolean add(MetricInfo metricInfo);

    /**
     * 查询
     * @param id
     * @return
     */
    public MetricInfo findById(String id);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<MetricInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部信息
     * @return
     */
    public List<MetricInfo> all();

    /**
     * 查询总数
     * @return
     */
    public Long count();

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(String id);

    /**
     * 修改
     * @param metricInfo
     * @return
     */
    public boolean update(MetricInfo metricInfo);
}
