/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 上午9:42:51
* 创建作者：王冬冬
* 文件名称：CenterPubDevice.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class CenterPubDevice {
    private String belongTo;
    private Integer city;
    private String cityText;
    private String corporation;
    private String corporationText;
    private Integer county;
    private String countyText;
    private String deviceId;
    private Long entityId;
    private String entityName;
    private String entityType;
    private String id;          //change by 152 from Integer to String
    private String importer;
    private String macAddr;
    private String model;
    private String modelText;
    private String outId;
    private String outTypeId;
    private Integer province;
    private String provinceText;
    private Byte status;
    private String address;
    private String longitude;
    private String latitude;
    
    //add by 152
    private String merchantName;
    private Integer isOnline;
    private List<CenterPubDevice> deviceList;
    private String newSsidName;
    private String ssid;
    private Long merchantId;

    //v13 备注
    private String remark;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private long bindDate;


    /**
     * 同时转换格林威治时间为普通时间
     * @return
     */
    public String getBindDate() {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTime(new Date(bindDate));   
        return dateFormat.format(calendar.getTime());
    }

    public void setBindDate(long bindDate) {
        this.bindDate = bindDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getNewSsidName() {
        return newSsidName;
    }

    public void setNewSsidName(String newSsidName) {
        this.newSsidName = newSsidName;
    }

    public List<CenterPubDevice> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<CenterPubDevice> deviceList) {
        this.deviceList = deviceList;
    }


    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getCorporationText() {
        return corporationText;
    }

    public void setCorporationText(String corporationText) {
        this.corporationText = corporationText;
    }

    public Integer getCounty() {
        return county;
    }

    public void setCounty(Integer county) {
        this.county = county;
    }

    public String getCountyText() {
        return countyText;
    }

    public void setCountyText(String countyText) {
        this.countyText = countyText;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImporter() {
        return importer;
    }

    public void setImporter(String importer) {
        this.importer = importer;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelText() {
        return modelText;
    }

    public void setModelText(String modelText) {
        this.modelText = modelText;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getOutTypeId() {
        return outTypeId;
    }

    public void setOutTypeId(String outTypeId) {
        this.outTypeId = outTypeId;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public String getProvinceText() {
        return provinceText;
    }

    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
