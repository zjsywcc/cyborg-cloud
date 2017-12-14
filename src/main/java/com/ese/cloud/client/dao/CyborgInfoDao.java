package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.CyborgInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * api信息接口
 * Created by wcc
 */
public interface CyborgInfoDao {

    /**
     * 添加电子人信息
     * @param cyborgInfo
     * @return
     */
    public boolean add(CyborgInfo cyborgInfo);

    /**
     * 查询电子人信息
     * @param query
     * @return
     */
    public List<CyborgInfo> findByQuery(Query query);

    /**
     * 查询一个电子人信息
     * @param query
     * @return
     */
    public CyborgInfo findOneByQuery(Query query);

    /**
     * 删除电子人信息
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改电子人信息
     * @param cyborgInfo
     * @return
     */
    public boolean update(CyborgInfo cyborgInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);
}
