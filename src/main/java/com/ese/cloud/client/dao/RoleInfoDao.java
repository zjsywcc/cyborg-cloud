package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.RoleInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 角色信息操作
 * Created by rencong on 16/9/10.
 */
public interface RoleInfoDao {

    /**
     * 添加角色信息到数据库
     * @param roleInfo 角色对象
     * @return
     */
    public boolean add(RoleInfo roleInfo);

    /**
     * 更新
     * @param roleInfo
     * @return
     */
    public boolean update(RoleInfo roleInfo);

    /***
     * 根据id查询角色信息
     * @param id 角色id
     * @return
     */
    public RoleInfo findById(String id);

    /**
     * 查询
     * @param query
     * @return
     */
    public List<RoleInfo> find(Query query);

    /**
     * 查询总数
     * @param query
     * @return
     */
    public Long count(Query query);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean delete(Query query);


}
