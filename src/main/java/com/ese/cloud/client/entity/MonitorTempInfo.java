package com.ese.cloud.client.entity;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
public class MonitorTempInfo {

    private static final long serialVersionUID = 1L;

    /**
     * EMG记录id
     */
    private String id;

    private long timestamp;

    private double tempValue;

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

    public double getTempValue() {
        return tempValue;
    }

    public void setTempValue(double tempValue) {
        this.tempValue = tempValue;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "MonitorTempInfo{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", tempValue=" + tempValue +
                ", isRead=" + isRead +
                '}';
    }
}
