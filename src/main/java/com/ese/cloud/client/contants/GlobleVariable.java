package com.ese.cloud.client.contants;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 全局变量
 * Created by rencong on 16/9/23.
 */
public class GlobleVariable {

    /**
     * md5 盐
     */
    public static  String MD5_SALT = "xWDS593";

    /**
     * 短信发送队列
     */
    public static Queue SMS_QUEUE=  new ArrayBlockingQueue(10000);



}
