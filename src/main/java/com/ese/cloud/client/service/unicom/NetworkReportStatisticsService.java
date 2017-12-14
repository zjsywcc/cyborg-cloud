package com.ese.cloud.client.service.unicom;

import com.ese.cloud.client.entity.hunanUnicom.NetworkReport;
import com.ese.cloud.client.entity.hunanUnicom.NetworkReportStatistics;

import java.util.List;
import java.util.Map;

/**
 * 故障统计统计服务
 * Created by rencong on 16/12/28.
 */
public interface NetworkReportStatisticsService {

    /**
     * 生成每日的数据统计并插入数据库
     * @return
     */
    public boolean statisticByTimeRange(Long start,Long end);

    /**
     * 根据时间范围查询网络故障信息统计信息
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    public List<NetworkReportStatistics> findStatisticByTimeRange(Long start, Long end);

    /**
     * 计算指定时间范围内的完成平均时间
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    public Double avgFinishTimeByTimeRange(Long start,Long end);

    /**
     * 计算指定时间范围内的完成平均时间
     * @return
     */
    public Double avgFinishTimeByTimeRange(List<NetworkReport> list);

    /**
     * 统计指定时间段内的故障报告类型的数量
     * @param start 开始时间戳
     * @param end 结束时间戳
     * @return key-类型,value-值
     */
    public Map<String,Integer> getTypeMapByTimeRange(Long start,Long end);

    /**
     * 转换为故障类型map
     * @param listAll
     * @return
     */
    public Map<String, Integer> getTypeMapByTimeRange(List<NetworkReport> listAll);




}
