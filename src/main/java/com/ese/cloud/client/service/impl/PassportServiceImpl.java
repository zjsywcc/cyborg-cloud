package com.ese.cloud.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.service.PassportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * passport相关服务
 * Created by rencong on 17/4/20.
 */
@Service
public class PassportServiceImpl implements PassportService {

    Logger logger = LoggerFactory.getLogger(PassportServiceImpl.class);

    /**
     *
     */
//    @Value("${passport.login.checkurl}")
//    private String url;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public boolean checkDriverLoginSuccess(String mob) {

        MultiValueMap requestBody = new LinkedMultiValueMap();
        requestBody.add("mob", mob);



        try {
//            ResponseEntity result = restTemplate.getForEntity(url+"?mob="+mob.trim(), String.class);

//            if(result.getStatusCode() == HttpStatus.OK){
//                JSONObject obj = JSON.parseObject(result.getBody().toString());
//                logger.info("passport result info:{}",result.getBody());
//                int status = obj.getIntValue("result");
//                if(status == 1){
//                    return true;//已经登录成功
//                }else {
//                    return false;//未登录成功
//                }
//            }else{
//                logger.info("passport result error code:{},body:{}",result.getStatusCode(),result.getBody());
//               return false;
//            }
        }catch (Exception e){
            logger.error("passport check mob login error:",e);
            return false;
        }
        return false;
    }
}
