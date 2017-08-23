package com.awifi.np.biz.timebuysrv.web.module.time.model;



import java.io.Serializable;
import java.util.Date;


/**
 * 订单实体类
 * @author zhouwx
 * @since:2015-12-15
 */
public class Order implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -2333054156539250814L;
    /**
     * 主键
     */
    private long id;
    /**
     * 订单编号 unique
     */
    private String orderId;
    /**
     * 商户id
     */
    private long merchantId;
    /**
     * 用户id
     */
    private long userId;
    /**
     * 套餐id
     */
    private long packageId;
    /**
     * 套餐数量
     */
    private int packageNum;
    /**
     * 套餐价值
     */
    private double totalNum;
    /**
     * 实际支付金额
     */
    private double payNum;
    /**
     * 充值通道 1：预留；2：翼支付；3：支付宝
     */
    private int rechargeType;
    /**
     * 充值账号
     */
    private String rechargeAccount;
    /**
     * 状态 1：创建订单 2：支付中 3：订单完成 9：订单异常
     */
    private int status;
    /**
     * 状态备注
     */
    private String statusRemarks;
    /**
     * 状态时间
     */
    private Date statusDate;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 订单备注
     */
    private String remarks;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 支付流水
     */
    private String paySno;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 全模糊匹配
     */
    private String merchantNameLike;
    /**
     * 手机号
     */
    private String telephone;
    
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMerchantNameLike() {
        return merchantNameLike;
    }

    public void setMerchantNameLike(String merchantNameLike) {
        this.merchantNameLike = merchantNameLike;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPaySno() {
        return paySno;
    }

    public void setPaySno(String paySno) {
        this.paySno = paySno;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public int getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(int packageNum) {
        this.packageNum = packageNum;
    }

    public double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(double totalNum) {
        this.totalNum = totalNum;
    }

    public double getPayNum() {
        return payNum;
    }

    public void setPayNum(double payNum) {
        this.payNum = payNum;
    }

    public int getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(int rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getRechargeAccount() {
        return rechargeAccount;
    }

    public void setRechargeAccount(String rechargeAccount) {
        this.rechargeAccount = rechargeAccount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusRemarks() {
        return statusRemarks;
    }

    public void setStatusRemarks(String statusRemarks) {
        this.statusRemarks = statusRemarks;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
