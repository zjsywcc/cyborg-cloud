package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.entity.MetricsInfo;
import com.ese.cloud.client.util.ReturnData;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

/**
 * Created by wangchengcheng on 2017/11/6.
 */
@Controller
@RequestMapping("monitor")
public class MonitorController {

    Logger logger = Logger.getLogger(MonitorController.class);


    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        return "monitor/index";
    }

    /**
     * 获取电子人实时肌电信号
     */
    @RequestMapping(value = "/getCyborgEMG", method = RequestMethod.POST)
    @ResponseBody
    public String getCyborgEMG() {
        try {
            double fakeEMG = Math.random() * 70;
            MetricsInfo emgInfo = new MetricsInfo();
            emgInfo.setValue(Double.toString(fakeEMG));
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(emgInfo));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }


}
