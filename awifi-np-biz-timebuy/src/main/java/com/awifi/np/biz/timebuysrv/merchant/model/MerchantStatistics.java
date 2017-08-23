/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月4日 下午6:52:53
* 创建作者：余红伟
* 文件名称：MerchantStatistics.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.model;

import java.io.Serializable;
import java.util.Date;

public class MerchantStatistics implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 2410466126602990303L;
    /** id**/
    private Long id;
    /** 商户Id**/
    private Long merchantId;
    /** 园区数量 **/
    private Integer merchantNum;
    /** 设备总数 **/
    private Integer deviceNum;
    /** 付款总金额**/
    private Float totalPaid;
    /** 使用总人数**/
    private Long totalUsers;
    /** 白名单总人数**/
    private Integer vipUsers;
    /** 付费人数 **/
    private Long payUsers;
    /** 包天数量 **/
    private Long pkgDays;
    /** 包月数量**/
    private Long pkgMonths;
    /** 包年数量**/
    private Long pkgYears;
    /** 创建时间 **/
    private Date createDate;
    
    /** 省**/
    private Integer province;
    /** 市 **/
    private Integer city;
    /** 区 **/
    private Integer county;
    /** 地区描述，为省名或市民或区名**/
    private String area;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
    public Integer getMerchantNum() {
        return merchantNum;
    }
    public void setMerchantNum(Integer merchantNum) {
        this.merchantNum = merchantNum;
    }
    public Integer getDeviceNum() {
        return deviceNum;
    }
    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }
    public Float getTotalPaid() {
        return totalPaid;
    }
    public void setTotalPaid(Float totalPaid) {
        this.totalPaid = totalPaid;
    }
    public Long getTotalUsers() {
        return totalUsers;
    }
    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }
    public Integer getVipUsers() {
        return vipUsers;
    }
    public void setVipUsers(Integer vipUsers) {
        this.vipUsers = vipUsers;
    }
    public Long getPayUsers() {
        return payUsers;
    }
    public void setPayUsers(Long payUsers) {
        this.payUsers = payUsers;
    }
    public Long getPkgDays() {
        return pkgDays;
    }
    public void setPkgDays(Long pkgDays) {
        this.pkgDays = pkgDays;
    }
    public Long getPkgMonths() {
        return pkgMonths;
    }
    public void setPkgMonths(Long pkgMonths) {
        this.pkgMonths = pkgMonths;
    }
    public Long getPkgYears() {
        return pkgYears;
    }
    public void setPkgYears(Long pkgYears) {
        this.pkgYears = pkgYears;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    
    
    

}
