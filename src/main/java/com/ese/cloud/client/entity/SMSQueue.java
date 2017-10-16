package com.ese.cloud.client.entity;

/**
 * 短信队列
 * Created by rencong on 17/4/28.
 */
public class SMSQueue {

    /**
     * 手机号
     */
    private String mob;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private long time;


    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
