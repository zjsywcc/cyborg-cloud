package com.ese.cloud.client.service.unicom;

import com.ese.cloud.client.entity.hunanUnicom.NetworkReport;
import com.ese.cloud.client.entity.hunanUnicom.NetworkReportTrace;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 故障服务
 * Created by rencong on 16/12/24.
 */
public interface ReportService {

    /**
     * 添加故障
     * @param networkReport
     * @return
     */
    public boolean add(NetworkReport networkReport);

    /**
     * 从微信中添加故障
     * @param networkReport
     * @return
     */
    public boolean addWx(NetworkReport networkReport);



    /**
     * 修改故障状态
     * @param id
     * @param status
     * @return
     */
    public boolean update(String id,Integer status);

    /**
     * 修改故障信息
     * @param networkReport
     * @return
     */
    public boolean update(NetworkReport networkReport);


    /**
     * 删除故障
     * @param id
     * @return
     */
    public boolean del(String id);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<NetworkReport> pageFind(int pageIndex, int pageSize,Query query);

    /**
     * 总数
     * @return
     */
    public Long count();

    /**
     * 条件查询总数
     * @return
     */
    public Long count(Query query);


    /**
     * 未处理分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<NetworkReport> pageFindUnCheck(int pageIndex, int pageSize);

    /**
     * 根据客户id查看未处理故障信息
     * @param clientID
     * @return
     */
    public List<NetworkReport> findUnCheckByClientID(String clientID);

    /**
     * 根据时间段查询故障信息
     * @param start 开始时间
     * @param end   结束时间
     * @return 故障信息列表
     */
    public List<NetworkReport> findByTime(Long start, Long end);

    /**
     * 查询指定时间段完成的故障信息
     * @param start
     * @param end
     * @return
     */
    public List<NetworkReport> findFinishByTime(Long start, Long end);


    /**
     * 根据接口人id查询故障列表
     * @param id 接口人id
     * @return 故障列表
     */
    public List<NetworkReport> findReportByIntegerfaceId(String id);

    /**
     * 根据维修人员id查询故障列表
     * @param id 维修人员id
     * @return 故障列表
     */
    public List<NetworkReport> findReportByWorkerId(String id);

    /**
     * 根据提交人微信号查询故障信息列表
     * @param wx 微信号
     * @return
     */
    public List<NetworkReport> findReprotListByWx(String wx);

    /**
     * 故障指派给维修人员
     * @param workerId 故障id
     * @param reportId 维修人员id
     * @return 指派成功true,否则false
     */
    public boolean reportIntefacerWorker(String workerId,String reportId);

    /**
     * 未处理总数
     * @return
     */
    public Long countUnCheck();

    /**
     * 添加故障的跟踪信息
     * @param networkReportTrace
     * @return
     */
    public boolean addTrace(NetworkReportTrace networkReportTrace);

    /**
     * 根据故障id查询出跟踪信息
     * @param id 故障id
     * @return
     */
    public List<NetworkReportTrace> getTraceByReportId(String id);

    /**
     * 根据id查询故障信息
     * @param id
     * @return
     */
    public NetworkReport findById(String id);

    /**
     * 指定时间范围内总故障数
     * @param start
     * @param end
     * @return
     */
    public Long countAll(Long start,Long end);

    /**
     * 指定时间范围内为完成的总故障数量
     * @param start
     * @param end
     * @return
     */
    public Long countFinish(Long start,Long end);

    /**
     * 查询全部故障信息
     * @return
     */
    public List<NetworkReport> allUnChecked();


    /**
     * 对指定故障信息添加星级和评论
     * @param id
     * @param info
     * @param star
     * @return
     */
    public boolean reportEvaluate(String id,String info,int star);



}
