package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.entity.UserInfo;
import com.ese.cloud.client.service.UserInfoService;
import com.ese.cloud.client.util.ReturnData;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户管理控制器
 * Created by rencong on 16/9/12.
 */
@Controller
@RequestMapping("/user")
public class UserController {

   Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    UserInfoService userInfoService;


    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){


        return "user/index";
    }




    @RequestMapping(value="/logout",method= RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes ){
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息

        Subject currentUser = SecurityUtils.getSubject();

        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return "redirect:/login";
    }


    /***
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value="/register",method= RequestMethod.POST)
    @ResponseBody
    public  String  register(@RequestParam String  username,
                              @RequestParam String password,
                              @RequestParam String email,
                              RedirectAttributes redirectAttributes){
        logger.info("user register:username="+username+",password="+password+",email="+email);

        if(userInfoService.register(username,password,email)) {
            redirectAttributes.addFlashAttribute("status",0);
            redirectAttributes.addFlashAttribute("msg","注册成功");
        }else{
            redirectAttributes.addFlashAttribute("status", 1);
            redirectAttributes.addFlashAttribute("msg", "注册失败");
        }
        return "redirect:/register";
    }


    /**
     * 修改密码
     * @param password 新密码
     * @return
     */
    @RequestMapping(value="/updatePwd",method= RequestMethod.POST)
    @ResponseBody
    public  String  updatePwd(@RequestParam String  password){


        Subject currentUser = SecurityUtils.getSubject();
        String id = String.valueOf(currentUser.getSession().getAttribute("user_id"));

        boolean status = userInfoService.updatePassword(id,password);

        if(status){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }
    }



    @RequestMapping(value="/add",method= RequestMethod.POST)
    @ResponseBody
    public  String  add(@RequestParam String  username,
                        @RequestParam String role,
                        @RequestParam String mobile,
                        @RequestParam String email,
                        @RequestParam String wechat,
                        @RequestParam String remarks){

        UserInfo userInfo = new UserInfo();
        userInfo.setRoleId(role);
        userInfo.setCreateTime(new Date());
        userInfo.setEmail(email.trim());
        userInfo.setPhone(mobile);
        userInfo.setWechat(wechat);
        userInfo.setRemark(remarks);
        userInfo.setUsername(username.trim());
        userInfo.setStatus(1);



        if(userInfoService.add(userInfo)){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }

    }


    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public  String  update(
            @RequestParam String  id,
            @RequestParam String  username,
            @RequestParam String role,
            @RequestParam String mobile,
            @RequestParam String email,
            @RequestParam String wechat,
            @RequestParam String remarks){

        if(userInfoService.update(id, username,email,mobile, remarks,role, wechat)){
            return ReturnData.result(0,"",null);
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


        UserInfo userInfo = userInfoService.findById(id);

        if(userInfo != null){
            return ReturnData.result(0,"", JSON.toJSONString(userInfo));
        }else{
            return ReturnData.result(-1,"", null);
        }

    }


    /**
     * 获取全部用户信息
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

        List<UserInfo> list = userInfoService.pageFind(iDisplayStart, iDisplayLength);

        JSONArray jsonlist=new JSONArray();
        for(UserInfo obj : list){
            JSONObject object = new JSONObject();
            object.put("id", obj.getId());
            object.put("name", obj.getUsername());
            object.put("email", obj.getEmail());
            object.put("phone", obj.getPhone());
            object.put("wechat", obj.getWechat());
            object.put("status", obj.getStatus());

            jsonlist.add(object);
        }

        JSONObject json=new JSONObject();
        Long count = userInfoService.count();
        json.put("sEcho", sEcho);
        json.put("iTotalRecords", count);//总条数
        json.put("iTotalDisplayRecords", count);//显示总条数
        json.put("aaData",jsonlist);

        return json.toString();

    }


    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("id") String id){
        boolean roleInfo = userInfoService.delete(id);
        if(roleInfo){
            return ReturnData.result(0,"",null);
        }else{
            return ReturnData.result(-1,"", null);
        }
    }


    /**
     * 验证用户信息是否存在
     * @param id 信息id
     * @param type 用户类型,0-用户名,1-手机号,2-邮箱,3-微信
     * @param value 值
     * @return
     */
    @RequestMapping("/validateUserInfo")
    @ResponseBody
    public boolean validateUserInfo(
            @RequestParam("id") String id,
            @RequestParam("type") String type,
            @RequestParam("value") String value){


        boolean roleInfo = !userInfoService.validateUserInfo(id,type,value);

        return roleInfo;

    }






    /**
     * Description: 判断是否为空<br/>
     * author: caishanzheng<br/>
     * date: 2016年8月9日 下午1:25:39<br/>
     * @param parameter 参数
     * @return<br/>
     * boolean
     */
    public boolean isNull(String parameter){
        return "".equals(parameter) ||  parameter.length()<1 || parameter.isEmpty();
    }


}
