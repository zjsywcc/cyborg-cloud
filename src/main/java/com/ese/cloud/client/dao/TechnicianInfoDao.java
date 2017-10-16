package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.hunanUnicom.TechnicianInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 维修人员信息数据库操作
 * Created by rencong on 17/1/23.
 */
public interface TechnicianInfoDao {

    /**
     * 添加维修人员信息
     * @param technicianInfo
     * @return
     */
    public boolean add(TechnicianInfo technicianInfo);

    /**
     * 查询
     * @param query
     * @return
     */
    public List<TechnicianInfo> findByQuery(Query query);

    /**
     * 根据id查询
     * @param query
     * @return
     */
    public TechnicianInfo findOneByQuery(Query query);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改
     * @param technicianInfo
     * @return
     */
    public boolean update(TechnicianInfo technicianInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);

}
