package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mengchenyun on 2017/3/1.
 */
@Controller
@RequestMapping("metric")
public class MetricController {

    Logger logger = Logger.getLogger(MetricController.class);

    @Autowired
    MetricInfoService metricInfoService;

    @Autowired
    TopicInfoService topicInfoService;

    private class MetricWrapper {
        private String id;
        private String name;
        private String topic;
        private String script;
        private String remarks;
        private String status;

        public MetricWrapper(String id, String name, String topic, String script, String remarks, String status) {
            this.id = id;
            this.name = name;
            this.topic = topic;
            this.script = script;
            this.remarks = remarks;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getScript() {
            return script;
        }

        public void setScript(String script) {
            this.script = script;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        return "metric/index";
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

        List<MetricInfo> list = metricInfoService.pageFind(iDisplayStart, iDisplayLength);
        long count = metricInfoService.count();
        List<MetricWrapper> metricWrappers = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            String id = list.get(i).getId();
            String name = list.get(i).getName();
            String remarks = list.get(i).getRemarks();
            String script = list.get(i).getScript();
            String status = list.get(i).getStatus();
            StringBuffer sb = new StringBuffer();
            boolean flag = true;
            for(String topic : list.get(i).getTopics()) {
                if(flag) {
                    sb.append(topic);
                    flag = false;
                } else {
                    sb.append(",").append(topic);
                }
            }
            String topic = sb.toString();
            MetricWrapper metricWrapper = new MetricWrapper(id, name, topic, script, remarks, status);
            metricWrappers.add(metricWrapper);
        }
        JSONObject json=new JSONObject();
        json.put("sEcho", sEcho);
        json.put("iTotalRecords",count);//总条数
        json.put("iTotalDisplayRecords",count);//显示总条数
        json.put("aaData", JSONArray.toJSON(metricWrappers));

        return json.toString();

    }

    @RequestMapping(value="/add",method= RequestMethod.POST)
    @ResponseBody
    public  String  add(@RequestParam String metric,
                        @RequestParam String topic,
                        @RequestParam String script,
                        @RequestParam String remarks,
                        @RequestParam String status){


        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setName(metric);
        String[] topics = topic.split(",");
        Set<String> set = new HashSet<>();
        for(String t : topics) {
            set.add(t);
        }
        metricInfo.setTopics(set);
        metricInfo.setScript(script);
        metricInfo.setRemarks(remarks);
        metricInfo.setStatus(status);

        if(metricInfoService.add(metricInfo)){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }

    }

    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public  String  update(@RequestParam String metric,
                           @RequestParam String topic,
                           @RequestParam String script,
                           @RequestParam String remarks,
                           @RequestParam String status,
                           @RequestParam String id){

        MetricInfo metricInfo = new MetricInfo();
        metricInfo.setName(metric);
        String[] topics = topic.split(",");
        Set<String> set = new HashSet<>();
        for(String t : topics) {
            set.add(t);
        }
        metricInfo.setTopics(set);
        metricInfo.setScript(script);
        metricInfo.setRemarks(remarks);
        metricInfo.setStatus(status);
        metricInfo.setId(id);

        if(metricInfoService.update(metricInfo)){
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

        if(metricInfoService.delete(id)){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }

    }

    /**
     * 根据id查询指标信息
     * @param id
     * @return
     */
    @RequestMapping(value="/findById",method= RequestMethod.POST)
    @ResponseBody
    public  String  findById(@RequestParam String  id){
        MetricInfo metricInfo = metricInfoService.findById(id);
        String metricId = metricInfo.getId();
        String name = metricInfo.getName();
        String remarks = metricInfo.getRemarks();
        String script = metricInfo.getScript();
        String status = metricInfo.getStatus();
        StringBuffer sb = new StringBuffer();
        boolean flag = true;
        for(String topic : metricInfo.getTopics()) {
            if(flag) {
                sb.append(topic);
                flag = false;
            } else {
                sb.append(",").append(topic);
            }
        }
        String topic = sb.toString();
        MetricWrapper metricWrapper = new MetricWrapper(metricId, name, topic, script, remarks, status);
        if(metricInfo != null){
            return ReturnData.result(0,"", JSON.toJSONString(metricWrapper));
        }else{
            return ReturnData.result(-1,"", null);
        }
    }

    @RequestMapping(value="/getTopicList",method= RequestMethod.POST)
    @ResponseBody
    public  String  getTopicList(){
        List<TopicInfo> topicList = topicInfoService.all();
        List<JSONObject> jsons = new ArrayList<>();
        for(TopicInfo topicInfo : topicList) {
            JSONObject json = new JSONObject();
            json.put("value", topicInfo.getTopic());
            jsons.add(json);
        }
        if(jsons != null){
            return ReturnData.result(0,"", JSON.toJSONString(jsons));
        }else{
            return ReturnData.result(-1,"", null);
        }
    }

}
