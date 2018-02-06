package com.ese.cloud.client.controller.app;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.entity.MonitorEEGInfo;
import com.ese.cloud.client.service.MonitorEEGInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangchengcheng on 2018/1/3.
 */
@Controller
@RequestMapping("screen")
public class ScreenController {

    Logger logger = LoggerFactory.getLogger(ScreenController.class);

    @Autowired
    MonitorEEGInfoService monitorEEGInfoService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "screen/index";
    }

    @RequestMapping(value = "/getFatigue", method = RequestMethod.POST)
    @ResponseBody
    public String getFatigue(@RequestParam long startTime) {
        try {
            final class FatigueState {

                public FatigueState(double fatigueValue) {
                    this.fatigueValue = fatigueValue;
                }

                private double fatigueValue;

                public double getFatigueValue() {
                    return fatigueValue;
                }

                public void setFatigueValue(double fatigueValue) {
                    this.fatigueValue = fatigueValue;
                }
            }
            MonitorEEGInfo eegInfo = monitorEEGInfoService.findNewestEEGInfo();
            double fatigueValue = eegInfo.getEegMediation() +
                    (eegInfo.getEegHighalpha() + eegInfo.getEegLowalpha()) / (eegInfo.getEegLowbeta() + eegInfo.getEegHighbeta()) + 27;
            fatigueValue = Math.floor(fatigueValue);
            FatigueState fatigueState = new FatigueState(fatigueValue);
            return ReturnData.result(0, "获取疲劳度数据成功", JSON.toJSONString(fatigueState));
        } catch (Exception e) {
            logger.error("获取疲劳度数据失败：", e);
            return ReturnData.result(-1, "获取疲劳度数据失败", null);
        }
    }
}
