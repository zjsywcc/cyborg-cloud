package com.ese.cloud.client.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 17/4/5.
 */
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String _id;
    private long createTime;
    private long updateTime;
    private String operatorName;

    public BaseModel() {
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return this._id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String toString() {
        return "BaseModel [_id=" + this._id + ", createTime=" + this.createTime + ", updateTime=" + this.updateTime + ", operatorName=" + this.operatorName + "]";
    }
}