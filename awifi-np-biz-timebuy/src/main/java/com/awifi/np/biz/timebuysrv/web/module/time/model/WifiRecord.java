/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月11日 上午11:14:35
* 创建作者：尤小平
* 文件名称：WifiRecord.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.model;

public class WifiRecord {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商户id
     */
    private Long merchantId;

    /**
     * portal url
     */
    private String portalUrl;

    /**
     * 用户手机号码
     */
    private String telphone;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备类型
     */
    private String devType;

    /**
     * token
     */
    private String token;

    /**
     * token生成时间
     */
    private String tokenDate;

    /**
     * 网络放通url
     */
    private String wifiUrl;

    /**
     * 网络放通成功或失败的结果
     */
    private String wifiResult;

    /**
     * 网络放通成功或失败的时间
     */
    private String wifiDate;

    /**
     * 失败原因
     */
    private String errorInfo;

    public WifiRecord() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the merchantId
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * @param merchantId
     *            the merchantId to set
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * @return the portalUrl
     */
    public String getPortalUrl() {
        return portalUrl;
    }

    /**
     * @param portalUrl
     *            the portalUrl to set
     */
    public void setPortalUrl(String portalUrl) {
        this.portalUrl = portalUrl;
    }

    /**
     * @return the telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone
     *            the telphone to set
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * @return the devType
     */
    public String getDevType() {
        return devType;
    }

    /**
     * @param devType
     *            the devType to set
     */
    public void setDevType(String devType) {
        this.devType = devType;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the tokenDate
     */
    public String getTokenDate() {
        return tokenDate;
    }

    /**
     * @return the wifiUrl
     */
    public String getWifiUrl() {
        return wifiUrl;
    }

    /**
     * @param wifiUrl
     *            the wifiUrl to set
     */
    public void setWifiUrl(String wifiUrl) {
        this.wifiUrl = wifiUrl;
    }

    /**
     * @return the wifiResult
     */
    public String getWifiResult() {
        return wifiResult;
    }

    /**
     * @param wifiResult
     *            the wifiResult to set
     */
    public void setWifiResult(String wifiResult) {
        this.wifiResult = wifiResult;
    }

    /**
     * @return the wifiDate
     */
    public String getWifiDate() {
        return wifiDate;
    }

    /**
     * @return the errorInfo
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    /**
     * @param errorInfo
     *            the errorInfo to set
     */
    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     *            the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
