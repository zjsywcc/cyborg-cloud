package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.APIInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * api信息接口
 * Created by rencong on 17/1/24.
 */
public interface APIInfoDao {

    /**
     * 添加维修人员信息
     * @param apiInfo
     * @return
     */
    public boolean add(APIInfo apiInfo);

    /**
     * 查询
     * @param query
     * @return
     */
    public List<APIInfo> findByQuery(Query query);

    /**
     * 查询一个api信息
     * @param query
     * @return
     */
    public APIInfo findOneByQuery(Query query);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改
     * @param apiInfo
     * @return
     */
    public boolean update(APIInfo apiInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);
}
