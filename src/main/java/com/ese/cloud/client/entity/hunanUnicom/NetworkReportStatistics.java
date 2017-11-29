package com.ese.cloud.client.entity.hunanUnicom;

import java.util.Map;

/**
 * 故障统计信息
 * Created by rencong on 16/12/28.
 */
public class NetworkReportStatistics {

    private static final long serialVersionUID = 1L;
    private String id;

    /**
     * 统计日期
     */
    private Long day;

    /**
     * 当日上报故障量
     */
    private Integer dayReportSum;

    /**
     * 当日修复完成故障量
     */
    private Integer dayFinishSum;

    /**
     * 当日未完成故障量
     */
    private Integer dayUnFinishSum;

    /**
     * 当日故障修复平均耗时
     */
    private Double  dayTimeAvg;

    /**
     * 不同类别故障数量详情
     */
    private Map<String,Integer> dayReportTypeDistribution;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDayReportSum() {
        return dayReportSum;
    }

    public void setDayReportSum(Integer dayReportSum) {
        this.dayReportSum = dayReportSum;
    }

    public Integer getDayFinishSum() {
        return dayFinishSum;
    }

    public void setDayFinishSum(Integer dayFinishSum) {
        this.dayFinishSum = dayFinishSum;
    }

    public Integer getDayUnFinishSum() {
        return dayUnFinishSum;
    }

    public void setDayUnFinishSum(Integer dayUnFinishSum) {
        this.dayUnFinishSum = dayUnFinishSum;
    }

    public Double getDayTimeAvg() {
        return dayTimeAvg;
    }

    public void setDayTimeAvg(Double dayTimeAvg) {
        this.dayTimeAvg = dayTimeAvg;
    }

    public Map<String, Integer> getDayReportTypeDistribution() {
        return dayReportTypeDistribution;
    }

    public void setDayReportTypeDistribution(Map<String, Integer> dayReportTypeDistribution) {
        this.dayReportTypeDistribution = dayReportTypeDistribution;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }
}
