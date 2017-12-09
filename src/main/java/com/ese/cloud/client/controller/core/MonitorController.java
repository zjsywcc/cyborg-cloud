package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.entity.MonitorEMGInfo;
import com.ese.cloud.client.service.MonitorEMGInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
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

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "monitor/index";
    }

    /**
     * 获取电子人实时肌电信号
     */
    @RequestMapping(value = "/getCyborgEMG", method = RequestMethod.POST)
    @ResponseBody
    public String getCyborgEMG() {

        try {
            List<MonitorEMGInfo> emgInfos = monitorEMGInfoService.findByIsRead(false);
//            long timestamp = new Date().getTime();
//            for(int i = 0; i < 120; i++) {
//                double fakeEMG = Math.random() * 70;
//                MonitorEMGInfo emgInfo = new MonitorEMGInfo();
//                emgInfo.setTimestamp(timestamp);
//                emgInfo.setValue(fakeEMG);
//                emgInfos.add(emgInfo);
//                timestamp += 500;
//            }
            for(MonitorEMGInfo emgInfo : emgInfos) {
                emgInfo.setRead(true);
                monitorEMGInfoService.update(emgInfo);
            }
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(emgInfos));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }


}
