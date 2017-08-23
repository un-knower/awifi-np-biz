package com.awifi.np.biz.timebuysrv.web.module.time.model;

import java.util.Date;

/**
 * 商户上网套餐信息
 * 
 * @author 张智威 2017年4月10日 上午10:04:58
 */
public class TimePackage {
    /** * center_pub_merchant_package.id 编号 */
    private Long id;
    /** * center_pub_merchant_package.merchant_id 商户编号 */
    private Long merchantId;
    /** * center_pub_merchant_package.package_type 套餐类型; 1：资费套餐 2：活动套餐 */
    private Integer packageType;
    /**
     * * center_pub_merchant_package.package_key 套餐包关键词 101：元/天 102：元/周 103：元/月
     * 104：元/季度 105：元/半年 106：元/年 ------------- 201：免费体验天数 202：vip用户体验天数
     */
    private Integer packageKey;
    /** * center_pub_merchant_package.package_value 套餐包值 */
    private Float packageValue;
    /** * center_pub_merchant_package.effect_datetime 套餐包生效时间 */
    private Date effectDatetime;
    /** * center_pub_merchant_package.expired_datetime 套餐包失效时间 */
    private Date expiredDatetime;
    /** * center_pub_merchant_package.remarks 描述 */
    private String remarks;
    /** * center_pub_merchant_package.status 状态 1:正常 2:冻结 9:作废 */
    private Integer status;
    /** * center_pub_merchant_package.statusDate 状态时间 */
    private Date statusDate;
    /** * center_pub_merchant_package.create_date 创建时间 */
    private Date createDate;

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

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
    }

    public Integer getPackageKey() {
        return packageKey;
    }

    public void setPackageKey(Integer packageKey) {
        this.packageKey = packageKey;
    }

    public Float getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(Float packageValue) {
        this.packageValue = packageValue;
    }

    public Date getEffectDatetime() {
        return effectDatetime;
    }

    public void setEffectDatetime(Date effectDatetime) {
        this.effectDatetime = effectDatetime;
    }

    public Date getExpiredDatetime() {
        return expiredDatetime;
    }

    public void setExpiredDatetime(Date expiredDatetime) {
        this.expiredDatetime = expiredDatetime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
