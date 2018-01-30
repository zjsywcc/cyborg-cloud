package com.ese.cloud.client.entity;

/**
 * Created by wangchengcheng on 2017/11/9.
 */
public class MonitorEMGInfo {

    private static final long serialVersionUID = 1L;

    /**
     * EMG记录id
     */
    private String id;

    private long timestamp;

    private double emgValue;

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

    public double getEmgValue() {
        return emgValue;
    }

    public void setEmgValue(double emgValue) {
        this.emgValue = emgValue;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "MonitorEMGInfo{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", emgValue=" + emgValue +
                ", isRead=" + isRead +
                '}';
    }
}
