package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.entity.ReasonCount;
import com.ese.cloud.client.service.RecordInfoService;
import com.ese.cloud.client.service.ShortURLService;
import com.ese.cloud.client.util.DateTimeUtil;
import com.ese.cloud.client.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by mengchenyun on 2017/4/12.
 */
@Controller
@RequestMapping("report")
public class ReportController {

    Logger logger = Logger.getLogger(ReportController.class);

    @Autowired
    RecordInfoService recordInfoService;

    @Autowired
    ShortURLService shortURLService;

    class Report {

        private long totalCount;
        private long submitCount;
        private long permitCount;
        private long smsTotalCount;
        private long smsSentCount;

        public Report(long totalCount, long submitCount, long permitCount, long smsTotalCount, long smsSentCount) {
            this.totalCount = totalCount;
            this.submitCount = submitCount;
            this.permitCount = permitCount;
            this.smsTotalCount = smsTotalCount;
            this.smsSentCount = smsSentCount;
        }

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }

        public long getSubmitCount() {
            return submitCount;
        }

        public void setSubmitCount(long submitCount) {
            this.submitCount = submitCount;
        }

        public void setPermitCount(long permitCount) {
            this.permitCount = permitCount;
        }

        public long getPermitCount() {
            return permitCount;
        }


        public long getSmsTotalCount() {
            return smsTotalCount;
        }

        public void setSmsTotalCount(long smsTotalCount) {
            this.smsTotalCount = smsTotalCount;
        }

        public long getSmsSentCount() {
            return smsSentCount;
        }

