package com.ese.cloud.client.service;

/**
 * passport提供的相关服务
 * Created by rencong on 17/4/20.
 */
public interface PassportService {

    /**
     * 根据手机号查询司机当天是否登录过
     * @param mob
     * @return
     */
    public boolean checkDriverLoginSuccess(String mob);

}
