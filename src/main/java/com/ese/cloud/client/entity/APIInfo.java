package com.ese.cloud.client.entity;

/**
 * Created by rencong on 17/1/23.
 */
public class APIInfo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * API名称
     */
    private String name;

    /**
     * api 的code
     */
    private String code;

    /**
     * 类型:1-BA认证,2-密码认证
     */
    private String type;

    /**
     * 密钥
     */
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
