package com.ese.cloud.client.entity;

/**
 * Created by wangchengcheng on 2018/1/27.
 */
public class BiosignalInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 生理信号记录id
     */
    private String id;

    /**
     * 产生时间戳
     */
    private long timestamp;

    /**
     * 生理信号HEX字符串
     */
    private String packet;

    /**
     * 是否已经展示过
     */
    private boolean isRead;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
