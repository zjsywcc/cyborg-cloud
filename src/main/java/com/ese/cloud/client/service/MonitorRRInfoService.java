package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.MonitorRRInfo;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
public interface MonitorRRInfoService {

    /**
     * 添加
     * @param monitorRRInfo
     * @return
     */
    public boolean add(MonitorRRInfo monitorRRInfo);

    /**
     * 批量添加
     * @param monitorRRInfos
     * @return
     */
    public boolean addAll(List<MonitorRRInfo> monitorRRInfos);

    /**
     * 查询
     * @param id
     * @return
     */
    public MonitorRRInfo findById(String id);

    /**
     * 根据未读状态查询
     * @param isRead
     * @return
     */
    public List<MonitorRRInfo> findByIsRead(boolean isRead);


    public List<MonitorRRInfo> findByIsReadAndAhead(boolean isRead, long timestamp);


    public List<MonitorRRInfo> findByIsReadAndUpdate(boolean isRead, long timestamp);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<MonitorRRInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部信息
     * @return
     */
    public List<MonitorRRInfo> all();

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
     * @param monitorRRInfo
     * @return
     */
    public boolean update(MonitorRRInfo monitorRRInfo);
}
