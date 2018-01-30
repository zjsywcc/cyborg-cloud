package com.ese.cloud.client.entity;

/**
 * Created by wangchengcheng on 2018/1/28.
 */
public class MonitorEEGInfo {

    private static final long serialVersionUID = 1L;

    /**
     * EEG记录id
     */
    private String id;

    private long timestamp;

    private double eegDelta;
    private double eegTheta;
    private double eegLowalpha;
    private double eegHighalpha;
    private double eegLowbeta;
    private double eegHighbeta;
    private double eegLowgamma;
    private double eegMidgamma;
    private double eegAttention;
    private double eegMediation;

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

    public double getEegDelta() {
        return eegDelta;
    }

    public void setEegDelta(double eegDelta) {
        this.eegDelta = eegDelta;
    }

    public double getEegTheta() {
        return eegTheta;
    }

    public void setEegTheta(double eegTheta) {
        this.eegTheta = eegTheta;
    }

    public double getEegLowalpha() {
        return eegLowalpha;
    }

    public void setEegLowalpha(double eegLowalpha) {
        this.eegLowalpha = eegLowalpha;
    }

    public double getEegHighalpha() {
        return eegHighalpha;
    }

    public void setEegHighalpha(double eegHighalpha) {
        this.eegHighalpha = eegHighalpha;
    }

    public double getEegLowbeta() {
        return eegLowbeta;
    }

    public void setEegLowbeta(double eegLowbeta) {
        this.eegLowbeta = eegLowbeta;
    }

    public double getEegHighbeta() {
        return eegHighbeta;
    }

    public void setEegHighbeta(double eegHighbeta) {
        this.eegHighbeta = eegHighbeta;
    }

    public double getEegLowgamma() {
        return eegLowgamma;
    }

    public void setEegLowgamma(double eegLowgamma) {
        this.eegLowgamma = eegLowgamma;
    }

    public double getEegMidgamma() {
        return eegMidgamma;
    }

    public void setEegMidgamma(double eegMidgamma) {
        this.eegMidgamma = eegMidgamma;
    }

    public double getEegAttention() {
        return eegAttention;
    }

    public void setEegAttention(double eegAttention) {
        this.eegAttention = eegAttention;
    }

    public double getEegMediation() {
        return eegMediation;
    }

    public void setEegMediation(double eegMediation) {
        this.eegMediation = eegMediation;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "MonitorEEGInfo{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", eegDelta=" + eegDelta +
                ", eegTheta=" + eegTheta +
                ", eegLowalpha=" + eegLowalpha +
                ", eegHighalpha=" + eegHighalpha +
                ", eegLowbeta=" + eegLowbeta +
                ", eegHighbeta=" + eegHighbeta +
                ", eegLowgamma=" + eegLowgamma +
                ", eegMidgamma=" + eegMidgamma +
                ", eegAttention=" + eegAttention +
                ", eegMediation=" + eegMediation +
                ", isRead=" + isRead +
                '}';
    }
}
