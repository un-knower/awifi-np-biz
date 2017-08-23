package com.awifi.np.biz.api.client.dbcenter.device.entity.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.FormatUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午9:12:27
 * 创建作者：亢燕翔
 * 文件名称：EntityInfo.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class EntityInfo implements Serializable{

    /**序列化*/
    private static final long serialVersionUID = -5695992625317285713L;

    /**设备MAC*/
    private String devMac;
    
    /**SSID*/
    private String ssid;
    
    /**所属AC*/
    private String acName;
    
    /**设备类型*/
    private Integer entityType;
    
    /**在线用户数*/
    private Integer onlineNum;
    
    /**在线时间*/
    private String onlineLastTime;
    /**状态，0代表离线、1代表在线*/
    private Integer status;

    /**
     * 商户名称
     */
    private String merchantName;
    
    /**
     * 商户id
     */
    private Long merchantId;
    
    /**
     * 所属热点
     */
    private String hotArea;
    /**
     * 日期
     */
    private String bindDate;
    /**
     * 将设备状态转化成中文
     * @return 设备类型
     */
    public String getStatusDsp(){
        if(status == null){
            return StringUtils.EMPTY;
        }
        return FormatUtil.formatDeviceStatus(status);
    }
    
    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    /**
     * 将设备类型转化成中文
     * @return 设备类型
     */
    public String getEntityTypeDsp(){
        return FormatUtil.formatEntityType(entityType);
    }
    
    
    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getHotArea() {
        return hotArea;
    }

    public void setHotArea(String hotArea) {
        this.hotArea = hotArea;
    }

    public String getBindDate() {
        return bindDate;
    }

    public void setBindDate(String bindDate) {
        this.bindDate = bindDate;
    }
    public String getOnlineLastTime() {
        return onlineLastTime;
    }

    public void setOnlineLastTime(String onlineLastTime) {
        this.onlineLastTime = onlineLastTime;
    }
    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

}
