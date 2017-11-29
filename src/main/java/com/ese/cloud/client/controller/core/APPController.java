package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.entity.AppInfo;
import com.ese.cloud.client.service.AppInfoService;
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
 * app信息控制器
 * Created by rencong on 17/2/15.
 */
@Controller
@RequestMapping("app")
public class APPController {

    Logger logger = Logger.getLogger(APIManagerController.class);
    @Autowired
    AppInfoService appInfoService;


    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        return "app/index";
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

        List<AppInfo> list = appInfoService.pageFind(iDisplayStart, iDisplayLength);
        long count = appInfoService.count();

        JSONObject json=new JSONObject();
        json.put("sEcho", sEcho);
        json.put("iTotalRecords",count);//总条数
        json.put("iTotalDisplayRecords",count);//显示总条数
        json.put("aaData",JSONArray.toJSON(list));

        return json.toString();

    }

    /**
     * 添加app信息
     * @param name
     * @param code
     * @param owner
     * @param mobile
     * @param email
     * @param remarks
     * @return
     */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    @ResponseBody
    public  String  add(@RequestParam String name,
                        @RequestParam String code,
                        @RequestParam String owner,
                        @RequestParam String mobile,
                        @RequestParam String email,
                        @RequestParam String remarks){

        AppInfo appInfo = new AppInfo();
        appInfo.setName(name);
        appInfo.setCode(code);
        appInfo.setMob(mobile);
        appInfo.setOwner(owner);
        appInfo.setRemarks(remarks);
        appInfo.setEmail(email);



        if(appInfoService.add(appInfo)){
            return ReturnData.result(0,"添加app信息成功", null);
        }else{
            return ReturnData.result(-1,"添加app信息失败", null);
        }

    }

    /**
     * 修改app信息
     * @param id
     * @param name
     * @param code
     * @param owner
     * @param mobile
     * @param email
     * @param remarks
     * @return
     */
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public  String  update(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String code,
            @RequestParam String owner,
            @RequestParam String mobile,
            @RequestParam String email,
            @RequestParam String remarks){

        AppInfo appInfo = new AppInfo();
        appInfo.setId(id);
        appInfo.setName(name);
        appInfo.setCode(code);
        appInfo.setMob(mobile);
        appInfo.setOwner(owner);
        appInfo.setRemarks(remarks);
        appInfo.setEmail(email);

        if(appInfoService.update(appInfo)){
            return ReturnData.result(0,"修改APP信息成功", null);
        }else{
            return ReturnData.result(-1,"修改app信息失败", null);
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

        if(appInfoService.delete(id)){
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
        AppInfo appInfo = appInfoService.findById(id);
        if(appInfo != null){
            return ReturnData.result(0,"", JSON.toJSONString(appInfo));
        }else{
            return ReturnData.result(-1,"", null);
        }
    }

    /**
     * 验证应用识别码是否存在
     * @param id 信息id
     * @param type 用户类型,0-用户名,1-手机号,2-邮箱,3-微信
     * @param value 值
     * @return
     */
    @RequestMapping("/validateAppInfo")
    @ResponseBody
    public boolean validateAppInfo(
            @RequestParam("id") String id,
            @RequestParam("type") String type,
            @RequestParam("value") String value){


        boolean appInfo = !appInfoService.validateAppInfo(id,type,value);

        return appInfo;

    }




}
