/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月27日 上午10:30:12
* 创建作者：周颖
* 文件名称：Strategy.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.strategy.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Strategy implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1641176934902505576L;

    /**主键*/
    private Long id;
    
    /**策略名称*/
    private String strategyName;
    
    /**策略类型*/
    private Integer strategyType;
    
    /**备注*/
    private String remark;
    
    /**优先级*/
    private Integer orderNo;
    
    /**开始时间*/
    private String startDate;

    /**截止时间*/
    private String endDate;
    
    /**创建日期*/
    private String createDate;
    
    /**修改日期*/
    private String updateDate;
    
    /**站点id*/
    private Long siteId;
    
    /**商户id*/
    private Long merchantId;
    
    /**商户层级*/
    private String cascadeLabel;
    
    /**热点名称*/
    private String ssid;
    
    /**设备名称*/
    private String deviceName;
    
    /**设备列表*/
    private List<Map<String,Object>> deviceList;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getCascadeLabel() {
        return cascadeLabel;
    }

    public void setCascadeLabel(String cascadeLabel) {
        this.cascadeLabel = cascadeLabel;
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

    public List<Map<String,Object>> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Map<String,Object>> deviceList) {
        this.deviceList = deviceList;
    }
}
