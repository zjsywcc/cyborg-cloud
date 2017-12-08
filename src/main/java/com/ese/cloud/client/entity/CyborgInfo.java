package com.ese.cloud.client.entity;


import java.io.Serializable;

/**
 * Created by wangchengcheng on 2017/10/15.
 */
public class CyborgInfo implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 电子人ID
     */
    private String id;

    /**
     * 电子人名称
     */
    private String name;

    /*性别*/
    private String gender;

    /*年龄*/
    private String age;


    /**
     * 备注
     */
    private String remarks;
    private String tiredness;



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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGender(){return gender;}

    public void setGender(String gender){this.gender=gender;}

    public String getAge(){return age;}

    public void setAge(String age){this.age=age;}

    public String getTiredness() {
        return tiredness;
    }

    public  void setTiredness(String tiredness){this.tiredness=tiredness;}
}
