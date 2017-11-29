package com.ese.cloud.client.util;

import com.alibaba.fastjson.JSONObject;


/**
 * 前后端交互json格式统一
 * Created by rencong on 16/12/23.
 */
public class ReturnData {


    /**
     * 公用的返回方法
     * @param code code
     * @param msg 消息
     * @param data 数据
     * @return
     */
    public static String result(Integer code, String msg, String data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        jsonObject.put("data",data);

        return jsonObject.toJSONString();
    }

}
