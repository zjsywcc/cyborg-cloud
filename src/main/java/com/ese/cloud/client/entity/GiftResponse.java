package com.ese.cloud.client.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by mengchenyun on 2017/3/31.
 */
public class GiftResponse {

    @JSONField(name="creation_time")
    private Integer creationTime;

    @JSONField(name="download_url")
    private String downloadUrl;

    @JSONField(name="download_url_https")
    private String downloadUrlHttps;

    @JSONField(name="file_size")
    private Integer fileSize;

    @JSONField(name="md5")
    private String md5;

    @JSONField(name="resource_key")
    private String resourceKey;

    @JSONField(name="status")
    private String status;

    @JSONField(name="status_code")
    private Integer statusCode;

    public Integer getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Integer creationTime) {
        this.creationTime = creationTime;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrlHttps() {
        return downloadUrlHttps;
    }

    public void setDownloadUrlHttps(String downloadUrlHttps) {
        this.downloadUrlHttps = downloadUrlHttps;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
