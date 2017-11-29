package com.ese.cloud.client.entity.hunanUnicom;

import java.util.Set;

/**
 * 上报网络故障
 * Created by rencong on 16/12/24.
 */
public class NetworkReport {

    private static final long serialVersionUID = 1L;
    private String id;


    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 客户在高德地图中的地理位置ID
     */
    private String clientGaoDeID;

    /**
     * 客户地址
     */
    private String address;
    /**
     * 故障发生时间
     */
    private Long createTime;

    /**
     * 故障修复时间
     */
    private Long finishTime;

    /**
     * 故障状态0-未处理,1-处理中,2-已处理
     */
    private Integer status;

    /**
     * 故障解决时间
     */
    private Long saveTime;

    /**
     * 故障现象
     */
    private String faultShow;

    /**
     * 故障原因
     *
     */
    private String fault;

    /**
     * 上行OLT
     */
    private String upOLT;

    /**
     * 客户联系人
     */
    private String clientLinkName;

    /***
     * 客户联系方式
     */
    private String clientLinkMob;

    /**
     * 受理人
     */
    private String worker;

    /**
     * 接口人
     */
    private Set<String> interfaceWere;

    /**
     * 其他故障现象
     */
    private String otherShow;
    /**
     * 故障行政区编码
     */
    private String adCode;

    /**
     * 上报人微信号码
     */
    private String wxNumber;

    /**
     * 星级
     */
    private int star;

    /**
     * 客户评价
     */
    private String evaluate;

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Long saveTime) {
        this.saveTime = saveTime;
    }

    public String getFaultShow() {
        return faultShow;
    }

    public void setFaultShow(String faultShow) {
        this.faultShow = faultShow;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getUpOLT() {
        return upOLT;
    }

    public void setUpOLT(String upOLT) {
        this.upOLT = upOLT;
    }

    public String getClientLinkMob() {
        return clientLinkMob;
    }

    public void setClientLinkMob(String clientLink) {
        this.clientLinkMob = clientLink;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOtherShow() {
        return otherShow;
    }

    public void setOtherShow(String otherShow) {
        this.otherShow = otherShow;
    }



    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClienName(String clienName) {
        this.clientName = clienName;
    }

    public String getClientGaoDeID() {
        return clientGaoDeID;
    }

    public void setClientGaoDeID(String clienGaoDeID) {
        this.clientGaoDeID = clienGaoDeID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getClientLinkName() {
        return clientLinkName;
    }

    public void setClientLinkName(String clientLinkName) {
        this.clientLinkName = clientLinkName;
    }


    public Set<String> getInterfaceWere() {
        return interfaceWere;
    }

    public void setInterfaceWere(Set<String> interfaceWere) {
        this.interfaceWere = interfaceWere;
    }

    public String getWxNumber() {
        return wxNumber;
    }

    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
