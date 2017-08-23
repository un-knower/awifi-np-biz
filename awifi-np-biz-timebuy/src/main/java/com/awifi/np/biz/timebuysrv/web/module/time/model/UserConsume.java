package com.awifi.np.biz.timebuysrv.web.module.time.model;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.awifi.np.biz.common.util.DateUtil;


/**
 * 消费记录表
 * @author 张智威
 * 2017年4月10日 下午4:45:23
 */
public class UserConsume {
    /** * center_pub_merchant_user_consume.id */
    private Long id;
    /** * center_pub_merchant_user_consume.merchantId 商户id */
    private Long merchantId;
    /** * center_pub_merchant_user_consume.userId 用户户id */
    private Long userId;
    /** * center_pub_merchant_user_consume.consumeType 消费类型 */
    private Integer consumeType;
    /** * center_pub_merchant_user_consume.packageId 套餐编号*/
    private Long packageId;
    /** * center_pub_merchant_user_consume.packageNum 消费几份套餐*/
    private Integer packageNum;
    /** * center_pub_merchant_user_consume.totalNum 套餐价值*/
    private Float totalNum;
    /** * center_pub_merchant_user_consume.orderId 对应订单编号*/
    private String orderId;
    /** * center_pub_merchant_user_consume.payNum 实付金额*/
    private Float payNum;
    /** * center_pub_merchant_user_consume.pkg_price 套餐价格*/
    private String pkgPrice;
    /** * center_pub_merchant_user_consume.pkg_detail 套餐详情(年|月|天)*/
    private String pkgDetail;
    /** * center_pub_merchant_user_consume.remarks 描述*/
    private String remarks;
    /** * center_pub_merchant_user_consume.status 状态 -1 代表被撤销*/
    private int status;
    
    

    /**********以下是关联查询带出的字段*****************/
    /** 用户手机号**/
    private String telephone;
    /** 园区名**/
    private String merchantName;
    /** 园区地址**/
    private String address;
    
    /****以下是时间套餐特殊字段 ***********/
    
    /** * center_pub_merchant_user_consume.addDay 可用天数*/
    private Float addDay;
    /** * center_pub_merchant_user_consume.begin_date 开始计时日期*/
    private Date beginDate;
    /** * center_pub_merchant_user_consume.end_date 结束计时日期*/
    private Date endDate;
    /** * center_pub_merchant_user_consume.createDate 充值时间*/
    private String createDate;
   
    /** * center_pub_merchant_user_consume.broadband_account 宽带帐号*/
    private String broadbandAccount;
   
    
    
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
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Integer getConsumeType() {
        return consumeType;
    }
    
    public void setConsumeType(Integer consumeType) {
        this.consumeType = consumeType;
    }
    
    public Long getPackageId() {
        return packageId;
    }
    
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
    
    public Integer getPackageNum() {
        return packageNum;
    }
    
    public void setPackageNum(Integer packageNum) {
        this.packageNum = packageNum;
    }

    public Float getPayNum() {
        return payNum;
    }

    public void setPayNum(Float payNum) {
        this.payNum = payNum;
    }

    public Float getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Float totalNum) {
        this.totalNum = totalNum;
    }

    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
   /* public Integer getAddHour() {
        return addDay;
    }
    
    public void setAddHour(Integer addHour) {
        this.addDay = addHour;
    }*/
    
    public Float getAddDay() {
        return addDay;
    }
    
    public void setAddDay(Float addDay) {
        this.addDay = addDay;
    }
    
    public Date getBeginDate() {
        return beginDate;
    }
    
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = DateUtil.formatToString(createDate, DateUtil.YYYY_MM_DD_HH_MM_SS);
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBroadbandAccount() {
        return broadbandAccount;
    }

    public void setBroadbandAccount(String broadbandAccount) {
        this.broadbandAccount = broadbandAccount;
    }

    public String getPkgPrice() {
        return pkgPrice;
    }

    public void setPkgPrice(String pkgPrice) {
        this.pkgPrice = pkgPrice;
    }

    public String getPkgDetail() {
        return pkgDetail;
    }

    public void setPkgDetail(String pkgDetail) {
        this.pkgDetail = pkgDetail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
