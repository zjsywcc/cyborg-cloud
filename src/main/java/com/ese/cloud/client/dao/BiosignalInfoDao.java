package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.BiosignalInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by wangchengcheng on 2018/1/27.
 */
public interface BiosignalInfoDao {

    /**
     * 添加EMG信息
     * @param biosignalInfo
     * @return
     */
    public boolean add(BiosignalInfo biosignalInfo);


    /**
     * 批量添加EMG信息
     * @param biosignalInfos
     * @return
     */
    public boolean addAll(List<BiosignalInfo> biosignalInfos);

    /**
     * 查询EMG信息
     * @param query
     * @return
     */
    public List<BiosignalInfo> findByQuery(Query query);

    /**
     * 查询一个EMG信息
     * @param query
     * @return
     */
    public BiosignalInfo findOneByQuery(Query query);

    /**
     * 删除EMG信息
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改EMG信息
     * @param biosignalInfo
     * @return
     */
    public boolean update(BiosignalInfo biosignalInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);
}
