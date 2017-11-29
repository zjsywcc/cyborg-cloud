package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.RoleInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by rencong on 16/12/30.
 */
public interface RoleInfoService {
    /**
     * 添加角色
     * @param roleInfo
     * @return
     */
    public boolean add(RoleInfo roleInfo);

    /**
     * 查询角色
     * @param id
     * @return
     */
    public RoleInfo findById(String id);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<RoleInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部角色信息
     * @return
     */
    public List<RoleInfo> all();

    /**
     * 查询总数
     * @param query
     * @return
     */
    public Long count(Query query);

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(String id);

    /**
     * 修改
     * @param roleInfo
     * @return
     */
    public boolean update(RoleInfo roleInfo);



}
