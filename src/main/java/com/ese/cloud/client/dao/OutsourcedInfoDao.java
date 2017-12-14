package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.hunanUnicom.OutsourcedInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 外包公司信息数据库操作
 * Created by rencong on 17/1/23.
 */
public interface OutsourcedInfoDao {

    /**
     * 添加
     * @param outsourcedInfo
     * @return
     */
    public boolean add(OutsourcedInfo outsourcedInfo);

    /**
     * 查询
     * @param query
     * @return
     */
    public List<OutsourcedInfo> findByQuery(Query query);

    /**
     * 查询外包公司
     * @param query
     * @return
     */
    public OutsourcedInfo findOneByQuery(Query query);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改
     * @param outsourcedInfo
     * @return
     */
    public boolean update(OutsourcedInfo outsourcedInfo);

    /**
     * 计算总数
     * @param query
     * @return
     */
    public Long count(Query query);


}
