/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月12日 下午2:28:48
* 创建作者：尤小平
* 文件名称：StationApp.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.app.model;

public class StationApp {
    private Integer id;

    private String appId;

    private String appSecret;

    private String appName;

    private String grantType;

    private String developCom;

    private String description;

    private Double price;

    private String appIcon;

    private String redirectUrl;

    private String linkUrl;

    private Long ownerUserId;

    private String ownerEmail;

    private String ownerName;

    private Integer createTime;

    private Short status;

    private Integer IndustryCode;

    private String appCode;

    private Short needConfiguration;

    private String configUrl;
    // 非数据库字段
    private Long merchantId;

    private Integer limitNum;

    private Integer relationId;

    private Byte buyStatus;

    private String token; // 生成各个应用的token

    private Long timestamp; // 生成timestamp时间 到微秒

    private String merchantOpenId; // 商户openid

    private String userOpenId;// 用户openid

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType == null ? null : grantType.trim();
    }

    public String getDevelopCom() {
        return developCom;
    }

    public void setDevelopCom(String developCom) {
        this.developCom = developCom == null ? null : developCom.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon == null ? null : appIcon.trim();
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl == null ? null : redirectUrl.trim();
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl == null ? null : linkUrl.trim();
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail == null ? null : ownerEmail.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getIndustryCode() {
        return IndustryCode;
    }

    public void setIndustryCode(Integer industryCode) {
        IndustryCode = industryCode;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Short getNeedConfiguration() {
        return needConfiguration;
    }

    public void setNeedConfiguration(Short needConfiguration) {
        this.needConfiguration = needConfiguration;
    }

    public String getConfigUrl() {
        return configUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.configUrl = configUrl;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Byte getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Byte buyStatus) {
        this.buyStatus = buyStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMerchantOpenId() {
        return merchantOpenId;
    }

    public void setMerchantOpenId(String merchantOpenId) {
        this.merchantOpenId = merchantOpenId;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}