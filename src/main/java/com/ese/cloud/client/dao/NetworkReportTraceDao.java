package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.hunanUnicom.NetworkReportTrace;

import java.util.List;

/**
 * 故障跟踪状态数据库操作
 * Created by rencong on 16/12/24.
 */
public interface NetworkReportTraceDao {

    /**
     * 添加故障跟中信息
     * @param networkReport
     * @return
     */
    public boolean add(NetworkReportTrace networkReport);

    /**
     * 根据故障id查询跟踪记录
     * @param id
     * @return
     */
    public List<NetworkReportTrace> findByReportID(String id);

}
