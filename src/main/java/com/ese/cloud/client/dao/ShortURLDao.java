package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.ShortURL;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 短地址和手机号对相应
 * Created by mengchenyun on 2017/3/30.
 */
public interface ShortURLDao {

    /**
     * 添加记录
     * @param shortURL
     */
    public boolean add(ShortURL shortURL);

    /**
     * 查询申请记录信息
     * @param query 查询对象
     * @return
     */
    public ShortURL findOneByQuery(Query query);

    /**
     * 查询申请记录list
     * @param query 查询对象
     * @return
     */
    public List<ShortURL> findListByQuery(Query query);

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
     * @param shortURL
     * @return
     */
    public boolean update(ShortURL shortURL);

    /**
     * 求和
     * @param query
     * @return
     */
    public long count(Query query);

    /**
     * 查询大于num的手机号
     * @param num 阈值
     * @return 结果
     */
    public List getDataNum(int num);
}
