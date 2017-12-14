package com.ese.cloud.client.entity.hunanUnicom;

import java.util.Set;

/**
 * 区域信息
 * Created by rencong on 17/1/24.
 */
public class AreaInfo {

    private String id;

    /**
     * 行政区域级别
     */
    private Integer level;

    /**
     * 行政区域名称
     */
    private String name;

    /**
     * 行政区域编码
     */
    private String code;

    /**
     * 区域负责人ID
     */
    private Set<String> technicians;

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


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public Set<String> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(Set<String> technicians) {
        this.technicians = technicians;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
