package com.ese.cloud.client.entity;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
public class MonitorRRInfo {

    private static final long serialVersionUID = 1L;

    /**
     * EMG记录id
     */
    private String id;

    private long timestamp;

    private double rrValue;

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

    public double getRrValue() {
        return rrValue;
    }

    public void setRrValue(double rrValue) {
        this.rrValue = rrValue;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "MonitorRRInfo{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", rrValue=" + rrValue +
                ", isRead=" + isRead +
                '}';
    }
}
