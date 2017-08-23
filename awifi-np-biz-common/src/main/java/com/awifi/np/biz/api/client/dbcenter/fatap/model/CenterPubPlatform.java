package com.awifi.np.biz.api.client.dbcenter.fatap.model;

import java.util.Date;

public class CenterPubPlatform {

    /**
     * 平台编号
     */
    private Integer id;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 平台类型
     */
    private String platformType;

    /**
     * 省编号
     */
    private Integer province;

    /**
     * 市编号
     */
    private Integer city;

    /**
     * 区编号
     */
    private Integer county;
    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区名称
     */
    private String countyName;
    /**
     * portal服务器地址
     */
    private String portalIp;

    /**
     * 认证服务器
     */
    private String authIp;

    /**
     * 平台服务器地址
     */
    private String platformIp;

    /**
     * 设备总线服务器地址
     */
    private String devBusIp;

    /**
     * 设备总线服务器端口
     */
    private String devBusPort;

    /**
     * 默认DEVID
     */
    private String deviceId;

    /**
     * 平台管理人
     */
    private String administrant;

    /**
     * 管理人电话
     */
    private String administrantPhone;

    /**
     * 平台对应url
     */
    private String url;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 说明
     */
    private String remark;
    /**
     * portal服务器地址
     */
    private String portalDomain;
    /**
     * 认证服务器地址
     */
    private String authDomain;
    /**
     * 平台服务器地址
     */
    private String platformDomain;
    /**
     * portal服务端口
     */
    private Integer portalPort;
    /**
     * 认证服务端口
     */
    private Integer authPort;
    /**
     * 平台服务端口
     */
    private Integer platformPort;

    public String getPortalDomain() {
        return portalDomain;
    }

    public void setPortalDomain(String portalDomain) {
        this.portalDomain = portalDomain;
    }

    public String getAuthDomain() {
        return authDomain;
    }

    public void setAuthDomain(String authDomain) {
        this.authDomain = authDomain;
    }

    public String getPlatformDomain() {
        return platformDomain;
    }

    public void setPlatformDomain(String platformDomain) {
        this.platformDomain = platformDomain;
    }

    public Integer getPortalPort() {
        return portalPort;
    }

    public void setPortalPort(Integer portalPort) {
        this.portalPort = portalPort;
    }

    public Integer getAuthPort() {
        return authPort;
    }

    public void setAuthPort(Integer authPort) {
        this.authPort = authPort;
    }

    public Integer getPlatformPort() {
        return platformPort;
    }

    public void setPlatformPort(Integer platformPort) {
        this.platformPort = platformPort;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCounty() {
        return county;
    }

    public void setCounty(Integer county) {
        this.county = county;
    }

    public String getPortalIp() {
        return portalIp;
    }

    public void setPortalIp(String portalIp) {
        this.portalIp = portalIp;
    }

    public String getAuthIp() {
        return authIp;
    }

    public void setAuthIp(String authIp) {
        this.authIp = authIp;
    }

    public String getPlatformIp() {
        return platformIp;
    }

    public void setPlatformIp(String platformIp) {
        this.platformIp = platformIp;
    }

    public String getDevBusIp() {
        return devBusIp;
    }

    public void setDevBusIp(String devBusIp) {
        this.devBusIp = devBusIp;
    }

    public String getDevBusPort() {
        return devBusPort;
    }

    public void setDevBusPort(String devBusPort) {
        this.devBusPort = devBusPort;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAdministrant() {
        return administrant;
    }

    public void setAdministrant(String administrant) {
        this.administrant = administrant;
    }

    public String getAdministrantPhone() {
        return administrantPhone;
    }

    public void setAdministrantPhone(String administrantPhone) {
        this.administrantPhone = administrantPhone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}