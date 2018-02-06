package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.MonitorEEGInfo;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
public interface MonitorEEGInfoService {

    /**
     * 添加
     * @param monitorEEGInfo
     * @return
     */
    public boolean add(MonitorEEGInfo monitorEEGInfo);

    /**
     * 批量添加
     * @param monitorEEGInfos
     * @return
     */
    public boolean addAll(List<MonitorEEGInfo> monitorEEGInfos);

    /**
     * 查询
     * @param id
     * @return
     */
    public MonitorEEGInfo findById(String id);

    /**
     * 根据未读状态查询
     * @param isRead
     * @return
     */
    public List<MonitorEEGInfo> findByIsRead(boolean isRead);


    public List<MonitorEEGInfo> findByIsReadAndAhead(boolean isRead, long timestamp);


    public List<MonitorEEGInfo> findByIsReadAndUpdate(boolean isRead, long timestamp);


    public MonitorEEGInfo findNewestEEGInfo();

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<MonitorEEGInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部信息
     * @return
     */
    public List<MonitorEEGInfo> all();

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
     * @param monitorEEGInfo
     * @return
     */
    public boolean update(MonitorEEGInfo monitorEEGInfo);
}
