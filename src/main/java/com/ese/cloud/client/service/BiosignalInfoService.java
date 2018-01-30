package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.BiosignalInfo;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/27.
 */
public interface BiosignalInfoService {

    /**
     * 添加
     * @param biosignalInfo
     * @return
     */
    public boolean add(BiosignalInfo biosignalInfo);

    /**
     * 批量添加
     * @param biosignalInfos
     * @return
     */
    public boolean addAll(List<BiosignalInfo> biosignalInfos);

    /**
     * 查询
     * @param id
     * @return
     */
    public BiosignalInfo findById(String id);

    /**
     * 根据未读状态查询
     * @param isRead
     * @return
     */
    public List<BiosignalInfo> findByIsRead(boolean isRead);


    public List<BiosignalInfo> findByIsReadAndAhead(boolean isRead, long timestamp);


    public List<BiosignalInfo> findByIsReadAndUpdate(boolean isRead, long timestamp);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<BiosignalInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部信息
     * @return
     */
    public List<BiosignalInfo> all();

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
     * @param biosignalInfo
     * @return
     */
    public boolean update(BiosignalInfo biosignalInfo);
}
