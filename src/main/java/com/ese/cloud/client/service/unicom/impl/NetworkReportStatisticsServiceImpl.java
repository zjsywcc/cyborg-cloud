package com.ese.cloud.client.service.unicom.impl;

import com.ese.cloud.client.dao.NetworkReportDao;
import com.ese.cloud.client.entity.hunanUnicom.NetworkReport;
import com.ese.cloud.client.service.unicom.NetworkReportStatisticsService;
import com.ese.cloud.client.dao.NetworkReportStatisticsDao;
import com.ese.cloud.client.entity.hunanUnicom.NetworkReportStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by rencong on 16/12/28.
 */
@Service
public class NetworkReportStatisticsServiceImpl implements NetworkReportStatisticsService {

    private Logger logger = LoggerFactory.getLogger(NetworkReportStatisticsServiceImpl.class);

    @Autowired
    private NetworkReportStatisticsDao networkReportStatisticsDao;
    @Autowired
    private NetworkReportDao networkReportDao;

    @Override
    public boolean statisticByTimeRange(Long start,Long end) {

        NetworkReportStatistics obj = new NetworkReportStatistics();

        Integer sum = 0;//故障总数
        Integer sumFinish = 0;//完成故障总数

        //故障分布数据
        Map<String,Integer> typeMap = new HashMap<>();

        //1.构造查询语句
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("createTime").gte(start).lte(end));

        Query query2 = new Query();
        query2.addCriteria(Criteria.where("createTime").gte(start).lte(end).and("finishTime").exists(true));
        //query2.addCriteria(Criteria.where("finishTime").exists(true));

        //2.执行查询
        //2.1指定时间段内全部数据
        List<NetworkReport> listAll =  networkReportDao.find(query1);
        //2.2指定时间段内已完成的数据
        List<NetworkReport> listFinish =  networkReportDao.find(query2);

        //2.3判断是否为空
        if ((listAll == null) || (listAll.size() == 0)) {
            logger.info("按照时间段统计({}-{}),查询数据为NULL",start,end);
        }
        if ((listFinish == null) || (listFinish.size() == 0)) {
            logger.info("按照时间段统计({}-{}),查询完成的数据为NULL",start,end);
        }

        sum = listAll.size();
        sumFinish = listFinish.size();



        obj.setDay(start);
        obj.setDayFinishSum(sumFinish);
        obj.setDayReportSum(sum);
        obj.setDayReportTypeDistribution(getTypeMapByTimeRange(listAll));
        obj.setDayTimeAvg(avgFinishTimeByTimeRange(listFinish));
        obj.setDayUnFinishSum(sum-sumFinish);

        return networkReportStatisticsDao.add(obj);
    }

    @Override
    public List<NetworkReportStatistics> findStatisticByTimeRange(Long start, Long end) {

        Query query = new Query();
        query.addCriteria(Criteria.where("day").gte(start).lte(end));

        return networkReportStatisticsDao.find(query);
    }

    @Override
    public Double avgFinishTimeByTimeRange(Long start, Long end) {

        Double time = 0.0;

        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(start).lte(end));
        query.addCriteria(Criteria.where("finishTime").exists(true));

        //2.指定时间段内全部数据
        List<NetworkReport> list =  networkReportDao.find(query);

        if (list == null || list.size() == 0) {
            logger.info("查询完成修复的数据为空");
            return null;
        }

        for(NetworkReport obj : list){
            time = time + obj.getFinishTime().doubleValue() - obj.getCreateTime().doubleValue();
        }


        return time/list.size();
    }

    @Override
    public Double avgFinishTimeByTimeRange(List<NetworkReport> list) {

        Double time = 0.0;



        if (list == null || list.size() == 0) {
            logger.info("查询完成修复的数据为空");
            return null;
        }

        for(NetworkReport obj : list){

            Long difftime = obj.getFinishTime() - obj.getCreateTime();

            logger.info("时间差:"+difftime.doubleValue());

            time = time + difftime.doubleValue();
        }


        return time/list.size();
    }


    @Override
    public Map<String, Integer> getTypeMapByTimeRange(Long start, Long end) {

        Map<String,Integer> result = new HashMap<>();

        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(start).lte(end));
        //query2.addCriteria(Criteria.where("finishTime").exists(true));

        //2.指定时间段内全部数据
        List<NetworkReport> listAll =  networkReportDao.find(query);


        for(NetworkReport obj : listAll){
            if(result.containsKey(obj.getFaultShow())){
                 result.put(obj.getFaultShow(),result.get(obj.getFaultShow())+1);
            }else{
                result.put(obj.getFaultShow(),1);
            }
        }

        return result;

    }



    @Override
    public Map<String, Integer> getTypeMapByTimeRange(List<NetworkReport> listAll) {

        Map<String,Integer> result = new HashMap<>();


        for(NetworkReport obj : listAll){
            if(result.containsKey(obj.getFaultShow())){
                result.put(obj.getFaultShow(),result.get(obj.getFaultShow())+1);
            }else{
                result.put(obj.getFaultShow(),1);
            }
        }

        return result;

    }


}
