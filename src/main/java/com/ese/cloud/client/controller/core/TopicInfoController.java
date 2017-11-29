package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.entity.AppInfo;
import com.ese.cloud.client.entity.TopicInfo;
import com.ese.cloud.client.service.AppInfoService;
import com.ese.cloud.client.service.TopicInfoService;
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

/**
 * Created by user on 17/2/15.
 */
@Controller
@RequestMapping("topic")
public class TopicInfoController {
    Logger logger = Logger.getLogger(TopicInfoController.class);
    @Autowired
    TopicInfoService topicInfoService;

    @Autowired
    AppInfoService appInfoService;


    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        return "topic/index";
    }


    /***
     * 列表
     * @param sSearch
     * @param sEcho
     * @param iDisplayStart
     * @param iDisplayLength
     * @param iColumns
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(
            @RequestParam("sSearch") String sSearch,//查询
            @RequestParam("sEcho") String sEcho,//标识
            @RequestParam("iDisplayStart") int iDisplayStart,//开始位置
            @RequestParam("iDisplayLength") int iDisplayLength,//显示长度
            @RequestParam("iColumns") String iColumns){

        List<TopicInfo> list = topicInfoService.pageFind(iDisplayStart, iDisplayLength);
        long count = topicInfoService.count();

        JSONObject json=new JSONObject();
        json.put("sEcho", sEcho);
        json.put("iTotalRecords",count);//总条数
        json.put("iTotalDisplayRecords",count);//显示总条数
        json.put("aaData", JSONArray.toJSON(list));

        return json.toString();

    }

    @RequestMapping(value="/add",method= RequestMethod.POST)
    @ResponseBody
    public  String  add(@RequestParam String appid,
                        @RequestParam String formatter,
                        @RequestParam String tag,
                        @RequestParam String topic,
                        @RequestParam String remarks){


        TopicInfo topicInfo = new TopicInfo();
        topicInfo.setAppId(appid);
        topicInfo.setFormatter(formatter);
        topicInfo.setTag(tag);
        topicInfo.setTopic(topic);
        topicInfo.setRemark(remarks);

        if(topicInfoService.add(topicInfo)){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }

    }

    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public  String  update(@RequestParam String appid,
                           @RequestParam String id,
                           @RequestParam String formatter,
                           @RequestParam String tag,
                           @RequestParam String topic,
                           @RequestParam String remarks){

        TopicInfo topicInfo = new TopicInfo();
        topicInfo.setAppId(appid);
        topicInfo.setFormatter(formatter);
        topicInfo.setTag(tag);
        topicInfo.setTopic(topic);
        topicInfo.setRemark(remarks);
        topicInfo.setId(id);

        if(topicInfoService.update(topicInfo)){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method= RequestMethod.POST)
    @ResponseBody
    public  String  delete(@RequestParam String id){

        if(topicInfoService.delete(id)){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }

    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @RequestMapping(value="/findById",method= RequestMethod.POST)
    @ResponseBody
    public  String  findById(@RequestParam String  id){
        TopicInfo topicInfo = topicInfoService.findById(id);
        if(topicInfo != null){
            return ReturnData.result(0,"", JSON.toJSONString(topicInfo));
        }else{
            return ReturnData.result(-1,"", null);
        }
    }

    @RequestMapping(value="/getAppIdList",method= RequestMethod.POST)
    @ResponseBody
    public  String  getAppIdList(){
        List<AppInfo> appIdList = appInfoService.all();
        List<JSONObject> jsons = new ArrayList<>();
        for(AppInfo appInfo : appIdList) {
            JSONObject json = new JSONObject();
            json.put("value", appInfo.getCode());
            jsons.add(json);
        }
        if(jsons != null){
            return ReturnData.result(0,"", JSON.toJSONString(jsons));
        }else{
            return ReturnData.result(-1,"", null);
        }
    }

}
