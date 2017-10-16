package com.ese.cloud.client.entity;

/**
 * 系统设定
 * Created by rencong on 17/4/18.
 */
public class SysSettingInfo {

    private String id;

    /**
     * 短信发送结果定时开关
     */
    private boolean sendResultStatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getSendResultStatus() {
        return sendResultStatus;
    }

    public void setSendResultStatus(boolean sendResultStatus) {
        this.sendResultStatus = sendResultStatus;
    }
}
