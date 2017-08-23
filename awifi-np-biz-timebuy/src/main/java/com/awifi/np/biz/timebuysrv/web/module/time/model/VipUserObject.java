package com.awifi.np.biz.timebuysrv.web.module.time.model;



import java.io.Serializable;
import java.util.Date;

import com.awifi.np.biz.common.util.DateUtil;


/**
 * 电渠传入数据 vip用户数据
 * @author 张智威
 * 2017年4月10日 下午3:23:40
 */
public class VipUserObject  extends BaseEntity implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 3528001077598143386L;
    /**
     * 序列化
     */
    private long id;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 标志位
     */
    private String processFlg;
    /**
     * 开始时间
     */
    private long startTime;
    private String startTimeStr;
    /**
     * 结束时间
     */
    private long endTime;
    private String endTimeStr;
    /**
     * 创建时间
     */
    private Date createDate;
    
    private String createDateStr;
    /**
     * 修改时间
     */
    private Date modifyDate;
    
    private String modifyDateStr;
    /**
     * 当前时间
     */
    private long nowTime;
    /**
     * 商户id
     */
    private long merchantId;
    /**
     * 商户名
     */
    private String merchantName;
    /**
     * 省份
     */
    private String provinceName;
    /**
     * 市
     */
    private String cityName;
    /**
     * 区
     */
    private String countyName;
    
    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    public long getNowTime() {
        return nowTime;
    }

    public void setNowTime(long nowTime) {
        this.nowTime = nowTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProcessFlg() {
        return processFlg;
    }

    public void setProcessFlg(String processFlg) {
        this.processFlg = processFlg;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(Date createDateStr) {
        this.createDateStr = DateUtil.formatToString(createDateStr, DateUtil.YYYY_MM_DD_HH_MM_SS);
    }

    public String getModifyDateStr() {
        return modifyDateStr;
    }

    public void setModifyDateStr(Date modifyDateStr) {
        this.modifyDateStr = DateUtil.formatToString(modifyDateStr, DateUtil.YYYY_MM_DD_HH_MM_SS);
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(Long startTimeStr) {
        this.startTimeStr = DateUtil.formatTimestamp(startTimeStr);
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(Long endTimeStr) {
        this.endTimeStr =  DateUtil.formatTimestamp(endTimeStr);
    }
    
}
