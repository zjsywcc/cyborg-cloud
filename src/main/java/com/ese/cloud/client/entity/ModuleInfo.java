package com.ese.cloud.client.entity;

import java.io.Serializable;

/**
 * 功能模块信息
 * Created by rencong on 16/10/10.
 */
public class ModuleInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 功能模块名称
     */
    private String name;
    /**
     * key
     */
    private String key;
    /**
     * 访问路径
     */
    private String url;
    /**
     * 图标
     */
    private String ico;

    /**
     * 备注
     */
    private String remark;

    /**
     * 模块级别,0顶级,1一级,2二级,3三级
     */
    private Integer level;

    /**
     * 上级模块id
     */
    private String parentId;

    /**
     * 排序
     */
    private Integer sort;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
