package com.awifi.np.biz.api.client.dbcenter.device.device.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.FormatUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 下午2:50:02
 * 创建作者：亢燕翔
 * 文件名称：Device.java
 * 版本：  v1.0
 * 功能：  设备实体
 * 修改记录：
 */
public class Device implements Serializable{
    
    /**对象序列化*/
    private static final long serialVersionUID = -6204502860985342622L;
    /**商户id*/
    private Long merchantId;
    /**商户名称*/
    private String merchantName;
    /**宽带账号*/
    private String broadbandAccount;
    /**设备id*/
    private String deviceId;
    /**实体设备名称*/
    private String entityName;
    /**设备归属*/
    private String belongTo;
    /**设备MAC*/
    private String devMac;
    /**设备ip*/
    private String devIp;
    /**SSID*/
    private String ssid;
    /**设备类型*/
    private Integer entityType;
    /**厂家id*/
    private String corporation;
    /**厂家中文名称*/
    private String corporationDsp;
    /**型号id*/
    private String model;
    /**型号中文名称*/
    private String modelDsp;
    /**固件版本号*/
    private String fwVersion;
    /**PING码*/
    private String pinCode;
    /**cvlan*/
    private String cvlan;
    /**pvlan*/
    private String pvlan;
    /**省id*/
    private Long provinceId;
    /**省*/
    private String province;
    /**市id*/
    private Long cityId;
    /**市*/
    private String city;
    /**区id*/
    private Long areaId;
    /**区*/
    private String area;
    /**地区全称*/
    //private String locationFullName;
    /**详细地址*/
    private String address;//
    /**绑定时间*/
    private String bindTime;
    /**在线用户数*/
    private Integer onlineNum;
    /**备注*/
    private String remark;
    /**设备状态*/
    private Integer status;
    /**商户账号*/
    private String userName;
    
    /** * 一键放通开关 */
    private String onekeySwitch;
    /** chinaNet开关 */
    private String chinaNetSwitch;
    /** awifi开关  */
    private String awifiSwitch;
    /** * lan口开关 */
    private String lanSwitch;
    /** * 闲时下线 */
    private String offlineTime;
    
    /** 经度 */
    private BigDecimal longitude;
    /** 维度  */
    private BigDecimal latitude;
    
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
    
    /**
     * 将设备类型转化成中文
     * @return 设备类型
     */
    public String getEntityTypeDsp(){
        return FormatUtil.formatEntityType(entityType);
    }
    
    /**
     * 设备名称
     * @return 设备名称
     * @author 周颖  
     * @date 2017年5月3日 上午10:06:16
     */
    public String getDeviceName(){
        return FormatUtil.formatDeviceName(entityType,devMac,ssid,entityName);
    }
    
    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }
    
    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getBroadbandAccount() {
        return broadbandAccount;
    }

    public void setBroadbandAccount(String broadbandAccount) {
        this.broadbandAccount = broadbandAccount;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public String getDevIp() {
        return devIp;
    }

    public void setDevIp(String devIp) {
        this.devIp = devIp;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getCorporationDsp() {
        return corporationDsp;
    }

    public void setCorporationDsp(String corporationDsp) {
        this.corporationDsp = corporationDsp;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public String getModelDsp() {
        return modelDsp;
    }

    public void setModelDsp(String modelDsp) {
        this.modelDsp = modelDsp;
    }

    public String getFwVersion() {
        return fwVersion;
    }

    public void setFwVersion(String fwVersion) {
        this.fwVersion = fwVersion;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCvlan() {
        return cvlan;
    }

    public void setCvlan(String cvlan) {
        this.cvlan = cvlan;
    }

    public String getPvlan() {
        return pvlan;
    }

    public void setPvlan(String pvlan) {
        this.pvlan = pvlan;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocationFullName() {
        return FormatUtil.locationFullName(province, city, area);
    }

    /*public void setLocationFullName(String locationFullName) {
        this.locationFullName = locationFullName;
    }*/

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChinaNetSwitch() {
        return chinaNetSwitch;
    }
    
    public String getChinaNetSwitchDsp() {
        return FormatUtil.formatDevSwitchDsp(chinaNetSwitch);
    }

    public void setChinaNetSwitch(String chinaNetSwitch) {
        this.chinaNetSwitch = chinaNetSwitch;
    }

    public String getAwifiSwitch() {
        return awifiSwitch;
    }
    
    public String getAwifiSwitchDsp() {
        return FormatUtil.formatDevSwitchDsp(awifiSwitch);
    }

    public void setAwifiSwitch(String awifiSwitch) {
        this.awifiSwitch = awifiSwitch;
    }

    public String getOnekeySwitch() {
        return onekeySwitch;
    }
    
    public String getOnekeySwitchDsp() {
        return FormatUtil.formatDevSwitchDsp(onekeySwitch);
    }

    public void setOnekeySwitch(String onekeySwitch) {
        this.onekeySwitch = onekeySwitch;
    }

    public String getLanSwitch() {
        return lanSwitch;
    }
    
    public String getLanSwitchDsp() {
        return FormatUtil.formatDevSwitchDsp(lanSwitch);
    }

    public void setLanSwitch(String lanSwitch) {
        this.lanSwitch = lanSwitch;
    }

    public String getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(String offlineTime) {
        this.offlineTime = offlineTime;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}
