package com.ese.cloud.client.api;

/**
 * Created by wangchengcheng on 2017/11/24.
 */
public class BaseMobileRequest {

    private String device;

    private String android_version;

    private String action;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
