package com.ese.cloud.client.entity;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/28.
 */
public class RecordInfo extends BaseModel{

    private static final long serialVersionUID = 1L;

    /**
     *  司机姓名
     */
    private String name;

    /**
     * 找回密码登录手机号
     */
    private String loginMob;

    /**
     *  注册手机
     */
    private String oldPhone;


    /**
     *  更换新手机
     */
    private String newPhone;

    /**
     *  身份证号
     */
    private String idCardNo;

    /**
     *  司机所在城市
     */
    private String city;

    /**
     *  车牌号
     */
    private String plateNo;

    /**
     *  银行卡后6位
     */
    private String bankCard;

    /**
     *  重新登陆方式
     *  0 - 重新登录成功
     *  1 - 重新登录失败
     */
    private Integer loginStatus;

    /**
     *  重新登陆时间
     */
    private String reLoginTime;

    /**
     *  第一次使用时间
     */
    private String startTime;

    /**
     *  最后使用时间
     */
    private String endTime;

    /**
     *  是否绑定过车辆
     *  0 - 是
     *  1 - 否
     */
    private int bindStatus;

    /**
     *  原手机失效地
     */
    private String phoneDisableLoc;

    /**
     *  原手机失效时间
     */
    private String phoneDisableTime;

    /**
     *  原手机失效原因
     */
    private String phoneDisableReason;

    /**
     *  手持身份证照片
     */
    private List<String> picWithIDCard;

    /**
     *  驾驶证照片
     */
    private List<String> picDriveLicense;

    /**
     *  新手机号购买凭证
     */
    private List<String> picNewPhoneProof;

    /**
     *  其他证明身份的照片
     */
    private List<String> picOtherProof;

    /**
     *  能否更改手机号
     *  0 - 不能更改
     *  1 - 可以更改
     */
    private int result;

    //0:执行 1:passport执行 2:face执行 3:mis执行
    private Integer transactionStatus = 0;


    /**
     * 策略处理状态
     * 0-未处理
     * 1-已处理
     */
    private int status;

    /**
     * 手机号修改状态(废弃)
     * 0-未修改
     * 1-已修改
     */
    private int updateMobStatus;

    /**
     * 人工处理结果,0-未处理,1-已经确认
     */
    private int checked;

    /**
     * 业务线
     */
    private String driverType;


    /**
     * 无法修改手机号的风控原因
     */
    private String reason;

    /**
     * 话术(被客户看)
     */
    private String terminology;

    /**
     * 提交状态,0-未提交,1-已经提交
     */
    private int submitStatus;

    /**
     * 0-未发送,1-已发送,处理结果通知短信
     */
    private int sendMsg;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getReLoginTime() {
        return reLoginTime;
    }

    public void setReLoginTime(String reLoginTime) {
        this.reLoginTime = reLoginTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getPhoneDisableLoc() {
        return phoneDisableLoc;
    }

    public void setPhoneDisableLoc(String phoneDisableLoc) {
        this.phoneDisableLoc = phoneDisableLoc;
    }

    public String getPhoneDisableTime() {
        return phoneDisableTime;
    }

    public void setPhoneDisableTime(String phoneDisableTime) {
        this.phoneDisableTime = phoneDisableTime;
    }

    public String getPhoneDisableReason() {
        return phoneDisableReason;
    }

    public void setPhoneDisableReason(String phoneDisableReason) {
        this.phoneDisableReason = phoneDisableReason;
    }

    public List<String> getPicWithIDCard() {
        return picWithIDCard;
    }

    public void setPicWithIDCard(List<String> picWithIDCard) {
        this.picWithIDCard = picWithIDCard;
    }

    public List<String> getPicDriveLicense() {
        return picDriveLicense;
    }

    public void setPicDriveLicense(List<String> picDriveLicense) {
        this.picDriveLicense = picDriveLicense;
    }

    public List<String> getPicNewPhoneProof() {
        return picNewPhoneProof;
    }

    public void setPicNewPhoneProof(List<String> picNewPhoneProof) {
        this.picNewPhoneProof = picNewPhoneProof;
    }

    public List<String> getPicOtherProof() {
        return picOtherProof;
    }

    public void setPicOtherProof(List<String> picOtherProof) {
        this.picOtherProof = picOtherProof;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getUpdateMobStatus() {
        return updateMobStatus;
    }

    public void setUpdateMobStatus(Integer updateMobStatus) {
        this.updateMobStatus = updateMobStatus;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getLoginMob() {
        return loginMob;
    }

    public void setLoginMob(String loginMob) {
        this.loginMob = loginMob;
    }

    public void setBindStatus(int bindStatus) {
        this.bindStatus = bindStatus;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUpdateMobStatus(int updateMobStatus) {
        this.updateMobStatus = updateMobStatus;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public String getTerminology() {
        return terminology;
    }

    public void setTerminology(String terminology) {
        this.terminology = terminology;
    }

    public int getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(int submitStatus) {
        this.submitStatus = submitStatus;
    }

    public int getSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(int sendMsg) {
        this.sendMsg = sendMsg;
    }

    public Integer getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Integer transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
