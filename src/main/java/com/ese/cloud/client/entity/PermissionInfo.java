package com.ese.cloud.client.entity;

import java.io.Serializable;

/**
 * 权限信息
 * Created by rencong on 16/9/10.
 */
public class PermissionInfo implements Serializable {

    private static final long serialVersionUID = 1L;



    private String moduleId;//名称.
    private String permission; //权限字符串,*,create,update,delete,view


    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
