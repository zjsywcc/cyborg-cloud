package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import com.ese.cloud.client.entity.APIInfo;
import com.ese.cloud.client.service.APIInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * api管理控制器
 * Created by rencong on 17/1/24.
 */
@Controller
@RequestMapping("api_manager")
public class APIManagerController {

    Logger logger = Logger.getLogger(APIManagerController.class);
    @Autowired
    APIInfoService apiInfoService;


    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        return "api/index";
    }


    /**
     * 添加
     * @param name
     * @param code
     * @param password
     * @param type
     * @param remarks
     * @return
     */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    @ResponseBody
    public  String  add(@RequestParam String name,
                        @RequestParam String code,
                        @RequestParam String password,
                        @RequestParam String type,
                        @RequestParam String remarks){

        APIInfo apiInfo = new APIInfo();
        apiInfo.setRemarks(remarks);
        apiInfo.setName(name);
        apiInfo.setCode(code);
        apiInfo.setPassword(password);
        apiInfo.setType(type);



        if(apiInfoService.add(apiInfo)){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }

    }


    /**
     * 修改
     * @param id
     * @param name
     * @param code
     * @param password
     * @param type
     * @param remarks
     * @return
     */
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public  String  update(
            @RequestParam String  id,
            @RequestParam String name,
            @RequestParam String code,
            @RequestParam String password,
            @RequestParam String type,
            @RequestParam String remarks){

        APIInfo apiInfo = new APIInfo();
        apiInfo.setType(type);
        apiInfo.setPassword(password);
        apiInfo.setCode(code);
        apiInfo.setName(name);
        apiInfo.setId(id);
        apiInfo.setRemarks(remarks);


        if(apiInfoService.update(apiInfo)){
            return ReturnData.result(0,"",null);
        }else{
            return ReturnData.result(-1,"", null);
        }

    }


    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    @RequestMapping(value="/findById",method= RequestMethod.POST)
    @ResponseBody
    public  String  findById(@RequestParam String  id){


        APIInfo userInfo = apiInfoService.findById(id);

        if(userInfo != null){
            return ReturnData.result(0,"", JSON.toJSONString(userInfo));
        }else{
            return ReturnData.result(-1,"", null);
        }

    }


    /**
     * 获取分页信息
     * @param sEcho
     * @param iDisplayStart
     * @param iDisplayLength
     * @param iColumns
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(
            @RequestParam("sEcho") String sEcho,//标识
            @RequestParam("iDisplayStart") int iDisplayStart,//开始位置
            @RequestParam("iDisplayLength") int iDisplayLength,//显示长度
            @RequestParam("iColumns") String iColumns){
        List<APIInfo> list = apiInfoService.pageFind(iDisplayStart, iDisplayLength);
        JSONArray jsonlist=new JSONArray();
        for(APIInfo obj : list){
            JSONObject object = new JSONObject();
            object.put("id", obj.getId());
            object.put("name", obj.getName());
            object.put("code", obj.getCode());
            object.put("password", obj.getPassword());
            object.put("type", obj.getType());
            object.put("remarks", obj.getRemarks());

            jsonlist.add(object);
        }

        JSONObject json=new JSONObject();
        Long count = apiInfoService.count();
        json.put("sEcho", sEcho);
        json.put("iTotalRecords", count);//总条数
        json.put("iTotalDisplayRecords", count);//显示总条数
        json.put("aaData",jsonlist);

        return json.toString();
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("id") String id){
        boolean roleInfo = apiInfoService.delete(id);
        if(roleInfo){
            return ReturnData.result(0,"",null);
        }else{
            return ReturnData.result(-1,"", null);
        }
    }




}
