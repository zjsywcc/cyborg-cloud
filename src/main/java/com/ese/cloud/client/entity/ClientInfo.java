package com.ese.cloud.client.entity;

/**
 * 客户信息
 * Created by rencong on 16/12/27.
 */
public class ClientInfo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 客户级别,0-大客户,1-中型,2-小客户
     */
    private Integer type;

    /**
     * 行政区ID
     */
    private String adCode;

    /**
     * 高德地图地点id,唯一
     */
    private String geoID;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 联系电话,手机
     */
    private String mobPhone;

    /**
     * 联系电话,座机
     */
    private String telePhone;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 业务OLT
     */
    private String OLT;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 接入号
     */
    private String accessNo;

    /**
     * 备注
     */
    private String remark;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobPhone() {
        return mobPhone;
    }

    public void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOLT() {
        return OLT;
    }

    public void setOLT(String OLT) {
        this.OLT = OLT;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccessNo() {
        return accessNo;
    }

    public void setAccessNo(String accessNo) {
        this.accessNo = accessNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public String getGeoID() {
        return geoID;
    }

    public void setGeoID(String geoID) {
        this.geoID = geoID;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }
}
