package com.ese.cloud.client.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色信息
 * Created by rencong on 16/9/10.
 */
public class RoleInfo implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;
    /**
     * 角色
     */
    private String role;
    /**
     * 描述
     */
    private String description;
    /**
     * 权限
     */
    private Map<String,Set> permissions;

    /**
     * 权限级别,越大权限级别越低,1-65536
     */
    private int level;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Map<String, Set> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Set> permissions) {
        this.permissions = permissions;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
