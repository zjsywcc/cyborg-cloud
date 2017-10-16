package com.ese.cloud.client.service.unicom.impl;

import com.ese.cloud.client.dao.NetworkReportDao;
import com.ese.cloud.client.dao.NetworkReportTraceDao;
import com.ese.cloud.client.entity.hunanUnicom.AreaInfo;
import com.ese.cloud.client.entity.hunanUnicom.NetworkReport;
import com.ese.cloud.client.entity.hunanUnicom.NetworkReportTrace;
import com.ese.cloud.client.entity.hunanUnicom.TechnicianInfo;
import com.ese.cloud.client.service.SMSService;
import com.ese.cloud.client.service.unicom.AreaInfoService;
import com.ese.cloud.client.service.unicom.ReportService;
import com.ese.cloud.client.service.unicom.TechnicianInfoService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 故障信息服务
 * Created by rencong on 16/12/24.
 */
@Service
public class ReportServiceImpl implements ReportService {


    Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    @Autowired
    NetworkReportDao networkReportDao;

    @Autowired
    NetworkReportTraceDao networkReportTraceDao;

    @Autowired
    AreaInfoService areaInfoService;//区域信息服务
    @Autowired
    SMSService smsService;//短信服务
    @Autowired
    TechnicianInfoService technicianInfoService;//维护人员服务

    @Override
    public boolean add(NetworkReport networkReport) {
        return networkReportDao.add(networkReport);
    }

    @Override
    public boolean addWx(NetworkReport networkReport) {

        boolean result = false;


        //0.根据区域位置设定接口人

        AreaInfo areaInfo = areaInfoService.getInfoByCode(networkReport.getAdCode());
        if(areaInfo != null){
            networkReport.setInterfaceWere(areaInfo.getTechnicians());
        }else{
            logger.error("adcode:{},接口人信息为空",networkReport.getAdCode());
        }

        //1.添加到数据库
        boolean status = networkReportDao.add(networkReport);
        if(status){
            //2.给接口人发送消息
            if(areaInfo.getTechnicians() != null) {
                //2.1修改故障接口人
                networkReport.setInterfaceWere(areaInfo.getTechnicians());
                for (String id : areaInfo.getTechnicians()) {
                    //2.2.1遍历查询接口人信息
                    TechnicianInfo technicianInfo = technicianInfoService.findOneById(id);
                    //2.2.2发送短信
                    smsService.sendMessage(technicianInfo.getMphone(), "您有新的故障信息,请登录微信小程序查看!");

                }
            }
            result = true;
        }else{
            result = false;
        }

        return result;


    }

    @Override
    public boolean update(String id, Integer status) {
        return networkReportDao.updateStatus(id,status);
    }

    @Override
    public boolean update(NetworkReport networkReport) {
        return networkReportDao.update(networkReport);
    }

    @Override
    public boolean del(String id) {
        return networkReportDao.del(id);
    }

    @Override
    public List<NetworkReport> pageFind(int pageIndex, int pageSize, Query query) {
        return networkReportDao.pageFind(pageIndex,pageSize,query);
    }

    @Override
    public Long count() {
        return networkReportDao.getCount();
    }

    @Override
    public Long count(Query query) {
        return networkReportDao.count(query);
    }

    @Override
    public List<NetworkReport> pageFindUnCheck(int pageIndex, int pageSize) {
        return networkReportDao.pageFindUnCheck(pageIndex,pageSize);
    }

    @Override
    public List<NetworkReport> findUnCheckByClientID(String clientID) {

        Query query = new Query();
        query.addCriteria(Criteria.where("clienId").is(clientID));
        query.addCriteria(Criteria.where("status").ne(2));
        query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序

        return networkReportDao.find(query);
    }

    @Override
    public List<NetworkReport> findByTime(Long start, Long end) {
        return networkReportDao.findByCreateTime(start, end);
    }

    @Override
    public List<NetworkReport> findFinishByTime(Long start, Long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(start).lte(end));
        query.addCriteria(Criteria.where("finishTime").exists(true));
        return networkReportDao.find(query);
    }

    @Override
    public List<NetworkReport> findReportByIntegerfaceId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("interfaceWere").in(id));
        query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序
        return networkReportDao.find(query);
    }

    @Override
    public List<NetworkReport> findReportByWorkerId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("worker").is(id));
        query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序
        return networkReportDao.find(query);
    }

    @Override
    public List<NetworkReport> findReprotListByWx(String wx) {

        Query query = new Query();
        query.addCriteria(Criteria.where("wxNumber").is(wx));
        query.with(new Sort(Sort.Direction.DESC, "createTime"));//降序排序

        return networkReportDao.find(query);
    }

    @Override
    public boolean reportIntefacerWorker(String workerId, String reportId) {

        NetworkReport networkReport = findById(reportId);
        if(StringUtils.isEmpty(networkReport)){
            return false;
        }else {
            networkReport.setWorker(workerId);
        }
        return networkReportDao.update(networkReport);
    }

    @Override
    public Long countUnCheck() {
        return networkReportDao.getUnCheckCount();
    }

    @Override
    public boolean addTrace(NetworkReportTrace networkReportTrace) {
        return networkReportTraceDao.add(networkReportTrace);
    }

    @Override
    public List<NetworkReportTrace> getTraceByReportId(String id) {
        return networkReportTraceDao.findByReportID(id);
    }

    @Override
    public NetworkReport findById(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

        List<NetworkReport> list = networkReportDao.find(query);

        if(list == null){
            return null;
        }else{
            return list.get(0);
        }
    }

    @Override
    public Long countAll(Long start, Long end) {

        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(start).lte(end));

        return networkReportDao.count(query);
    }

    @Override
    public Long countFinish(Long start, Long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(start).lte(end));
        query.addCriteria(Criteria.where("finishTime").exists(true));
        return networkReportDao.count(query);
    }

    @Override
    public List<NetworkReport> allUnChecked() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").ne(2));

        return networkReportDao.find(query);
    }

    @Override
    public boolean reportEvaluate(String id, String info, int star) {
        NetworkReport networkReport = findById(id);
        if(StringUtils.isEmpty(networkReport)){
            return false;
        }else {
            networkReport.setEvaluate(info);
            networkReport.setStar(star);
        }
        return networkReportDao.update(networkReport);
    }


}
