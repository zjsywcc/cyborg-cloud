package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.MonitorTempInfo;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
public interface MonitorTempInfoService {

    /**
     * 添加
     * @param monitorTempInfo
     * @return
     */
    public boolean add(MonitorTempInfo monitorTempInfo);

    /**
     * 批量添加
     * @param monitorTempInfos
     * @return
     */
    public boolean addAll(List<MonitorTempInfo> monitorTempInfos);

    /**
     * 查询
     * @param id
     * @return
     */
    public MonitorTempInfo findById(String id);

    /**
     * 根据未读状态查询
     * @param isRead
     * @return
     */
    public List<MonitorTempInfo> findByIsRead(boolean isRead);


    public List<MonitorTempInfo> findByIsReadAndAhead(boolean isRead, long timestamp);


    public List<MonitorTempInfo> findByIsReadAndUpdate(boolean isRead, long timestamp);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<MonitorTempInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部信息
     * @return
     */
    public List<MonitorTempInfo> all();

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
     * @param monitorTempInfo
     * @return
     */
    public boolean update(MonitorTempInfo monitorTempInfo);
}
