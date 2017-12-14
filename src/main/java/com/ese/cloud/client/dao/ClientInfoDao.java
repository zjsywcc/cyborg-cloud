package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.ClientInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 客户信息数据操作
 * Created by rencong on 16/12/27.
 */
public interface ClientInfoDao {

    /**
     * 查找
     * @param query
     * @return
     */
    public List<ClientInfo> find(Query query);

    /**
     * 计数
     * @param query
     * @return
     */
    public Long count(Query query);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean del(Query query);

    /**
     * 更新
     * @param clientInfo
     * @return
     */
    public boolean update(ClientInfo clientInfo);

    /**
     * 添加
     * @param clientInfo
     * @return
     */
    public boolean add(ClientInfo clientInfo);





}
