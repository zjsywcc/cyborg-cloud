package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.hunanUnicom.NetworkReport;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 故障报告
 * Created by rencong on 16/12/24.
 */
public interface NetworkReportDao {
    /**
     * 添加
     * @param report
     * @return
     */
    public boolean add(NetworkReport report);

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean del(String id);

    /**
     * 修改故障状态
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(String id,Integer status);


    public List<NetworkReport> pageFind(int pageIndex, int pageSize, Query query);

    public Long getCount();


    /**
     * 分页查询未处理的故障
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<NetworkReport> pageFindUnCheck(int pageIndex, int pageSize);

    /**
     * 查询
     * @param query
     * @return
     */
    public List<NetworkReport> find(Query query);

    /**
     * 统计计数
     * @param query
     * @return
     */
    public Long count(Query query);


    /**
     * 获得未处理的故障的总数
     * @return
     */
    public Long getUnCheckCount();

    /**
     * 查询指定时间范围内的故障信息
     * @param start
     * @param end
     * @return
     */
    public List<NetworkReport> findByCreateTime(Long start, Long end);

    /**
     * 修改故障信息
     * @param networkReport
     * @return
     */
    public boolean update(NetworkReport networkReport);
}
