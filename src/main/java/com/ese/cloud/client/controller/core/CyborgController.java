package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.entity.CyborgInfo;
import com.ese.cloud.client.service.CyborgInfoService;
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
 * Created by wangchengcheng on 2017/10/16.
 */
@Controller
@RequestMapping("cyborg")
public class CyborgController {

    Logger logger = Logger.getLogger(CyborgController.class);

    @Autowired
    CyborgInfoService cyborgInfoService;


    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        return "cyborg/index";
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

        List<CyborgInfo> list = cyborgInfoService.pageFind(iDisplayStart, iDisplayLength);
        long count = cyborgInfoService.count();

        JSONObject json=new JSONObject();
        json.put("sEcho", sEcho);
        json.put("iTotalRecords",count);//总条数
        json.put("iTotalDisplayRecords",count);//显示总条数
        json.put("aaData", JSONArray.toJSON(list));

        return json.toString();

    }

    /**
     * 添加电子人信息
     * @param name
     * @param remarks
     * @return
     */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    @ResponseBody
    public  String  add(@RequestParam String name,
                        @RequestParam String remarks){

        CyborgInfo cyborgInfo = new CyborgInfo();
        cyborgInfo.setName(name);
        cyborgInfo.setRemarks(remarks);

        if(cyborgInfoService.add(cyborgInfo)){
            return ReturnData.result(0,"添加电子人信息成功", null);
        }else{
            return ReturnData.result(-1,"添加电子人信息失败", null);
        }

    }

    /**
     * 修改电子人信息
     * @param id
     * @param name
     * @param remarks
     * @return
     */
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public  String  update(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String remarks){

        CyborgInfo cyborgInfo = new CyborgInfo();
        cyborgInfo.setName(name);
        cyborgInfo.setId(id);
        cyborgInfo.setRemarks(remarks);

        if(cyborgInfoService.update(cyborgInfo)){
            return ReturnData.result(0,"修改电子人信息成功", null);
        }else{
            return ReturnData.result(-1,"修改电子人信息失败", null);
        }

    }

    /**
     * 删除电子人
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method= RequestMethod.POST)
    @ResponseBody
    public  String  delete(@RequestParam String id){

        if(cyborgInfoService.delete(id)){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }

    }

    /**
     * 根据id查询电子人信息
     * @param id
     * @return
     */
    @RequestMapping(value="/findById",method= RequestMethod.POST)
    @ResponseBody
    public  String  findById(@RequestParam String  id){
        CyborgInfo cyborgInfo = cyborgInfoService.findById(id);
        if(cyborgInfo != null){
            return ReturnData.result(0,"", JSON.toJSONString(cyborgInfo));
        }else{
            return ReturnData.result(-1,"", null);
        }
    }

}
