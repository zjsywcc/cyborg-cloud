package com.ese.cloud.client.entity;

/**
 * Created by mengchenyun on 2017/4/24.
 */
public class ReasonCount {

    private String name;
    private long y;

    public ReasonCount(String name, long y) {
        this.name = name;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
}
