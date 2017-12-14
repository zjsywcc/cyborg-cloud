package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.entity.ModuleInfo;
import com.ese.cloud.client.entity.RoleInfo;
import com.ese.cloud.client.service.RoleInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.apache.commons.lang3.StringUtils;
import com.ese.cloud.client.service.ModuleInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 角色管理
 * Created by rencong on 16/12/30.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    RoleInfoService roleInfoService;
    @Autowired
    ModuleInfoService moduleInfoService;



    @RequestMapping("/index")
    public ModelAndView index(){

        List<ModuleInfo> list = new ArrayList<>();

        List<ModuleInfo> list0 = moduleInfoService.findByLevel(0);
        List<ModuleInfo> list1 = moduleInfoService.findByLevel(1);
        List<ModuleInfo> list2 = moduleInfoService.findByLevel(2);

        for(ModuleInfo obj0 : list0){
            list.add(obj0);
            for(ModuleInfo obj1:list1){
                if(StringUtils.equals(obj1.getParentId(),obj0.getId())){
                    obj1.setName("++"+obj1.getName());//添加级别展示
                    list.add(obj1);
                    for(ModuleInfo obj2 : list2){
                        if(StringUtils.equals(obj1.getId(),obj2.getParentId())){
                            obj2.setName("+++"+obj2.getName());//添加级别展示
                            list.add(obj2);
                        }
                    }

                }
            }
        }



        ModelAndView mav = new ModelAndView();
        mav.addObject("data",list);
        mav.setViewName("auth/auth_group");

        return  mav;
    }


    /**
     * 添加角色信息
     * @param name
     * @param create
     * @param update
     * @param delete
     * @param view
     * @param description
     * @param remarks
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestParam("name") String name,
                      @RequestParam("level") int level,
                      @RequestParam(value = "create",required = false) String[] create,
                      @RequestParam(value = "update",required = false) String[] update,
                      @RequestParam(value = "delete",required = false) String[] delete,
                      @RequestParam(value = "view",required = false) String[] view,
                      @RequestParam(value = "description",required = false) String description,
                      @RequestParam(value = "remarks",required = false) String remarks) {

        Map<String,Set> permissions = new HashMap<>();


        //创建权限
        createPermission("create",permissions,create);
        //修改权限
        createPermission("update",permissions,update);
        //查看权限
        createPermission("view",permissions,view);
        //删除权限
        createPermission("delete",permissions,delete);


        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setDescription(description);
        roleInfo.setPermissions(permissions);
        roleInfo.setRemarks(remarks);
        roleInfo.setRole(name);
        roleInfo.setLevel(level);
        if(roleInfoService.add(roleInfo)){
            return ReturnData.result(0,"",null);
        }else{
            return ReturnData.result(-1,"",null);
        }
    }


    /**
     * 更新
     * @param id
     * @param name
     * @param create
     * @param update
     * @param delete
     * @param view
     * @param description
     * @param remarks
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public String update(
            @RequestParam("id") String id,
            @RequestParam("level") int level,
            @RequestParam("name") String name,
            @RequestParam(value = "create",required = false) String[] create,
            @RequestParam(value = "update",required = false) String[] update,
            @RequestParam(value = "delete",required = false) String[] delete,
            @RequestParam(value = "view",required = false) String[] view,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "remarks",required = false) String remarks) {

        Map<String,Set> permissions = new HashMap<>();


        //创建权限
        createPermission("create",permissions,create);
        //修改权限
        createPermission("update",permissions,update);
        //查看权限
        createPermission("view",permissions,view);
        //删除权限
        createPermission("delete",permissions,delete);


        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setDescription(description);
        roleInfo.setPermissions(permissions);
        roleInfo.setRemarks(remarks);
        roleInfo.setRole(name);
        roleInfo.setLevel(level);
        roleInfo.setId(id);
        if(roleInfoService.update(roleInfo)){
            return ReturnData.result(0,"",null);
        }else{
            return ReturnData.result(-1,"",null);
        }
    }


    /**
     * 根据id查询角色信息
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public String findById(@RequestParam("id") String id){
        RoleInfo roleInfo = roleInfoService.findById(id);
        if(roleInfo == null){
            return ReturnData.result(-1,"",null);
        }else{
            return ReturnData.result(0,"", JSON.toJSONString(roleInfo));
        }
    }


    /**
     * 查询全部角色信息
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    @ResponseBody
    public String all(){
        List<RoleInfo> roleInfo = roleInfoService.all();
        if(roleInfo == null){
            return ReturnData.result(-1,"",null);
        }else{
            return ReturnData.result(0,"", JSON.toJSONString(roleInfo));
        }
    }


    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("id") String id){
        boolean roleInfo = roleInfoService.delete(id);
        if(roleInfo){
            return ReturnData.result(0,"",null);
        }else{
            return ReturnData.result(-1,"", null);
        }
    }


    /**
     * 分页
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

        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<RoleInfo> list = roleInfoService.pageFind(iDisplayStart, iDisplayLength);

        JSONArray jsonlist=new JSONArray();
        for(RoleInfo obj : list){
            JSONObject object = new JSONObject();
            object.put("id", obj.getId());
            object.put("name", obj.getRole());
            object.put("desc", obj.getDescription());

            jsonlist.add(object);
        }

        JSONObject json=new JSONObject();

        Long count = roleInfoService.count(new Query());

        json.put("sEcho", sEcho);
        json.put("iTotalRecords", count);//总条数
        json.put("iTotalDisplayRecords",count);//显示总条数
        json.put("aaData",jsonlist);

        return json.toString();

    }




    private void createPermission(String auth,Map<String,Set> map,String[] data){

        if(data == null){
            return ;
        }

        for(String obj:data){

            Set permission = map.get(obj);

            if(permission == null ){
                Set set = new HashSet();
                set.add(auth);
                map.put(obj,set);

            }else{

                permission.add(auth);

                map.put(obj,permission);

            }
        }
    }


}
