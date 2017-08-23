package com.awifi.np.biz.api.client.dbcenter.device.device.model;

import java.io.Serializable;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午2:49:25
 * 创建作者：亢燕翔
 * 文件名称：DeviceOwner.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class DeviceOwner implements Serializable{

    /**序列化*/
    private static final long serialVersionUID = -3217113579016755190L;

    /** 设备类型 */
    private String deviceType;
    /** mac */
    private String mac;
    /** 热点 */
    private String ssid;
    /** 设备名称 */
    private String deviceName;
    /** 商户id */
    private String merchantId;
    /** 项目id */
    private String projectId;
    /** 设备id */
    private String deviceId;
    /**
     * 宽带账号
     */
    private String broadAccount;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户账号
     */
    private String merchantAccount;
    /**
     * 一级行业
     */
    private String firstIndustry;
    /**
     * 二级行业
     */
    private String secondIndustry;
    /**
     * 联系人
     */
    private String contacter;
    /**
     * 联系电话
     */
    private String cellPhone;
    /**
     * 省
     */
    private String province;
    /**
     * 省id
     */
    private Long provinceId;
    /**
     * 市
     */
    private String city;
    /**
     * 市id
     */
    private Long cityId;
    /**
     * 区
     */
    private String country;
    /**
     * 区id
     */
    private Long countryId;
    /**
     * 商户地址
     */
    private String merchantAddress;
    /**
     * 角色id
     */
    private String roleId;
    
    public String getDeviceType() {
        return deviceType;
    }
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getSsid() {
        return ssid;
    }
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public String getMerchantAccount() {
        return merchantAccount;
    }
    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }
    public String getFirstIndustry() {
        return firstIndustry;
    }
    public void setFirstIndustry(String firstIndustry) {
        this.firstIndustry = firstIndustry;
    }
    public String getSecondIndustry() {
        return secondIndustry;
    }
    public void setSecondIndustry(String secondIndustry) {
        this.secondIndustry = secondIndustry;
    }
    public String getContacter() {
        return contacter;
    }
    public void setContacter(String contacter) {
        this.contacter = contacter;
    }
    public String getCellPhone() {
        return cellPhone;
    }
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getMerchantAddress() {
        return merchantAddress;
    }
    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }
    public String getBroadAccount() {
        return broadAccount;
    }
    public void setBroadAccount(String broadAccount) {
        this.broadAccount = broadAccount;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public Long getProvinceId() {
        return provinceId;
    }
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public Long getCountryId() {
        return countryId;
    }
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    @Override
    public String toString() {
        return "DeviceOwner [deviceType=" + deviceType + ", mac=" + mac + ", ssid=" + ssid + ", deviceName="
                + deviceName + ", merchantId=" + merchantId + ", projectId=" + projectId + ", deviceId=" + deviceId
                + ", broadAccount=" + broadAccount + ", merchantName=" + merchantName + ", merchantAccount="
                + merchantAccount + ", firstIndustry=" + firstIndustry + ", secondIndustry=" + secondIndustry
                + ", contacter=" + contacter + ", cellPhone=" + cellPhone + ", province=" + province + ", provinceId="
                + provinceId + ", city=" + city + ", cityId=" + cityId + ", country=" + country + ", countryId="
                + countryId + ", merchantAddress=" + merchantAddress + ", roleId=" + roleId + "]";
    }
}
