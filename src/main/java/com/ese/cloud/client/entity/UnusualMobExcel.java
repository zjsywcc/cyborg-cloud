package com.ese.cloud.client.entity;

/**
 * 异常手机号队形
 * Created by rencong on 17/6/1.
 */
public class UnusualMobExcel {


    private String mob;//手机号
    private int num;//次数

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
