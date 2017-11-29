package com.ese.cloud.client.entity;

/**
 * Created by user on 17/2/15.
 */
public class AppInfo {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * app名称
     */
    private String name;

    /**
     * app识别码
     */
    private String code;

    /**
     * 负责人
     */
    private String owner;

    /**
     * 负责人手机号
     */
    private String mob;

    /**
     * 负责人邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remarks;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
