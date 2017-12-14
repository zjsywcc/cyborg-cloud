package com.ese.cloud.client.service.unicom;

/**
 * 导出告警数据
 * Created by rencong on 16/12/27.
 */
public interface ExcelService {

    /**
     * 导出时间段的故障数据
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    public void exportReport(Long start,Long end);

}
