package com.ese.cloud.client.service.impl;

import com.ese.cloud.client.dao.ShortURLDao;
import com.ese.cloud.client.entity.RecordInfo;
import com.ese.cloud.client.entity.ShortURL;
import com.ese.cloud.client.service.RecordInfoService;
import com.ese.cloud.client.service.SMSService;
import com.ese.cloud.client.util.ShortURLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 发送短信消息
 * Created by rencong on 17/2/6.
 */
@Service
public class SMSServiceImpl implements SMSService {

    Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);

//    @Autowired
//    SendMsgUtil sendMsgUtil;
    @Autowired
    ShortURLDao shortURLDao;
    @Autowired
    RecordInfoService recordInfoService;


    @Override
    public boolean sendMessage(String mob, String message) {

        logger.info("发送短信手机号:{},短信内容:{}",mob,message);
        try {
//            sendMsgUtil.sendMsg(mob, message);
        }catch (Exception e){
            logger.error("发送短信失败",e);
            return false;
        }

        return true;
    }

    @Override
    public String newShortUrl(String mob) {
        //1.生成短连接
        String encode = ShortURLUtil.shortUrl(mob)[0];
        //2.发送保存到数据库并标记发送
        ShortURL obj = new ShortURL();
        obj.setUserId("system");//系统
        obj.setCreateTime(new Date().toLocaleString());
        obj.setRealURL(mob);
        obj.setShortURL(encode);
        obj.setStatus("已发送");
        shortURLDao.add(obj);

        return encode;
    }

    @Override
    public boolean sendResultMsg(List<RecordInfo> list) {
        String msg="";
        return true;
    }
}
