package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.hunanUnicom.ClientNetworkInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by user on 16/12/27.
 */
public interface ClientNetworkInfoDao {

    /**
     * 查找
     * @param query
     * @return
     */
    public List<ClientNetworkInfo> find(Query query);

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
    public boolean update(ClientNetworkInfo clientInfo);

    /**
     * 添加
     * @param clientInfo
     * @return
     */
    public boolean add(ClientNetworkInfo clientInfo);

}
