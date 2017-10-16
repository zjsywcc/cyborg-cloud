package com.ese.cloud.client.entity;

/**
 * Created by mengchenyun on 2017/3/30.
 */
public class ShortURL {

    private static final long serialVersionUID = 1L;

    /**
     *  id
     */
    private String id;

    /**
     *  加密后的url路径
     */
    private String shortURL;

    /**
     *  加密前的url路径,此处即为手机号
     */
    private String realURL;

    /**
     *  是否已经发送
     */
    private String status;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 导入用户的ID
     */
    private String userId;

    /**
     * 要发送短信的业务线
     */
    private String driverType;

    /**
     * 业务方发的还是自己发的标记 0 - 平台 1 - 业务方
     */
    private int sendType;

    /**
     * 业务方发送短信的内容
     */
    private String smsContent;

    /**
     * 业务方发送者的用户名
     */
    private String sendUser;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public String getRealURL() {
        return realURL;
    }

    public void setRealURL(String realURL) {
        this.realURL = realURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }
}
