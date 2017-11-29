package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.entity.MetricInfo;
import com.ese.cloud.client.entity.TopicInfo;
import com.ese.cloud.client.service.MetricInfoService;
import com.ese.cloud.client.service.TopicInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/3.
 */
@Controller
@RequestMapping("api")
public class APIController {

    Logger logger = Logger.getLogger(APIController.class);

    @Autowired
    TopicInfoService topicInfoService;

    @Autowired
    MetricInfoService metricInfoService;

    @RequestMapping(value="/getAllTopics",method= RequestMethod.POST)
    @ResponseBody
    public  String  getAllTopics(){
        List<TopicInfo> topicInfos = topicInfoService.all();
        if(topicInfos != null){
            return ReturnData.result(0,"", JSON.toJSONString(topicInfos));
        }else{
            return ReturnData.result(-1,"", null);
        }
    }

    @RequestMapping(value="/getAllMetrics",method= RequestMethod.POST)
    @ResponseBody
    public  String  getAllMetrics(){
        List<MetricInfo> metricInfos = metricInfoService.all();
        if(metricInfos != null){
            return ReturnData.result(0,"", JSON.toJSONString(metricInfos));
        }else{
            return ReturnData.result(-1,"", null);
        }
    }
}