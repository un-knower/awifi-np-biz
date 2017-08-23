package com.awifi.np.biz.toe.admin.project.model;

import java.io.Serializable;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 11, 2017 2:00:08 PM
 * 创建作者：亢燕翔
 * 文件名称：Project.java
 * 版本：  v1.0
 * 功能：  项目实体
 * 修改记录：
 */
public class Project implements Serializable{

	/** 序列化  */
    private static final long serialVersionUID = 3261347953165360449L;

    /** 项目ID  */
    private Long id;
    
    /** 项目名称  */
    private String projectName;

    /** 联系人  */
    private String contact;
    
    /** 联系方式  */
    private String contactWay; 
    
    /**平台id*/
    private Integer platformId;
    
    /** 省  */
    private String province;
    
    /** 省ID */
    private Long provinceId;
    
    /** 市  */
    private String city;
    
    /** 市ID */
    private Long cityId;
    
    /** 区县  */
    private String area;
    
    /** 区县ID */
    private Long areaId;
    
    /** 详细地址  */
    private String address;
    
    /** 地区全名  */
    private String locationFullName;
    
    /**创建时间*/
    private String createDate;
    
    /** 备注  */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationFullName() {
        return locationFullName;
    }

    public void setLocationFullName(String locationFullName) {
        this.locationFullName = locationFullName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", projectName=" + projectName
                + ", contact=" + contact + ", contactWay=" + contactWay
                + ", province=" + province + ", provinceId=" + provinceId
                + ", city=" + city + ", cityId=" + cityId + ", area=" + area
                + ", areaId=" + areaId + ", address=" + address
                + ", locationFullName=" + locationFullName + ", createDate="
                + createDate + ", remark=" + remark + "]";
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }
    
}
