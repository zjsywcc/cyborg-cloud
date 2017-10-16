package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.RecordInfo;

import java.util.List;

/**
 * 短信服务
 * Created by rencong on 17/2/6.
 */

public interface SMSService {


    /**
     * 发送短信消息
     * @param mob
     * @param message
     * @return
     */
    public boolean sendMessage(String mob,String message);


    /**
     * 再次生成访问短链并发送
     * @param mob 手机号
     * @return 6位段链接
     */
    public String newShortUrl(String mob);


    /**
     * 发送结果通知短信
     * @param list
     * @return
     */
    public boolean sendResultMsg(List<RecordInfo> list);







}
