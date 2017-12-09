package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.MonitorEMGInfo;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/11/9.
 */
public interface MonitorEMGInfoService {

    /**
     * 添加
     * @param monitorEMGInfo
     * @return
     */
    public boolean add(MonitorEMGInfo monitorEMGInfo);

    /**
     * 批量添加
     * @param monitorEMGInfos
     * @return
     */
    public boolean addAll(List<MonitorEMGInfo> monitorEMGInfos);

    /**
     * 查询
     * @param id
     * @return
     */
    public MonitorEMGInfo findById(String id);

    /**
     * 根据未读状态查询
     * @param isRead
     * @return
     */
    public List<MonitorEMGInfo> findByIsRead(boolean isRead);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<MonitorEMGInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部信息
     * @return
     */
    public List<MonitorEMGInfo> all();

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
     * @param monitorEMGInfo
     * @return
     */
    public boolean update(MonitorEMGInfo monitorEMGInfo);
}
