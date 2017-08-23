package com.awifi.np.biz.mws.whiteuser.model;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月11日 下午5:03:25
* 创建作者：王冬冬
* 文件名称：WhiteUser.java
* 版本：  v1.0
* 功能：白名单实体类
* 修改记录：
*/
public class StationMerchantNamelistSendlog {
    /**id*/
    private Integer id;

    /**id*/
    private Integer accountId;

    /**商户id*/
    private Long merchantId;
    /**设备id*/
    private String deviceId;
    /**用户mac*/
    private String userMac;
    /**状态*/
    private Byte status;
    /**失败原因*/
    private String failReason;
    /**标示白名单*/
    private Byte flg;
    /**创建日期*/
    private Integer createTime;
    /**任务id*/
    private String taskId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserMac() {
        return userMac;
    }

    public void setUserMac(String userMac) {
        this.userMac = userMac == null ? null : userMac.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public Byte getFlg() {
        return flg;
    }

    public void setFlg(Byte flg) {
        this.flg = flg;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
	
}