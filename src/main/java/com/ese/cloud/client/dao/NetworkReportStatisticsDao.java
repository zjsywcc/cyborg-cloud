package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.hunanUnicom.NetworkReportStatistics;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 网络故障统计数据库操作
 * Created by rencong on 16/12/28.
 */
public interface NetworkReportStatisticsDao {

    /**
     * 添加
     * @param statistics
     * @return
     */
    public boolean add(NetworkReportStatistics statistics);

    /**
     * 查找
     * @param query
     * @return
     */
    public List<NetworkReportStatistics> find(Query query);


}
