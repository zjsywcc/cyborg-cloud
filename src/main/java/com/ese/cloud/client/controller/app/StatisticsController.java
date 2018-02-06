package com.ese.cloud.client.controller.app;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("stat")
public class StatisticsController {

    Logger logger = Logger.getLogger(MonitorController.class);

    @Autowired

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "stat/index";
    }

    /**
     * 获取统计数据
     */
    @RequestMapping(value = "/getStatistics", method = RequestMethod.POST)
    @ResponseBody
    public String getStatistics(@RequestParam String item,
                                @RequestParam String date,
                                @RequestParam String danwei) {

        final class StatInfo {

            private int hour;

            private double accuracy;

            private double timing;

            public int getHour() {
                return hour;
            }

            public void setHour(int hour) {
                this.hour = hour;
            }

            public double getAccuracy() {
                return accuracy;
            }

            public void setAccuracy(double accuracy) {
                this.accuracy = accuracy;
            }

            public double getTiming() {
                return timing;
            }

            public void setTiming(double timing) {
                this.timing = timing;
            }
        }
        try {
            List<StatInfo> statInfos = new ArrayList<>();
            for(int i = 0; i < 24; i++) {
                StatInfo statInfo = new StatInfo();
                statInfo.setHour(i);
                statInfo.setAccuracy(Math.random() * 20 + 70);
                statInfo.setTiming(Math.random() * 400 + 800);
                statInfos.add(statInfo);
            }
            System.out.println(statInfos);
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(statInfos));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }
}