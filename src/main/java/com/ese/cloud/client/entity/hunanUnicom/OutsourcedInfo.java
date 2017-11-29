package com.ese.cloud.client.entity.hunanUnicom;

/**
 * 外包公司信息
 * Created by rencong on 17/1/23.
 */
public class OutsourcedInfo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contentor;

    /**
     * 电话
     */
    private String tel;

    /**
     * 公司资质
     */
    private String companyLicense;

    /**
     * 公司规模
     */
    private String companyScale;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContentor() {
        return contentor;
    }

    public void setContentor(String contentor) {
        this.contentor = contentor;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCompanyLicense() {
        return companyLicense;
    }

    public void setCompanyLicense(String companyLicense) {
        this.companyLicense = companyLicense;
    }

    public String getCompanyScale() {
        return companyScale;
    }

    public void setCompanyScale(String companyScale) {
        this.companyScale = companyScale;
    }
}
