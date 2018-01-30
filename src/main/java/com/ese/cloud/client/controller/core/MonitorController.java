package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.entity.MonitorEEGInfo;
import com.ese.cloud.client.entity.MonitorEMGInfo;
import com.ese.cloud.client.entity.MonitorRRInfo;
import com.ese.cloud.client.entity.MonitorTempInfo;
import com.ese.cloud.client.service.MonitorEEGInfoService;
import com.ese.cloud.client.service.MonitorEMGInfoService;
import com.ese.cloud.client.service.MonitorRRInfoService;
import com.ese.cloud.client.service.MonitorTempInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * Created by wangchengcheng on 2017/11/6.
 */
@Controller
@RequestMapping("monitor")
public class MonitorController {

    Logger logger = Logger.getLogger(MonitorController.class);

    @Autowired
    MonitorEMGInfoService monitorEMGInfoService;

    @Autowired
    MonitorEEGInfoService monitorEEGInfoService;

    @Autowired
    MonitorRRInfoService monitorRRInfoService;

    @Autowired
    MonitorTempInfoService monitorTempInfoService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {

        System.out.println("here is monitor!");
        return "monitor/index";
    }

    /**
     * 获取电子人实时肌电信号
     */
    @RequestMapping(value = "/getCyborgEMG", method = RequestMethod.POST)
    @ResponseBody
    public String getCyborgEMG(@RequestParam long startTime) {
        try {
            List<MonitorEMGInfo> emgInfos = monitorEMGInfoService.findByIsReadAndUpdate(false, startTime);
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(emgInfos));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }

    /**
     * 获取电子人实时脑电信号
     */
    @RequestMapping(value = "/getCyborgEEG", method = RequestMethod.POST)
    @ResponseBody
    public String getCyborgEEG(@RequestParam long startTime) {
        try {
            List<MonitorEEGInfo> eegInfos = monitorEEGInfoService.findByIsReadAndUpdate(false, startTime);
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(eegInfos));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }

    /**
     * 获取电子人实时呼吸信号
     */
    @RequestMapping(value = "/getCyborgRR", method = RequestMethod.POST)
    @ResponseBody
    public String getCyborgRR(@RequestParam long startTime) {
        try {
            List<MonitorRRInfo> rrInfos = monitorRRInfoService.findByIsReadAndUpdate(false, startTime);
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(rrInfos));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }

    /**
     * 获取电子人实时体温信号
     */
    @RequestMapping(value = "/getCyborgTemp", method = RequestMethod.POST)
    @ResponseBody
    public String getCyborgTemp(@RequestParam long startTime) {
        try {
            List<MonitorTempInfo> tempInfos = monitorTempInfoService.findByIsReadAndUpdate(false, startTime);
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(tempInfos));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }

    @RequestMapping(value = "/getCyborgStatus", method = RequestMethod.POST)
    @ResponseBody
    public String getCyborgStatus(@RequestParam String id,
                                  @RequestParam String name) {
        //
        // System.out.println("ID=" + id);
        //System.out.println("name=" + name);
        // return level;
        try {
            String fakeTiredLevel = "7";
            return ReturnData.result(0, "", JSON.toJSONString(fakeTiredLevel));
        } catch (Exception e) {
            String m="failed";
            logger.error("获取疲劳度失败：", e);
            return ReturnData.result(-1, "获取疲劳度失败", m);

        }
    }


}