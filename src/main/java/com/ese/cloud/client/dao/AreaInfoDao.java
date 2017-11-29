package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.hunanUnicom.AreaInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 区域信息
 * Created by rencong on 17/1/24.
 */
public interface AreaInfoDao {

    /**
     * 区域信息
     * @param areaInfo
     * @return
     */
    public boolean add(AreaInfo areaInfo);

    /**
     * 查询
     * @param query
     * @return
     */
    public List<AreaInfo> findByQuery(Query query);

    /**
     * 查询单个
     * @param query 查询
     * @return
     */
    public AreaInfo findOneByQuery(Query query);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改
     * @param areaInfo
     * @return
     */
    public boolean update(AreaInfo areaInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);
}
