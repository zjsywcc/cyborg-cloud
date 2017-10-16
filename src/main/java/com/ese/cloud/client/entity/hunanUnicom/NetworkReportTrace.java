package com.ese.cloud.client.entity.hunanUnicom;

/**
 * 故障的跟踪信息
 * Created by renong on 16/12/24.
 */
public class NetworkReportTrace {

    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 故障id
     */
    private String reportId;

    /**
     * 上报时间
     */
    private Long time;

    /**
     * 当前状态信息
     */
    private String content;

    /**
     * 上报人id
     */
    private String reporterId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportID() {
        return reportId;
    }

    public void setReportID(String reportID) {
        this.reportId = reportID;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }
}
