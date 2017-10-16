package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.ReasonCount;
import com.ese.cloud.client.entity.RecordInfo;
import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/28.
 */
public interface RecordInfoDao {

    /**
     * 添加记录
     * @param recordInfo
     */
    public boolean add(RecordInfo recordInfo);

    /**
     * 查询申请记录信息
     * @param query 查询对象
     * @return
     */
    public RecordInfo findOneByQuery(Query query);

    /**
     * 查询申请记录list
     * @param query 查询对象
     * @return
     */
    public List<RecordInfo> findListByQuery(Query query);

    /**
     * 根据查询语句查询应用是否存在
     * @param query 查询语句
     * @return 存在返回true,否则返回false
     */
    public Boolean findByQuery(Query query);

    /**
     * 删除
     * @param query
     * @return
     */
    public boolean delete(Query query);

    /**
     * 修改
     * @param recordInfo
     * @return
     */
    public boolean update(RecordInfo recordInfo);

    /**
     * 求和
     * @param query
     * @return
     */
    public long count(Query query);

    /**
     * 计算对登陆手机号去重后的记录总数
     * @param query
     * @return
     */
    public long countDistinctByLoginMob(Query query);

    public boolean update(String id, Update update);

    public List<ReasonCount> getReasonCount(long start, long end);


    public List<BasicDBObject> getDataNum(int num);

    /**
     * 查询修改手机号是否成功
     * @param mob 手机号
     * @return true存在,false不存在
     */
    public boolean checkUpdateMobSuccess(String mob);

















}
