package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.entity.ClientInfo;
import com.ese.cloud.client.service.ClientService;
import com.ese.cloud.client.util.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.List;

/**
 * 客户管理
 * Created by rencong on 16/12/29.
 */
@Controller
@RequestMapping("client")
public class ClientController {

    Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "client/index";
    }

    @RequestMapping("/page_add")
    public String addHtml(){
        return "client/add";
    }


    /**
     * 列表
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


        JSONObject queryObject = JSON.parseObject(sSearch);

        Query query  = new Query();

        if(!StringUtils.isEmpty(sSearch)) {

            Criteria criteria = new Criteria();
            if (!StringUtils.isEmpty(queryObject.getString("client"))) {
                query.addCriteria(Criteria.where("name").all(queryObject.getString("client")));
            }
            if (!StringUtils.isEmpty(queryObject.getString("contact"))) {
                query.addCriteria(Criteria.where("contact").all(queryObject.getString("contact")));
            }
            if (!StringUtils.isEmpty(queryObject.getString("mob"))) {
                query.addCriteria(Criteria.where("mobPhone").all(queryObject.getString("mob")));
            }

        }


        List<ClientInfo> list = clientService.pageFind(iDisplayStart, iDisplayLength,query);

        JSONArray jsonlist=new JSONArray();
        if(list != null){
          for(ClientInfo obj : list){
            JSONObject object = new JSONObject();
            object.put("id", obj.getId());
            object.put("name", obj.getName());
            object.put("contact", obj.getContact());
            object.put("address", obj.getAddress());
            object.put("mob", obj.getMobPhone());
            object.put("type", obj.getType());

            jsonlist.add(object);
          }
        }

        JSONObject json=new JSONObject();
        json.put("sEcho", sEcho);
        json.put("iTotalRecords", clientService.count());//总条数
        json.put("iTotalDisplayRecords", clientService.count());//显示总条数
        json.put("aaData",jsonlist);

        return json.toString();

    }


    /**
     * 添加
     * @param name
     * @param type
     * @param mob
     * @param telPhone
     * @param address
     * @param contact
     * @param OLT
     * @param ip
     * @param accessNo
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(
            @RequestParam("name") String name,//客户名称
            @RequestParam("type") Integer type,//客户类别
            @RequestParam("mobPhone") String mob,//手机
            @RequestParam("telPhone") String telPhone,//电话
            @RequestParam("address") String address,//联系地址
            @RequestParam("contact") String contact,//联系人
            @RequestParam("OLT") String OLT,//OLT
            @RequestParam("ip") String ip,//ip
            @RequestParam("lat") Double lat,//纬度
            @RequestParam("lng") Double lng,//经度
            @RequestParam("accessNo") String accessNo,//接入号
            @RequestParam("remark") String remark//备注
            ){


        if(clientService.add(name, type,address, mob, telPhone, contact, OLT, ip, accessNo,remark,lat,lng)){

            return ReturnData.result(0,"添加成功",null);

        }else {
            return ReturnData.result(-1,"添加失败",null);
        }

    }


    /**
     * 修改
     * @param id
     * @param name
     * @param type
     * @param mob
     * @param telPhone
     * @param address
     * @param contact
     * @param OLT
     * @param ip
     * @param accessNo
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public String update(
            @RequestParam("id") String id,//id
            @RequestParam("name") String name,//客户名称
            @RequestParam("type") Integer type,//客户类别
            @RequestParam("mobPhone") String mob,//手机
            @RequestParam("telPhone") String telPhone,//电话
            @RequestParam("address") String address,//联系地址
            @RequestParam("contact") String contact,//联系人
            @RequestParam("OLT") String OLT,//OLT
            @RequestParam("ip") String ip,//ip
            @RequestParam("lat") Double lat,//纬度
            @RequestParam("lng") Double lng,//经度
            @RequestParam("accessNo") String accessNo,//接入号
            @RequestParam("remark") String remark//备注
    ){


        if(clientService.update(id,name, type,address, mob, telPhone,  contact, OLT, ip, accessNo,remark,lat,lng)){

            return ReturnData.result(0,"修改成功",null);

        }else {
            return ReturnData.result(-1,"修改失败",null);
        }

    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(@RequestParam("id") String id){
        if(clientService.delById(id)){
            return ReturnData.result(0,"删除成功",null);
        }else {
            return ReturnData.result(-1,"删除失败",null);
        }
    }


    /**
     * 根据id查询客户详细信息
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    @ResponseBody
    public String getById(@RequestParam("id") String id){

        ClientInfo info = clientService.findById(id);

        if(info != null){
            return ReturnData.result(0,"查询成功成功",JSONObject.toJSONString(info));
        }else {
            return ReturnData.result(-1,"查询失败",null);
        }
    }


    /**
     * 根据客户名称模糊查询,用于Select2
     * @param q
     * @return
     */
    @RequestMapping(value = "/likeByName",method = RequestMethod.POST)
    @ResponseBody
    public String likeByName(@RequestParam("q") String q){

        logger.info(q);

        try {
            q = URLDecoder.decode(q, "UTF-8");
        }catch (Exception e){
            logger.error("q转换故障",e);
        }

        List<ClientInfo> info = clientService.likeByName(q);

        //{'result':[{'id':'4048','text':'4808','name':'CHINA169-BJ'},{'id':'4048','text':'4808','name':'CHINA169-BJ'}],'total':'1'}”
        JSONObject result = new JSONObject();
        result.put("total",info.size());
        result.put("result",JSONArray.toJSON(info));


        return result.toJSONString();


    }

    /**
     * 拉去全部客户信息
     * @return
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ResponseBody
    public String all(){
        List<ClientInfo> info = clientService.findAll();



        return ReturnData.result(0,"",JSON.toJSONString(info));
    }




}
