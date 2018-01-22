package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.service.MonitorEMGInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("statistics")
public class StatisticsController {

    Logger logger = Logger.getLogger(MonitorController.class);

    @Autowired

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "statistics/index";
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
            Date timestamp;
            double value;

            public Date getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(Date timestamp) {
                this.timestamp = timestamp;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }
        }
        try {
            List<StatInfo> statInfos = new ArrayList<>();
            long timestamp = new Date().getTime();
            for (int i = 0; i < 10; i++) {
                double fakeSTAT = Math.random() * 100;
                StatInfo statInfo = new StatInfo();
                SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
                Date d = new Date();
                form.format(d);
                System.out.println("!!!!!!!!!!!!!!!!!!!!");
                System.out.println(d);
                statInfo.setTimestamp(d);
                statInfo.setValue(fakeSTAT);
                statInfos.add(statInfo);
                timestamp += 500;
            }
            System.out.println(statInfos);
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(statInfos));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }
}