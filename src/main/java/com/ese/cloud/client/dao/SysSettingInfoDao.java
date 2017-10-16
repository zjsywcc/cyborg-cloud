package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.SysSettingInfo;

/**
 * 系统信息操作
 * Created by rencong on 17/4/18.
 */
public interface SysSettingInfoDao {

    /**
     * 更改定时短信任务标记
     * @param status
     * @return
     */
    public boolean updateSendMsgStatus(boolean status);

    /**
     * 获取系统配置
     * @return
     */
    public SysSettingInfo getSysConfig();


}
