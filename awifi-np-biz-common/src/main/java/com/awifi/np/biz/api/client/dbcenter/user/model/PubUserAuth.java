/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 上午9:47:46
* 创建作者：尤小平
* 文件名称：PubUserAuth.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.user.model;

import java.io.Serializable;
import java.util.Date;

public class PubUserAuth implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7668678139343536262L;

    private Long id;//主键

    private Integer authPlatform;// 认证平台

    private Long userId;// 用户编号，关联到用户表里面的主键

    private String logname;//用户名（登录账号）

    private String telphone;//用户手机号码

    private String wechat;//用户微信账号

    private String email;//用户邮箱

    private String userCard;//用户身份证

    private String authPswd;//认证密码

    private String userType;//用户类型，1：普通用户，2：商户

    private String safetyCode;//安全码

    private Date createDate;//创建时间

    private Integer status;// 状态（1:正常,2:冻结/锁定,9:注销）

    private Date statusDate;//状态时间

    private Date unlockDate;//自动解锁时间
    
    private String seed;//双方约定随机码
    
    private String token;//token

    public PubUserAuth() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the authPlatform
     */
    public Integer getAuthPlatform() {
        return authPlatform;
    }

    /**
     * @param authPlatform the authPlatform to set
     */
    public void setAuthPlatform(Integer authPlatform) {
        this.authPlatform = authPlatform;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the logname
     */
    public String getLogname() {
        return logname;
    }

    /**
     * @param logname the logname to set
     */
    public void setLogname(String logname) {
        this.logname = logname;
    }

    /**
     * @return the telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone the telphone to set
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * @return the wechat
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * @param wechat the wechat to set
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the userCard
     */
    public String getUserCard() {
        return userCard;
    }

    /**
     * @param userCard the userCard to set
     */
    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    /**
     * @return the authPswd
     */
    public String getAuthPswd() {
        return authPswd;
    }

    /**
     * @param authPswd the authPswd to set
     */
    public void setAuthPswd(String authPswd) {
        this.authPswd = authPswd;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the safetyCode
     */
    public String getSafetyCode() {
        return safetyCode;
    }

    /**
     * @param safetyCode the safetyCode to set
     */
    public void setSafetyCode(String safetyCode) {
        this.safetyCode = safetyCode;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the statusDate
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the unlockDate
     */
    public Date getUnlockDate() {
        return unlockDate;
    }

    /**
     * @param unlockDate the unlockDate to set
     */
    public void setUnlockDate(Date unlockDate) {
        this.unlockDate = unlockDate;
    }

    /**
     * @return the seed
     */
    public String getSeed() {
        return seed;
    }

    /**
     * @param seed the seed to set
     */
    public void setSeed(String seed) {
        this.seed = seed;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