        public void setSmsSentCount(long smsSentCount) {
            this.smsSentCount = smsSentCount;
        }
    }

    class DailyReport {
        private long smsTotalDaily;
        private long smsSentDaily;
        private long submitTotal;
        private long submitSuccess;
        private long strategyCount;
        private long passDaily;

        public DailyReport(long smsTotalDaily, long smsSentDaily, long submitTotal, long submitSuccess, long strategyCount, long passDaily) {
            this.smsTotalDaily = smsTotalDaily;
            this.smsSentDaily = smsSentDaily;
            this.submitTotal = submitTotal;
            this.submitSuccess = submitSuccess;
            this.strategyCount = strategyCount;
            this.passDaily = passDaily;
        }

        public long getSmsTotalDaily() {
            return smsTotalDaily;
        }

        public void setSmsTotalDaily(long smsTotalDaily) {
            this.smsTotalDaily = smsTotalDaily;
        }

        public long getSmsSentDaily() {
            return smsSentDaily;
        }

        public void setSmsSentDaily(long smsSentDaily) {
            this.smsSentDaily = smsSentDaily;
        }

        public long getSubmitTotal() {
            return submitTotal;
        }

        public void setSubmitTotal(long submitTotal) {
            this.submitTotal = submitTotal;
        }

        public long getSubmitSuccess() {
            return submitSuccess;
        }

        public void setSubmitSuccess(long submitSuccess) {
            this.submitSuccess = submitSuccess;
        }

        public long getStrategyCount() {
            return strategyCount;
        }

        public void setStrategyCount(long strategyCount) {
            this.strategyCount = strategyCount;
        }

        public long getPassDaily() {
            return passDaily;
        }

        public void setPassDaily(long passDaily) {
            this.passDaily = passDaily;
        }
    }

    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index() {
        return "report/index";
    }

    /**
     *  获取申请记录的统计数目
     * @return
     */
    @RequestMapping(value = "/getReport", method = RequestMethod.POST)
    @ResponseBody
    public String getReport(@RequestParam String driverType) {
        try {
            long totalCount = recordInfoService.countDistinctByDriverType(driverType);
            long submitCount = recordInfoService.countSubmitByDriverType(driverType);
            long permitCount = recordInfoService.countPermitByDriverType(driverType);
            long smsTotalCount = shortURLService.countByDriverType(driverType);
            long smsSentCount = shortURLService.countSentByDriverType(driverType) +
                    recordInfoService.countSentByDriverType(driverType);
            Report report = new Report(totalCount, submitCount, permitCount, smsTotalCount, smsSentCount);
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(report));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }

    /**
     * 获取申请记录的日统计数目
     * @return
     */
    @RequestMapping(value = "/getDailyReport", method = RequestMethod.POST)
    @ResponseBody
    public String getDailyReport(@RequestParam String date, @RequestParam String driverType) {
        try {
            Date now = DateTimeUtil.parseDay(date);
            Date nextDay = DateTimeUtil.nextDay(now, 1);
            String nextStr = DateTimeUtil.convert(nextDay);
            long prevLong = DateTimeUtil.convert2long(date);
            long nextLong = DateTimeUtil.convert2long(nextStr);
            String dateMDY = DateTimeUtil.convertMDY(now);
            long smsTotalDaily = shortURLService.countSMSDailyByDriverType(dateMDY, driverType);
            long smsCountDaily = shortURLService.countSentDailyByDriverType(dateMDY, driverType) +
                    recordInfoService.countSentDailyByDriverType(prevLong, nextLong, driverType);
            long totalDaily = recordInfoService.countDailyByDriverType(prevLong, nextLong, driverType);
            long submitDaily = recordInfoService.countSubmitDailyByDriverType(prevLong, nextLong, driverType);
            long strategyDaily = recordInfoService.countStrategyDailyByDriverType(prevLong, nextLong, driverType);
            long passDaily = recordInfoService.countPassDailyByDriverType(prevLong, nextLong, driverType);
            DailyReport report = new DailyReport(smsTotalDaily, smsCountDaily, totalDaily, submitDaily, strategyDaily, passDaily);
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(report));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }

    /**
     *  获取申请记录不通过原因日分布
     * @return
     */
    @RequestMapping(value = "/getPieChart", method = RequestMethod.POST)
    @ResponseBody
    public String getPieChart(@RequestParam String date, @RequestParam String driverType) {
        try {
            Date now = DateTimeUtil.parseDay(date);
            Date nextDay = new Date(now.getTime()+24*60*60*1000l);

            long prevLong = now.getTime();
            long nextLong = nextDay.getTime();
            List<ReasonCount> reasonCounts = new ArrayList<>();
            reasonCounts.add(new ReasonCount("无法登录老手机号", recordInfoService.countByReasonByDriverType("无法登录老手机号",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("类型不是快车", recordInfoService.countByReasonByDriverType("类型不是快车",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("手机号无对应司机", recordInfoService.countByReasonByDriverType("手机号无对应司机",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("登录手机号与新手机号不一致", recordInfoService.countByReasonByDriverType("登录手机号与新手机号不一致",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("无作弊记录,当天密码未登陆成功", recordInfoService.countByReasonByDriverType("无作弊记录,当天密码未登陆成功",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("车牌对不上", recordInfoService.countByReasonByDriverType("车牌对不上",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("旧手机号与uid不匹配", recordInfoService.countByReasonByDriverType("旧手机号与uid不匹配",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("系统司机已存在", recordInfoService.countByReasonByDriverType("系统司机已存在",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("无法登录,司机严重作弊", recordInfoService.countByReasonByDriverType("无法登录,司机严重作弊",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("名字对不上", recordInfoService.countByReasonByDriverType("名字对不上",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("身份证对不上", recordInfoService.countByReasonByDriverType("身份证对不上",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("轻度作弊司机,当天密码未登陆成功", recordInfoService.countByReasonByDriverType("轻度作弊司机,当天密码未登陆成功",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("严重作弊", recordInfoService.countByReasonByDriverType("严重作弊",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("无作弊记录,当天验证码登陆成功", recordInfoService.countByReasonByDriverType("无作弊记录,当天验证码登陆成功",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("轻度作弊司机,老手机号三证不一致", recordInfoService.countByReasonByDriverType("轻度作弊司机,老手机号三证不一致",prevLong, nextLong, driverType)));
            reasonCounts.add(new ReasonCount("修改账号失败", recordInfoService.countByReasonByDriverType("修改账号失败",prevLong, nextLong, driverType)));
//            List<ReasonCount> reasonCounts = reduceMap(recordInfoService.getReasonCount(prevLong, nextLong));
            return ReturnData.result(0, "获取原因分布数据成功", JSON.toJSONString(reasonCounts));
        } catch (Exception e) {
            logger.error("获取原因分布数据失败：", e);
            System.out.println("获取原因分布数据失败：" + e);
            return ReturnData.result(-1, "获取原因分布数据失败", null);
        }
    }

    private List<ReasonCount> reduceMap(List<ReasonCount> reasons) {
        HashMap<String, Long> reasonMap = new HashMap<>();
        for(ReasonCount count : reasons) {
            String reducedKey = reduceMapKey(count.getName());
            if(!reasonMap.containsKey(reducedKey)) {
                reasonMap.put(reducedKey, count.getY());
            } else {
                reasonMap.put(reducedKey, reasonMap.get(reducedKey) + count.getY());
            }
        }
        List<ReasonCount> res = new ArrayList<>();
        for(Map.Entry<String, Long> entry : reasonMap.entrySet()) {
            ReasonCount count = new ReasonCount(entry.getKey(), entry.getValue());
            res.add(count);
        }
        return res;
    }

    private String reduceMapKey(String key) {
        String s1 = "";
        String s2 = "";
        if(key.contains("->")) {
            s1 = key.split("->")[0];
        }
        if(key.contains(":")) {
            s2 = key.split(":")[0];
            if(s2.length() < s1.length() || s1.isEmpty()) {
                s1 = s2;
            }
        }
        if(!s1.isEmpty()) {
            return s1;
        } else {
            return key;
        }
    }
}
