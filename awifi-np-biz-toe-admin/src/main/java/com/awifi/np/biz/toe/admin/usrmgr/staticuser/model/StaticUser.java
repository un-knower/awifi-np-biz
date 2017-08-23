package com.awifi.np.biz.toe.admin.usrmgr.staticuser.model;

import java.io.Serializable;

import com.awifi.np.biz.common.util.FormatUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 上午8:50:05
 * 创建作者：周颖
 * 文件名称：StaticUser.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class StaticUser implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -4664952885697388862L;

    /**用户主键id*/
    private Long id;
    
    /**用户名*/
    private String userName;
    
    /**密码*/
    private String password;
    
    /**用户类型：1代表普通员工、2代表VIP客户、3代表终端体验区*/
    private Integer userType;
    
    /**用户信息类别：1代表手机号、2代表护照号 、3代表身份证号*/
    private Integer userInfoType; 
    
    /**手机号*/
    private String cellphone;
    
    /**护照*/
    private String passport;
    
    /**身份证号*/
    private String identityCard;
    
    /**真实姓名*/
    private String realName;
    
    /**部门*/
    private String deptName;
    
    /**备注*/
    private String remark;
    
    /**商户id*/
    private Long merchantId;
    
    /**商户名称*/
    private String merchantName;
    
    /**商户层级关系*/
    private String cascadeLabel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }
    
    /**
     * 静态用户类型显示值
     * @return dsp
     * @date 2017年2月13日 下午7:17:56
     */
    public String getUserTypeDsp(){
        return FormatUtil.userTypeDsp(userType);
    }
    
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserInfoType() {
        return userInfoType;
    }

    public void setUserInfoType(Integer userInfoType) {
        this.userInfoType = userInfoType;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCascadeLabel() {
        return cascadeLabel;
    }

    public void setCascadeLabel(String cascadeLabel) {
        this.cascadeLabel = cascadeLabel;
    }
}