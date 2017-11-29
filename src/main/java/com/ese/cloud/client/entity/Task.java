package com.ese.cloud.client.entity;

/**
 * Created by mengchenyun on 2017/4/10.
 */
public class Task {

    /**
     * 总需要发送短信数
     */
    private int total;

    /**
     * 成功发送短信数
     */
    private int success;

    /**
     * 失败发送短信数
     */
    private int failure;

    /**
     * 已处理进度
     */
    private int progress;

    private String status;

    public Task() {
        this.total = 0;
        this.progress = 0;
        this.success = 0;
        this.failure = 0;
        this.status = "created";
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
