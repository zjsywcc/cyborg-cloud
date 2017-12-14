package com.ese.cloud.client.controller.app;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.api.LoginRequest;
import com.ese.cloud.client.api.RegisterRequest;
import com.ese.cloud.client.contants.GlobleVariable;
import com.ese.cloud.client.entity.CyborgInfo;
import com.ese.cloud.client.entity.app.AppUser;
import com.ese.cloud.client.service.AppUserService;
import com.ese.cloud.client.service.CyborgInfoService;
import com.ese.cloud.client.util.ReturnData;
import com.ese.cloud.client.util.TokenUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangchengcheng on 2017/11/23.
 */
@Controller
@RequestMapping("appuser")
public class AppUserController {

    Logger logger = LoggerFactory.getLogger(AppUserController.class);


    @Autowired
    AppUserService appUserService;

    @Autowired
    CyborgInfoService cyborgInfoService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody LoginRequest request) {
        String password = request.getPassword();
        String username = request.getUsername();
        password = new Md5Hash(password, GlobleVariable.MD5_SALT).toString();
        logger.info("login username:{},passowrd:{}",username,password);
        AppUser appUser = appUserService.getAppUserByUsername(username);
        if(appUser != null) {
            if(appUser.getPassword().equals(password)) {
                String token = TokenUtil.generateToken(appUser.getUid());
                appUser.setToken(token);
                appUserService.update(appUser);
                return ReturnData.result(200,"登录成功!", JSON.toJSONString(appUser));
            } else {
                logger.info("password incorrect: {}", password + "==" + appUser.getPassword());
                return ReturnData.result(4002,"密码错误!",null);
            }
        } else {
            return ReturnData.result(4001,"用户不存在!",null);
        }
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody RegisterRequest request) {
        String password = request.getPassword();
        String username = request.getUsername();
        logger.info("register username:{},passowrd:{}",username,password);
        AppUser appUser = appUserService.getAppUserByUsername(username);
        if(appUser == null) {
            appUserService.register(username, password, username);
            appUser = new AppUser();
            appUser.setUsername(username);
            appUser.setPassword(password);
            appUser.setEmail(username);
            CyborgInfo cyborgInfo = new CyborgInfo();
            cyborgInfo.setName(appUser.getUsername());
            cyborgInfo.setRemarks(appUser.getEmail());
            cyborgInfoService.add(cyborgInfo);
            return ReturnData.result(200,"注册成功!", JSON.toJSONString(appUser));
        } else {
            return ReturnData.result(4001,"用户已存在!",null);
        }
    }


}
