package com.ese.cloud.client.entity.hunanUnicom;

import java.util.List;

/**
 * 客户设备信息
 * Created by rencong on 16/12/27.
 */
public class ClientNetworkInfo {

    private static final long serialVersionUID = 1L;

    private String Id;

    /**
     * 客户ID
     */
    private String clientId;

    /**
     * ip地址信息
     */
    private List<String> ips;

    /**
     * OLT设备信息
     */
    private String OLT;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public String getOLT() {
        return OLT;
    }

    public void setOLT(String OLT) {
        this.OLT = OLT;
    }
}
