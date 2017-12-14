package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.service.RoleInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import com.ese.cloud.client.entity.ModuleInfo;
import com.ese.cloud.client.entity.RoleInfo;
import com.ese.cloud.client.service.ModuleInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 功能模块管理
 * Created by rencong on 16/10/2.
 */
@Controller
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    ModuleInfoService moduleInfoService;
    @Autowired
    RoleInfoService roleInfoService;



    @RequestMapping("/index")
    public String index() {




        return "module/index";
    }

    /**
     * 添加功能模块
     * @param name 模块名称
     * @param url  访问路径
     * @param key  key
     * @param ico  图标
     * @param remark 备注
     * @return  0:成功,1失败,2系统错误
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(  @RequestParam Integer level,
                        @RequestParam(required = false) String father,
                        @RequestParam String name,
                        @RequestParam String url,
                        @RequestParam String key,
                        @RequestParam String ico,
                        @RequestParam Integer sort,
                        @RequestParam String remark) {
        String result="";
        boolean status = false;
        if(StringUtils.isEmpty(father)){
            status = moduleInfoService.add(level,"",name.trim(), url.trim(), key.trim(), ico, remark,sort);
        }else{
            status = moduleInfoService.add(level,father,name.trim(), url.trim(), key.trim(), ico, remark,sort);
        }

        if(status){
            result = ReturnData.result(0,"功能模块添加成功",null);
        }else{
            result = ReturnData.result(-1,"功能模块添加失败",null);
        }


       return result;
    }


    /***
     * 修改
     * @param id
     * @param level
     * @param father
     * @param name
     * @param url
     * @param key
     * @param ico
     * @param remark
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(
            @RequestParam String id,
            @RequestParam Integer level,
            @RequestParam(required = false) String father,
            @RequestParam String name,
            @RequestParam String url,
            @RequestParam String key,
            @RequestParam String ico,
            @RequestParam Integer sort,
            @RequestParam String remark) {
        String result="";
        boolean status = false;
        if(StringUtils.isEmpty(father)){
            status = moduleInfoService.update(id,level,"",name.trim(), url.trim(), key.trim(), ico, remark,sort);
        }else{
            status = moduleInfoService.update(id,level,father,name.trim(), url.trim(), key.trim(), ico, remark,sort);
        }

        if(status){
            result = ReturnData.result(0,"功能模块修改成功",null);
        }else{
            result = ReturnData.result(-1,"功能模块修改失败",null);
        }


        return result;
    }


    /**
     * 通过级别查询此级别下的下拉列表信息
     * @param level
     * @return
     */
    @RequestMapping(value = "/find_by_level", method = RequestMethod.POST)
    @ResponseBody
    public String findByLevel(@RequestParam Integer level){

        List<ModuleInfo> list= moduleInfoService.findByLevel(level);
        if(StringUtils.isEmpty(list)){
            return ReturnData.result(2,"没有数据",null);
        }else{
            return ReturnData.result(0,"没有数据", JSON.toJSONString(list, true));
        }
    }


    /**
     * 通过级别查询此级别下的下拉列表信息
     * @return
     */
    @RequestMapping(value = "/getSidebarTree", method = RequestMethod.POST)
    @ResponseBody
    public String getSidebarTree(){

        Subject currentUser = SecurityUtils.getSubject();

        //先判断缓存中是否存在
        if(currentUser.getSession().getAttribute("user_tree") != null){

            return ReturnData.result(0,"缓存数据", JSON.toJSONString(currentUser.getSession().getAttribute("user_tree"), true));

        }


        //设定权限组id
        String roleid = String.valueOf(currentUser.getSession().getAttribute("role_id"));

        RoleInfo roleInfo = roleInfoService.findById(roleid);

        List<ModuleInfo> list= moduleInfoService.findByIds(roleInfo.getPermissions().keySet());
        //缓存到内存中
        currentUser.getSession().setAttribute("user_tree",list);

        if(StringUtils.isEmpty(list)){
            return ReturnData.result(2,"没有数据",null);
        }else{
            return ReturnData.result(0,"没有数据", JSON.toJSONString(list, true));
        }
    }



    /**
     * 查询全部信息
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public String all() {
        List<ModuleInfo> list = moduleInfoService.findAll();
        if(StringUtils.isEmpty(list)){
            return ReturnData.result(2,"没有数据",null);
        }else{
            return ReturnData.result(0,"没有数据", JSON.toJSONString(list, true));
        }
    }


    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    @ResponseBody
    public String findById(@RequestParam String id) {
        ModuleInfo obj = moduleInfoService.findById(id);
        if(StringUtils.isEmpty(obj)){
            return ReturnData.result(1,"没有数据",null);
        }else{
            return ReturnData.result(0,"数据", JSON.toJSONString(obj, true));
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam String id) {
        boolean obj = moduleInfoService.delete(id);
        if(!obj){
            return ReturnData.result(1,"没有数据",null);
        }else{
            return ReturnData.result(0,"数据", JSON.toJSONString(obj, true));
        }
    }
}
